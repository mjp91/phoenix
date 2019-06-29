package com.mpearsall.hr.service;

import com.mpearsall.hr.dto.CurrentUserHoliday;
import com.mpearsall.hr.dto.TodaysHolidays;
import com.mpearsall.hr.entity.Employee;
import com.mpearsall.hr.entity.holiday.Holiday;
import com.mpearsall.hr.entity.holiday.HolidayYear;
import com.mpearsall.hr.repository.HolidayRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;

@Service
public class CurrentUserHolidayService {
  private final EmployeeService employeeService;
  private final HolidayYearService holidayYearService;

  private HolidayRepository holidayRepository;

  public CurrentUserHolidayService(EmployeeService employeeService, HolidayYearService holidayYearService,
                                   HolidayRepository holidayRepository) {
    this.employeeService = employeeService;
    this.holidayYearService = holidayYearService;
    this.holidayRepository = holidayRepository;
  }

  public CurrentUserHoliday getCurrentUserHoliday() {
    // get user's employee record
    final Employee employee = employeeService.getCurrentUserEmployee();

    // get current holiday year
    final HolidayYear holidayYear = holidayYearService.getCurrentHolidayYear();

    final Double holidayUsed = HolidayService.calculateHolidayUsed(employee, holidayYear);
    final Double entitlement = HolidayEntitlementService.calculateHolidayEntitlementInDays(employee, holidayYear);

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
