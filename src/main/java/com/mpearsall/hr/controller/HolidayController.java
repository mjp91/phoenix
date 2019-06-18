package com.mpearsall.hr.controller;

import com.mpearsall.hr.dto.CurrentUserHoliday;
import com.mpearsall.hr.dto.HolidayRequest;
import com.mpearsall.hr.entity.Employee;
import com.mpearsall.hr.entity.holiday.Holiday;
import com.mpearsall.hr.repository.HolidayRepository;
import com.mpearsall.hr.service.CurrentUserHolidayService;
import com.mpearsall.hr.service.EmployeeService;
import com.mpearsall.hr.service.HolidayService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/holiday")
public class HolidayController {
  private final HolidayService holidayService;
  private final EmployeeService employeeService;
  private final CurrentUserHolidayService currentUserHolidayService;
  private final HolidayRepository holidayRepository;

  public HolidayController(HolidayService holidayService, EmployeeService employeeService,
                           CurrentUserHolidayService currentUserHolidayService, HolidayRepository holidayRepository) {
    this.holidayService = holidayService;
    this.employeeService = employeeService;
    this.currentUserHolidayService = currentUserHolidayService;
    this.holidayRepository = holidayRepository;
  }

  @GetMapping(path = "/current", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public CurrentUserHoliday getCurrentUserHoliday() {
    return currentUserHolidayService.getCurrentUserHoliday();
  }

  @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public Collection<Holiday> getHolidays() {
    // get user's employee record
    final Employee employee = employeeService.getCurrentUserEmployee();

    return employee.getHolidays();
  }

  @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public Holiday createHoliday(@RequestBody @Valid HolidayRequest holidayRequest) {
    return holidayService.requestToHoliday(holidayRequest);
  }

  @GetMapping(path = "/requests", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public Collection<Holiday> getRequests() {
    // get user's employee record
    final Employee employee = employeeService.getCurrentUserEmployee();

    return holidayRepository.findAllPendingHolidays(employee);
  }
}
