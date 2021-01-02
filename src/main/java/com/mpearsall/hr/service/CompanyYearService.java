package com.mpearsall.hr.service;

import com.mpearsall.hr.entity.secondary.holiday.CompanyYear;
import com.mpearsall.hr.exception.InvalidDetailsException;
import com.mpearsall.hr.repository.secondary.CompanyYearRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class CompanyYearService {
  private final CompanyYearRepository companyYearRepository;

  public CompanyYearService(CompanyYearRepository companyYearRepository) {
    this.companyYearRepository = companyYearRepository;
  }

  public CompanyYear getCurrentCompanyYear() {
    return companyYearRepository.findForDate(LocalDate.now());
  }

  @Transactional
  public CompanyYear save(CompanyYear companyYear) {
    final LocalDate yearStart = companyYear.getYearStart();
    final LocalDate yearEnd = companyYear.getYearEnd();

    if (yearEnd.isBefore(yearStart)) {
      throw new InvalidDetailsException("Year end should occur after year start");
    }

    final boolean exists = companyYearRepository.existsByDate(yearStart)
        || companyYearRepository.existsByDate(yearEnd);

    if (exists) {
      throw new InvalidDetailsException("An existing year overlaps with the requested dates");
    }

    return companyYearRepository.save(companyYear);
  }
}
