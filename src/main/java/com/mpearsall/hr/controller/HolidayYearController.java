package com.mpearsall.hr.controller;

import com.mpearsall.hr.entity.holiday.HolidayYear;
import com.mpearsall.hr.repository.HolidayYearRepository;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/holidayYear")
public class HolidayYearController {
  private HolidayYearRepository holidayYearRepository;

  public HolidayYearController(HolidayYearRepository holidayYearRepository) {
    this.holidayYearRepository = holidayYearRepository;
  }

  @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public Iterable<HolidayYear> holidayYears() {
    return holidayYearRepository.findAll();
  }

  @Secured("ROLE_ADMIN")
  @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public HolidayYear save(@RequestBody HolidayYear holidayYear) {
    return holidayYearRepository.save(holidayYear);
  }
}
