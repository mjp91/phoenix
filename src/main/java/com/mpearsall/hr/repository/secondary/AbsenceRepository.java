package com.mpearsall.hr.repository.secondary;

import com.mpearsall.hr.entity.secondary.absence.Absence;
import com.mpearsall.hr.entity.secondary.employee.Employee;
import com.mpearsall.hr.entity.secondary.holiday.CompanyYear;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.util.Collection;

public interface AbsenceRepository extends PagingAndSortingRepository<Absence, Long> {
  @Query("SELECT a FROM Absence a" +
      " WHERE a.employee = ?1" +
      " AND a.cancelled = false")
  Page<Absence> findAllByEmployee(Employee employee, Pageable pageable);

  @Query("SELECT a FROM Absence a" +
      " WHERE a.employee = ?1" +
      " AND a.companyYear = ?2" +
      " AND a.cancelled = false")
  Collection<Absence> findAllByEmployeeAndCompanyYear(Employee employee, CompanyYear companyYear);

  @Query("SELECT a FROM Absence a" +
      " WHERE a.start >= ?1 AND a.end <= ?2" +
      " AND a.employee = ?3" +
      " AND a.cancelled = false ")
  Collection<Absence> findAllInRange(LocalDate start, LocalDate end, Employee employee);

  @Query("SELECT a FROM Absence a" +
      " JOIN Employee e ON e = a.employee AND e.manager = ?1" +
      " WHERE a.authorized IS NULL" +
      " AND a.cancelled = false" +
      " ORDER BY a.createdDate DESC")
  Collection<Absence> findAllPendingAuthorisationForManager(Employee employee);

  @Query("SELECT a FROM Absence a" +
      " WHERE a.authorized IS NULL" +
      " AND a.cancelled = false" +
      " AND a.employee.serviceEndDate IS NULL" +
      " ORDER BY a.createdDate DESC")
  Collection<Absence> findAllPendingAuthorisation();

  @Override
  @Query("SELECT a FROM Absence a" +
      " WHERE a.employee.serviceEndDate IS NULL")
  Page<Absence> findAll(Pageable pageable);
}
