package com.mpearsall.hr.service;

import com.mpearsall.hr.entity.holiday.HolidayYear;
import com.mpearsall.hr.exception.InvalidDetailsException;
import com.mpearsall.hr.repository.HolidayYearRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class HolidayYearService {
  private final HolidayYearRepository holidayYearRepository;

  public HolidayYearService(HolidayYearRepository holidayYearRepository) {
    this.holidayYearRepository = holidayYearRepository;
  }

  public HolidayYear getCurrentHolidayYear() {
    return holidayYearRepository.findForDate(LocalDate.now());
  }

  @Transactional
  public HolidayYear save(HolidayYear holidayYear) {
    final LocalDate yearStart = holidayYear.getYearStart();
    final LocalDate yearEnd = holidayYear.getYearEnd();

    if (yearEnd.isBefore(yearStart)) {
      throw new InvalidDetailsException("Year end should occur after year start");
    }

    final boolean exists = holidayYearRepository.existsByDate(yearStart)
        || holidayYearRepository.existsByDate(yearEnd);

    if (exists) {
      throw new InvalidDetailsException("An existing year overlaps with the requested dates");
    }

    return holidayYearRepository.save(holidayYear);
  }
}
