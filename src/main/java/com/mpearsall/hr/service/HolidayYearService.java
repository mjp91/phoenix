package com.mpearsall.hr.service;

import com.mpearsall.hr.entity.holiday.HolidayYear;
import com.mpearsall.hr.repository.HolidayYearRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class HolidayYearService {
  private final HolidayYearRepository holidayYearRepository;

  public HolidayYearService(HolidayYearRepository holidayYearRepository) {
    this.holidayYearRepository = holidayYearRepository;
  }

  public HolidayYear getCurrentHolidayYear() {
    final LocalDate now = LocalDate.now();
    return holidayYearRepository.findByYearStartBeforeAndYearEndAfter(now, now);
  }
}
