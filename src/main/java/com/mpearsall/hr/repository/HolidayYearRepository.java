package com.mpearsall.hr.repository;

import com.mpearsall.hr.entity.holiday.HolidayYear;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public interface HolidayYearRepository extends PagingAndSortingRepository<HolidayYear, Long> {
  @Query("SELECT CASE WHEN COUNT(hy) > 0 THEN true ELSE false END" +
      " FROM HolidayYear hy WHERE hy.yearStart <= ?1 AND hy.yearEnd >= ?1")
  boolean existsByDate(@NotNull LocalDate date);

  @Query("SELECT hy FROM HolidayYear hy WHERE hy.yearStart <= ?1 AND hy.yearEnd >= ?1")
  HolidayYear findForDate(@NotNull LocalDate date);
}
