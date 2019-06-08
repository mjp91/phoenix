package com.mpearsall.hr.controller;

import com.mpearsall.hr.dto.CurrentUserHoliday;
import com.mpearsall.hr.entity.Employee;
import com.mpearsall.hr.entity.holiday.HolidayYear;
import com.mpearsall.hr.service.EmployeeService;
import com.mpearsall.hr.service.HolidayEntitlementService;
import com.mpearsall.hr.service.HolidayService;
import com.mpearsall.hr.service.HolidayYearService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/holiday")
public class HolidayController {
  private final HolidayEntitlementService holidayEntitlementService;
  private final HolidayService holidayService;
  private final EmployeeService employeeService;
  private final HolidayYearService holidayYearService;

  public HolidayController(HolidayEntitlementService holidayEntitlementService,
                           HolidayService holidayService, EmployeeService employeeService,
                           HolidayYearService holidayYearService) {
    this.holidayEntitlementService = holidayEntitlementService;
    this.holidayService = holidayService;
    this.employeeService = employeeService;
    this.holidayYearService = holidayYearService;
  }

  @GetMapping(path = "/current", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public CurrentUserHoliday getCurrentUserHoliday() {
    // get user's employee record
    final Employee employee = employeeService.getCurrentUserEmployee();

    // get current holiday year
    final HolidayYear holidayYear = holidayYearService.getCurrentHolidayYear();

    final Double holidayUsed = holidayService.calculateHolidayUsed(employee, holidayYear);
    final Double entitlement = holidayEntitlementService.calculateHolidayEntitlementInDays(employee, holidayYear);
    return new CurrentUserHoliday(holidayUsed, entitlement);
  }
}
