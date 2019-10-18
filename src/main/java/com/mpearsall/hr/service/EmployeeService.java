package com.mpearsall.hr.service;

import com.mpearsall.hr.entity.Company;
import com.mpearsall.hr.entity.employee.Employee;
import com.mpearsall.hr.entity.employee.EmployeeDay;
import com.mpearsall.hr.entity.employee.EmployeeWeek;
import com.mpearsall.hr.entity.holiday.HolidayEntitlement;
import com.mpearsall.hr.entity.user.Role;
import com.mpearsall.hr.entity.user.User;
import com.mpearsall.hr.exception.InvalidDetailsException;
import com.mpearsall.hr.exception.PermissionException;
import com.mpearsall.hr.repository.CompanyRepository;
import com.mpearsall.hr.repository.EmployeeRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
  private final EmployeeRepository employeeRepository;
  private final CompanyRepository companyRepository;
  private final UserService userService;
  private final HolidayYearService holidayYearService;

  public EmployeeService(EmployeeRepository employeeRepository, CompanyRepository companyRepository, UserService userService, HolidayYearService holidayYearService) {
    this.employeeRepository = employeeRepository;
    this.companyRepository = companyRepository;
    this.userService = userService;
    this.holidayYearService = holidayYearService;
  }

  public Employee getCurrentUserEmployee() {
    final UserDetails currentUser = userService.getCurrentUserDetails();

    Employee employee = null;
    if (currentUser != null) {
      employee = employeeRepository.findByUser_Username(currentUser.getUsername());
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
    if (!userService.currentUserHasRole(Role.ADMIN) && !employee.equals(getCurrentUserEmployee())) {
      throw new PermissionException("User must be admin to update other employees");
    }

    final Employee manager = employee.getManager() != null
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

  @Transactional
  public Employee createEmployee(User user) {
    final Company company = companyRepository.find();

    if (employeeRepository.existsByUser(user)) {
      throw new InvalidDetailsException("User already has an employee associated with them");
    }

    final Employee employee = new Employee();
    employee.setUser(user);
    employee.setEmployeeWeek(company.getDefaultEmployeeWeek());

    final HolidayEntitlement holidayEntitlement = new HolidayEntitlement();
    holidayEntitlement.setHolidayYear(holidayYearService.getCurrentHolidayYear());
    holidayEntitlement.setHolidayEntitlementHours(company.getDefaultHolidayEntitlementHours());

    employee.setHolidayEntitlements(new ArrayList<>(List.of(holidayEntitlement)));

    return save(employee);
  }
}
