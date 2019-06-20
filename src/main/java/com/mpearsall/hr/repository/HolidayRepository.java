package com.mpearsall.hr.repository;

import com.mpearsall.hr.entity.Employee;
import com.mpearsall.hr.entity.holiday.Holiday;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;

public interface HolidayRepository extends PagingAndSortingRepository<Holiday, Long> {

  String FIND_ALL_PENDING_HOLIDAYS_QUERY = "SELECT h FROM Holiday h" +
      " JOIN Employee e ON e = h.employee AND e.manager = ?1" +
      " WHERE h.approved IS NULL ORDER BY h.createdDate DESC";

  @Query(FIND_ALL_PENDING_HOLIDAYS_QUERY)
  Page<Holiday> findAllPendingHolidays(Employee manager, Pageable pageable);

  @Query(FIND_ALL_PENDING_HOLIDAYS_QUERY)
  Collection<Holiday> findAllPendingHolidays(Employee employee);
}