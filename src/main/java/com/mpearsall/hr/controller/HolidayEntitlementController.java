package com.mpearsall.hr.controller;

import com.mpearsall.hr.entity.secondary.holiday.HolidayEntitlement;
import com.mpearsall.hr.repository.secondary.HolidayEntitlementRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/holiday-entitlement")
public class HolidayEntitlementController {
  private final HolidayEntitlementRepository holidayEntitlementRepository;

  public HolidayEntitlementController(HolidayEntitlementRepository holidayEntitlementRepository) {
    this.holidayEntitlementRepository = holidayEntitlementRepository;
  }

  @GetMapping(path = "/employee/{employeeId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<HolidayEntitlement> byEmployee(@PathVariable Long employeeId) {
    return holidayEntitlementRepository.findAllByEmployee_Id(employeeId);
  }

  @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public HolidayEntitlement save(@RequestBody HolidayEntitlement holidayEntitlement) {
    return holidayEntitlementRepository.save(holidayEntitlement);
  }
}
