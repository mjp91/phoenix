package com.mpearsall.hr.repository;

import com.mpearsall.hr.entity.Employee;
import com.mpearsall.hr.entity.holiday.Holiday;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;

public interface HolidayRepository extends PagingAndSortingRepository<Holiday, Long> {
  @Query("SELECT h FROM Holiday h" +
      " JOIN Employee e ON e = h.employee AND e.manager = ?1" +
      " WHERE h.approved IS NULL")
  Collection<Holiday> findAllPendingHolidays(Employee manager);
}
