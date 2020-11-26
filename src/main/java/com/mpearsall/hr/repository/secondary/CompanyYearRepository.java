package com.mpearsall.hr.repository.secondary;

import com.mpearsall.hr.entity.secondary.holiday.CompanyYear;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public interface CompanyYearRepository extends PagingAndSortingRepository<CompanyYear, Long> {
  @Query("SELECT CASE WHEN COUNT(hy) > 0 THEN true ELSE false END" +
      " FROM CompanyYear hy WHERE hy.yearStart <= ?1 AND hy.yearEnd >= ?1")
  boolean existsByDate(@NotNull LocalDate date);

  @Query("SELECT hy FROM CompanyYear hy WHERE hy.yearStart <= ?1 AND hy.yearEnd >= ?1")
  CompanyYear findForDate(@NotNull LocalDate date);

  @Query("SELECT cy FROM CompanyYear cy WHERE cy.yearEnd > CURRENT_DATE()")
  Iterable<CompanyYear> findAllCurrentAndFuture();
}
