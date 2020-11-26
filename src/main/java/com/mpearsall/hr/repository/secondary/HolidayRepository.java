package com.mpearsall.hr.repository.secondary;

import com.mpearsall.hr.entity.secondary.employee.Employee;
import com.mpearsall.hr.entity.secondary.holiday.Holiday;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.util.Collection;

public interface HolidayRepository extends PagingAndSortingRepository<Holiday, Long> {

  String FIND_ALL_PENDING_HOLIDAYS_QUERY = "SELECT h FROM Holiday h" +
      " JOIN Employee e ON e = h.employee AND e.manager = ?1 AND e.serviceEndDate IS NULL" +
      " WHERE h.approved IS NULL ORDER BY h.createdDate DESC";

  @Query(FIND_ALL_PENDING_HOLIDAYS_QUERY)
  Page<Holiday> findAllPendingHolidays(Employee manager, Pageable pageable);

  @Query(FIND_ALL_PENDING_HOLIDAYS_QUERY)
  Collection<Holiday> findAllPendingHolidays(Employee employee);

  @Query("SELECT DISTINCT h FROM Holiday h" +
      " JOIN HolidayDate hd ON hd.holiday = h" +
      " AND hd.date >= ?1 AND hd.date <= ?2" +
      " WHERE h.employee = ?3" +
      " AND h.cancelled = false")
  Collection<Holiday> findAllInRange(LocalDate startDate, LocalDate endDate, Employee employee);

  @Query("SELECT h FROM HolidayDate hd" +
      " JOIN Holiday h ON h = hd.holiday" +
      " AND hd.date > ?1" +
      " WHERE h.employee = ?2" +
      " ORDER BY hd.date ASC")
  Collection<Holiday> findNext(LocalDate after, Employee employee);

  @Query("SELECT h FROM Holiday h" +
      " JOIN HolidayDate hd ON hd.holiday = h" +
      " AND hd.date >= ?1 AND hd.date <= ?2" +
      " JOIN Employee e ON e = h.employee" +
      " AND e.manager = ?3" +
      " WHERE h.approved = true")
  Collection<Holiday> findAllManagedInRange(LocalDate startDate, LocalDate endDate, Employee employee);

  @Query("SELECT h FROM Holiday h" +
      " WHERE h.employee = ?1" +
      " AND h.cancelled = false")
  Page<Holiday> findAllByEmployee(Employee employee, Pageable pageable);

  @Override
  @Query("SELECT h FROM Holiday h" +
      " WHERE h.employee.serviceEndDate IS NULL")
  Page<Holiday> findAll(Pageable pageable);
}
