package com.mpearsall.hr.controller;

import com.mpearsall.hr.dto.*;
import com.mpearsall.hr.entity.primary.user.Role;
import com.mpearsall.hr.entity.secondary.employee.Employee;
import com.mpearsall.hr.entity.secondary.holiday.Holiday;
import com.mpearsall.hr.repository.secondary.HolidayRepository;
import com.mpearsall.hr.service.CurrentUserHolidayService;
import com.mpearsall.hr.service.EmployeeService;
import com.mpearsall.hr.service.HolidayService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/holiday")
public class HolidayController {
  private final HolidayService holidayService;
  private final EmployeeService employeeService;
  private final CurrentUserHolidayService currentUserHolidayService;
  private final HolidayRepository holidayRepository;
  private final DtoMapper dtoMapper;

  public HolidayController(HolidayService holidayService, EmployeeService employeeService,
                           CurrentUserHolidayService currentUserHolidayService, HolidayRepository holidayRepository, DtoMapper dtoMapper) {
    this.holidayService = holidayService;
    this.employeeService = employeeService;
    this.currentUserHolidayService = currentUserHolidayService;
    this.holidayRepository = holidayRepository;
    this.dtoMapper = dtoMapper;
  }

  @GetMapping(path = "/current", produces = MediaType.APPLICATION_JSON_VALUE)
  public CurrentUserHoliday getCurrentUserHoliday() {
    return currentUserHolidayService.getCurrentUserHoliday();
  }

  @GetMapping(path = "/today", produces = MediaType.APPLICATION_JSON_VALUE)
  public TodaysHolidays getTodaysHolidays() {
    return currentUserHolidayService.getTodaysHolidays();
  }

  @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<HolidayDto> getHolidays() {
    // get user's employee record
    final Employee employee = employeeService.getCurrentUserEmployee();

    return toDto(holidayRepository.findAllByEmployee(employee, PageRequest.of(0, 100)));
  }

  @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
  @Secured({Role.ADMIN, Role.SUPER_ADMIN})
  public Iterable<HolidayDto> getAllHolidays() {
    return toDto(holidayRepository.findAll(Pageable.unpaged()));
  }

  @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public HolidayDto createHoliday(@RequestBody @Valid HolidayRequest holidayRequest) {
    return dtoMapper.toHolidayDto(holidayService.requestToHoliday(holidayRequest));
  }

  @PatchMapping(path = "/cancel/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public HolidayDto cancelHoliday(@PathVariable("id") Long id) {
    return dtoMapper.toHolidayDto(holidayService.cancelHoliday(id));
  }

  @GetMapping(path = "/requests/page", produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<HolidayDto> getRequests(@RequestParam int page, @RequestParam int size) {
    // get user's employee record
    final Employee employee = employeeService.getCurrentUserEmployee();

    return toDto(holidayRepository.findAllPendingHolidays(employee, PageRequest.of(page, size)));
  }

  @GetMapping(path = "/requests", produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<HolidayDto> getRequests() {
    // get user's employee record
    final Employee employee = employeeService.getCurrentUserEmployee();

    return toDto(holidayRepository.findAllPendingHolidays(employee));
  }

  @PatchMapping(value = "/approve/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public HolidayDto approveHoliday(@PathVariable("id") Long id) {
    return dtoMapper.toHolidayDto(holidayService.approveHoliday(id));
  }

  @PatchMapping(value = "/disapprove/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public HolidayDto disapproveHoliday(@PathVariable("id") Long id, @RequestBody Map<String, Object> body) {
    return dtoMapper.toHolidayDto(holidayService.disapproveHoliday(id, (String) body.get("reason")));
  }

  private List<HolidayDto> toDto(Iterable<Holiday> holidays) {
    return StreamSupport.stream(holidays.spliterator(), false)
        .map(dtoMapper::toHolidayDto)
        .collect(Collectors.toList());
  }
}
