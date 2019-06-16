package com.mpearsall.hr.service;

import com.mpearsall.hr.dto.CurrentUserHoliday;
import com.mpearsall.hr.entity.Employee;
import com.mpearsall.hr.entity.holiday.HolidayYear;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserHolidayService {
  private final EmployeeService employeeService;
  private final HolidayYearService holidayYearService;
  private final HolidayEntitlementService holidayEntitlementService;

  public CurrentUserHolidayService(EmployeeService employeeService, HolidayYearService holidayYearService,
                                   HolidayEntitlementService holidayEntitlementService) {
    this.employeeService = employeeService;
    this.holidayYearService = holidayYearService;
    this.holidayEntitlementService = holidayEntitlementService;
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
}
