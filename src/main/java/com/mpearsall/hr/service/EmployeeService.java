package com.mpearsall.hr.service;

import com.mpearsall.hr.config.CustomUserDetailsService;
import com.mpearsall.hr.dto.EmployeeAnniversary;
import com.mpearsall.hr.dto.EmployeeUser;
import com.mpearsall.hr.entity.primary.user.Role;
import com.mpearsall.hr.entity.primary.user.User;
import com.mpearsall.hr.entity.secondary.Company;
import com.mpearsall.hr.entity.secondary.employee.Employee;
import com.mpearsall.hr.entity.secondary.employee.EmployeeDay;
import com.mpearsall.hr.entity.secondary.employee.EmployeeWeek;
import com.mpearsall.hr.entity.secondary.holiday.CompanyYear;
import com.mpearsall.hr.entity.secondary.holiday.HolidayEntitlement;
import com.mpearsall.hr.exception.InvalidDetailsException;
import com.mpearsall.hr.exception.PermissionException;
import com.mpearsall.hr.exception.ResourceNotFoundException;
import com.mpearsall.hr.repository.primary.UserRepository;
import com.mpearsall.hr.repository.secondary.CompanyRepository;
import com.mpearsall.hr.repository.secondary.EmployeeRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class EmployeeService {
  private static final int BIRTHDAYS = 3;

  private final EmployeeRepository employeeRepository;
  private final CompanyRepository companyRepository;
  private final UserRepository userRepository;
  private final CustomUserDetailsService customUserDetailsService;
  private final CompanyYearService companyYearService;

  public EmployeeService(EmployeeRepository employeeRepository, CompanyRepository companyRepository,
                         UserRepository userRepository, CustomUserDetailsService customUserDetailsService, CompanyYearService companyYearService) {
    this.employeeRepository = employeeRepository;
    this.companyRepository = companyRepository;
    this.userRepository = userRepository;
    this.customUserDetailsService = customUserDetailsService;
    this.companyYearService = companyYearService;
  }

  public static Set<LocalDate> calculateDaysWorkedBetween(Employee employee, LocalDate startDate, LocalDate endDate) {
    // check entitlement
    final Set<DayOfWeek> daysWorked = Employee.getDaysWorked(employee);

    return Stream.iterate(startDate, d -> d.plusDays(1))
        .limit(startDate.until(endDate.plusDays(1), ChronoUnit.DAYS))
        .filter(d -> daysWorked.contains(d.getDayOfWeek()))
        .collect(Collectors.toSet());
  }

  public Employee getCurrentUserEmployee() {
    final UserDetails currentUser = customUserDetailsService.getCurrentUserDetails(false);

    Employee employee = null;
    if (currentUser != null) {
      final User user = userRepository.findByUsername(currentUser.getUsername())
          .orElseThrow(() -> new ResourceNotFoundException(currentUser.getUsername(), User.class));
      employee = employeeRepository.findByUser(user.getId())
          .orElseThrow(() -> new ResourceNotFoundException(user.getId(), Employee.class));
    }

    return employee;
  }

  public static double getAverageDayLength(Employee employee) {
    final EmployeeWeek employeeWeek = employee.getEmployeeWeek();

    int daysWorked = 0;
    double totalHours = 0.0;

    if (employee.getEmployeeWeek() != null) {
      final EmployeeDay[] employeeDays = new EmployeeDay[]{
          employeeWeek.getMonday(),
          employeeWeek.getTuesday(),
          employeeWeek.getWednesday(),
          employeeWeek.getThursday(),
          employeeWeek.getFriday(),
          employeeWeek.getSaturday(),
          employeeWeek.getSunday()
      };

      for (EmployeeDay employeeDay : employeeDays) {
        if (employeeDay != null) {
          daysWorked++;

          final long minutesBetween = ChronoUnit.MINUTES.between(employeeDay.getStart(), employeeDay.getEnd());
          totalHours += minutesBetween / 60.0;
        }
      }
    }

    if (daysWorked == 0.0) {
      return 0.0;
    }

    return totalHours / daysWorked;
  }

  @Transactional
  public Employee save(Employee employee) {
    return save(employee, true);
  }

  @Transactional
  public Employee save(Employee employee, boolean authorize) {
    if (authorize) {
      final boolean admin = customUserDetailsService.currentUserHasRole(Role.ADMIN) ||
          customUserDetailsService.currentUserHasRole(Role.SUPER_ADMIN);

      if (!admin && !employee.equals(getCurrentUserEmployee())) {
        throw new PermissionException("User must be admin to update other employees");
      }
    }

    final Employee manager = employee.getManager() != null && employee.getManager().getId() != null
        ? employeeRepository.findById(employee.getManager().getId()).orElseThrow() : null;

    if (manager != null) {
      if (manager.equals(employee)) {
        throw new InvalidDetailsException("Employee cannot be their own manager");
      }

      if (manager.getManager() != null && manager.getManager().equals(employee)) {
        throw new InvalidDetailsException("Employee cannot manage their own manger");
      }
    }

    return employeeRepository.save(employee);
  }

  @Transactional("secondaryTransactionManager")
  public Employee createEmployee(User user) {
    final Company company = Optional.ofNullable(companyRepository.find()).orElse(new Company());

    if (employeeRepository.existsByUser(user.getId())) {
      throw new InvalidDetailsException("User already has an employee associated with them");
    }

    final Employee employee = new Employee();
    employee.setUser(user.getId());
    employee.setEmployeeWeek(company.getDefaultEmployeeWeek());

    final CompanyYear currentCompanyYear = companyYearService.getCurrentCompanyYear();

    if (currentCompanyYear != null) {
      addDefaultHolidayEntitlement(currentCompanyYear, company, employee);
    }

    return save(employee, false);
  }

  @Transactional
  public void addDefaultHolidayEntitlement(CompanyYear companyYear) {
    final Company company = companyRepository.find();
    final Iterable<Employee> employees = employeeRepository.findAll();

    for (Employee employee : employees) {
      addDefaultHolidayEntitlement(companyYear, company, employee);
    }
  }

  private void addDefaultHolidayEntitlement(CompanyYear companyYear, Company company, Employee employee) {
    final boolean exists = employee.getHolidayEntitlements().stream()
        .anyMatch(holidayEntitlement -> holidayEntitlement.getCompanyYear().equals(companyYear));

    if (!exists) {
      final HolidayEntitlement holidayEntitlement = new HolidayEntitlement();
      holidayEntitlement.setCompanyYear(companyYear);
      holidayEntitlement.setHolidayEntitlementHours(company.getDefaultHolidayEntitlementHours());

      employee.addHolidayEntitlement(holidayEntitlement);
    }
  }

  @Transactional
  public void leave(Employee employee, LocalDate leaveDate) {
    employee.setServiceEndDate(leaveDate);
  }

  public boolean isManager(Employee employee) {
    final Employee currentUserEmployee = getCurrentUserEmployee();
    final Employee manager = employee.getManager();

    return manager != null && manager.equals(currentUserEmployee);
  }

  public List<EmployeeAnniversary> findAllWithUpcomingBirthdays() {
    // group by unique birthday
    final Map<LocalDate, List<Employee>> employeeMap = StreamSupport.stream(employeeRepository.findAll().spliterator(), false)
        .filter(employee -> employee.getDateOfBirth() != null)
        .collect(Collectors.groupingBy(employee -> employee.getDateOfBirth().withYear(LocalDate.now().getYear())));

    return getEmployeeAnniversaries(employeeMap, BIRTHDAYS);
  }

  public List<EmployeeAnniversary> findAllWithUpcomingServiceAnniversaries() {
    final Map<LocalDate, List<Employee>> employeeMap = StreamSupport.stream(employeeRepository.findAll().spliterator(), false)
        .filter(employee -> employee.getServiceStartDate() != null)
        .collect(Collectors.groupingBy(employee -> employee.getServiceStartDate().withYear(LocalDate.now().getYear())));

    return getEmployeeAnniversaries(employeeMap, BIRTHDAYS);
  }

  private List<EmployeeAnniversary> getEmployeeAnniversaries(Map<LocalDate, List<Employee>> employeeMap, int limit) {
    final List<LocalDate> uniqueDates = new ArrayList<>(employeeMap.keySet());
    Collections.sort(uniqueDates);

    final List<EmployeeAnniversary> results = new ArrayList<>();

    LocalDate now = LocalDate.now();
    int i = 0;
    while (results.size() < limit) {
      final Iterator<LocalDate> iterator = uniqueDates.iterator();

      // break if empty
      if (!iterator.hasNext()) {
        break;
      }

      while (results.size() < limit && iterator.hasNext()) {
        final LocalDate dateKey = iterator.next();
        LocalDate date = dateKey;

        // if second pass or more use the next year
        if (i > 0) {
          date = dateKey.withYear(now.getYear());
        } else if (dateKey.isBefore(now)) {
          continue;
        }

        final List<Employee> employees = employeeMap.get(dateKey);

        final List<EmployeeUser> employeeUsers = new ArrayList<>();
        for (Employee employee : employees) {
          final User user = userRepository.findById(employee.getUser())
              .orElseThrow(() -> new ResourceNotFoundException(employee.getUser(), User.class));
          employeeUsers.add(new EmployeeUser(employee, user));
        }

        results.add(new EmployeeAnniversary(date, employeeUsers));
      }

      now = now.plusYears(1);
      i++;
    }

    return results;
  }
}
