package com.mpearsall.hr.controller;

import com.mpearsall.hr.dto.CurrentUserHoliday;
import com.mpearsall.hr.dto.HolidayRequest;
import com.mpearsall.hr.dto.TodaysHolidays;
import com.mpearsall.hr.entity.Employee;
import com.mpearsall.hr.entity.holiday.Holiday;
import com.mpearsall.hr.repository.HolidayRepository;
import com.mpearsall.hr.service.CurrentUserHolidayService;
import com.mpearsall.hr.service.EmployeeService;
import com.mpearsall.hr.service.HolidayService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

  @GetMapping(path = "/today", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public TodaysHolidays getTodaysHolidays() {
    return currentUserHolidayService.getTodaysHolidays();
  }

  @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public Page<Holiday> getHolidays() {
    // get user's employee record
    final Employee employee = employeeService.getCurrentUserEmployee();

    return holidayRepository.findAllByEmployee(employee, PageRequest.of(0, 100));
  }

  @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public Holiday createHoliday(@RequestBody @Valid HolidayRequest holidayRequest) {
    return holidayService.requestToHoliday(holidayRequest);
  }

  @PatchMapping(path = "/cancel/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public Holiday cancelHoliday(@PathVariable("id") Long id) {
    return holidayService.cancelHoliday(id);
  }

  @GetMapping(path = "/requests/page", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public Page<Holiday> getRequests(@RequestParam int page, @RequestParam int size) {
    // get user's employee record
    final Employee employee = employeeService.getCurrentUserEmployee();

    return holidayRepository.findAllPendingHolidays(employee, PageRequest.of(page, size));
  }

  @GetMapping(path = "/requests", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public Collection<Holiday> getRequests() {
    // get user's employee record
    final Employee employee = employeeService.getCurrentUserEmployee();

    return holidayRepository.findAllPendingHolidays(employee);
  }

  @PatchMapping(value = "/approve/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public Holiday approveHoliday(@PathVariable("id") Long id) {
    return holidayService.approveHoliday(id);
  }

  @PatchMapping(value = "/disapprove/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public Holiday disapproveHoliday(@PathVariable("id") Long id) {
    return holidayService.disapproveHoliday(id);
  }
}
