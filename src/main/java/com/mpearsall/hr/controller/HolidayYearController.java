package com.mpearsall.hr.controller;

import com.mpearsall.hr.entity.holiday.HolidayYear;
import com.mpearsall.hr.repository.HolidayYearRepository;
import com.mpearsall.hr.service.HolidayYearService;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/holiday-year")
public class HolidayYearController {
  private final HolidayYearRepository holidayYearRepository;
  private final HolidayYearService holidayYearService;

  public HolidayYearController(HolidayYearRepository holidayYearRepository, HolidayYearService holidayYearService) {
    this.holidayYearRepository = holidayYearRepository;
    this.holidayYearService = holidayYearService;
  }

  @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<HolidayYear> holidayYears() {
    return holidayYearRepository.findAll();
  }

  @Secured("ROLE_ADMIN")
  @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public HolidayYear save(@RequestBody HolidayYear holidayYear) {
    return holidayYearService.save(holidayYear);
  }
}
