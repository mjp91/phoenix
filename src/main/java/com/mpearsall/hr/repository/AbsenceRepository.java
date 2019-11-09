package com.mpearsall.hr.repository;

import com.mpearsall.hr.entity.absence.Absence;
import com.mpearsall.hr.entity.employee.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AbsenceRepository extends PagingAndSortingRepository<Absence, Long> {
  Page<Absence> findAllByEmployee(Employee employee, Pageable pageable);
}
