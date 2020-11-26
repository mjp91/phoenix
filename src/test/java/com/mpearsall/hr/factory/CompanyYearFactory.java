package com.mpearsall.hr.factory;

import com.mpearsall.hr.entity.secondary.holiday.CompanyYear;
import com.mpearsall.hr.repository.secondary.CompanyYearRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

@Component
public class CompanyYearFactory {
  private final CompanyYearRepository companyYearRepository;

  public CompanyYearFactory(CompanyYearRepository companyYearRepository) {
    this.companyYearRepository = companyYearRepository;
  }

  public CompanyYear generateForCurrentYear() {
    final LocalDate now = LocalDate.now();

    CompanyYear companyYear = companyYearRepository.findForDate(now);

    if (companyYear == null) {
      companyYear = new CompanyYear();
      companyYear.setName(String.valueOf(now.getYear()));
      companyYear.setYearStart(now.with(TemporalAdjusters.firstDayOfYear()));
      companyYear.setYearEnd(now.with(TemporalAdjusters.lastDayOfYear()));
      companyYear = companyYearRepository.save(companyYear);
    }

    return companyYear;
  }
}
