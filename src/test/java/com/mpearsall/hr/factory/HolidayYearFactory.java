package com.mpearsall.hr.factory;

import com.mpearsall.hr.entity.holiday.HolidayYear;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class HolidayYearFactory {
  private HolidayYearFactory() {
  }

  public static HolidayYear generateForCurrentYear() {
    final LocalDate now = LocalDate.now();

    final HolidayYear holidayYear = new HolidayYear();
    holidayYear.setYearStart(now.with(TemporalAdjusters.firstDayOfYear()));
    holidayYear.setYearEnd(now.with(TemporalAdjusters.lastDayOfYear()));

    return holidayYear;
  }
}
