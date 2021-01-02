package com.mpearsall.hr.controller;

import com.mpearsall.hr.dto.DtoMapper;
import com.mpearsall.hr.dto.HolidayEntitlementDto;
import com.mpearsall.hr.entity.secondary.holiday.HolidayEntitlement;
import com.mpearsall.hr.repository.secondary.HolidayEntitlementRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/holiday-entitlement")
public class HolidayEntitlementController {
  private final HolidayEntitlementRepository holidayEntitlementRepository;
  private final DtoMapper dtoMapper;

  public HolidayEntitlementController(HolidayEntitlementRepository holidayEntitlementRepository, DtoMapper dtoMapper) {
    this.holidayEntitlementRepository = holidayEntitlementRepository;
    this.dtoMapper = dtoMapper;
  }

  @GetMapping(path = "/employee/{employeeId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<HolidayEntitlementDto> byEmployee(@PathVariable Long employeeId) {
    return dtoMapper.toHolidayEntitlementDtos(holidayEntitlementRepository.findAllByEmployee_Id(employeeId));
  }

  @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public HolidayEntitlementDto save(@RequestBody HolidayEntitlementDto holidayEntitlementDto) {
    final HolidayEntitlement holidayEntitlement = dtoMapper.toHolidayEntitlement(holidayEntitlementDto);
    holidayEntitlementDto = dtoMapper.toHolidayEntitlementDto(holidayEntitlementRepository.save(holidayEntitlement));

    return holidayEntitlementDto;
  }
}
