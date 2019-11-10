package com.mpearsall.hr.factory;

import com.mpearsall.hr.entity.holiday.CompanyYear;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class CompanyYearFactory {
  private CompanyYearFactory() {
  }

  public static CompanyYear generateForCurrentYear() {
    final LocalDate now = LocalDate.now();

    final CompanyYear companyYear = new CompanyYear();
    companyYear.setYearStart(now.with(TemporalAdjusters.firstDayOfYear()));
    companyYear.setYearEnd(now.with(TemporalAdjusters.lastDayOfYear()));

    return companyYear;
  }
}
