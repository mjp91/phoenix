package com.mpearsall.hr.repository;

import com.mpearsall.hr.entity.holiday.HolidayYear;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public interface HolidayYearRepository extends PagingAndSortingRepository<HolidayYear, Long> {
  HolidayYear findByYearStartBeforeAndYearEndAfter(@NotNull LocalDate yearStart, @NotNull LocalDate yearEnd);
}