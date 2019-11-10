package com.mpearsall.hr.repository;

import com.mpearsall.hr.entity.absence.Absence;
import com.mpearsall.hr.entity.employee.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.util.Collection;

public interface AbsenceRepository extends PagingAndSortingRepository<Absence, Long> {
  Page<Absence> findAllByEmployee(Employee employee, Pageable pageable);

  @Query("SELECT a FROM Absence a" +
      " WHERE a.start >= ?1 AND a.end <= ?2" +
      " AND a.employee = ?3")
  Collection<Absence> findAllInRange(LocalDate start, LocalDate end, Employee employee);
}