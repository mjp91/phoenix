package com.mpearsall.hr.service;

import com.mpearsall.hr.dto.CurrentUserHoliday;
import com.mpearsall.hr.dto.TodaysHolidays;
import com.mpearsall.hr.entity.secondary.employee.Employee;
import com.mpearsall.hr.entity.secondary.holiday.CompanyYear;
import com.mpearsall.hr.entity.secondary.holiday.Holiday;
import com.mpearsall.hr.repository.secondary.HolidayRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;

@Service
public class CurrentUserHolidayService {
  private final EmployeeService employeeService;
  private final CompanyYearService companyYearService;

  private final HolidayRepository holidayRepository;

  public CurrentUserHolidayService(EmployeeService employeeService, CompanyYearService companyYearService,
                                   HolidayRepository holidayRepository) {
    this.employeeService = employeeService;
    this.companyYearService = companyYearService;
    this.holidayRepository = holidayRepository;
  }

  public CurrentUserHoliday getCurrentUserHoliday() {
    // get user's employee record
    final Employee employee = employeeService.getCurrentUserEmployee();

    // get current company Year
    final CompanyYear companyYear = companyYearService.getCurrentCompanyYear();

    final Double holidayUsed = HolidayService.calculateHolidayUsed(employee, companyYear);
    final Double entitlement = HolidayEntitlementService.calculateHolidayEntitlementInDays(employee, companyYear);

    return new CurrentUserHoliday(holidayUsed, entitlement);
  }

  public TodaysHolidays getTodaysHolidays() {
    final Employee currentUserEmployee = employeeService.getCurrentUserEmployee();

    final LocalDate now = LocalDate.now();
    final Holiday todaysHoliday = holidayRepository.findAllInRange(now, now, currentUserEmployee).stream()
        .findFirst().orElse(null);

    final Holiday nextHoliday = holidayRepository.findNext(LocalDate.now(), currentUserEmployee).stream()
        .findFirst().orElse(null);

    final Collection<Holiday> managedInRange = holidayRepository.findAllManagedInRange(now, now, currentUserEmployee);

    return new TodaysHolidays(todaysHoliday, nextHoliday, managedInRange);
  }
}
