package com.mpearsall.hr.repository;

import com.mpearsall.hr.entity.Employee;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.validation.constraints.NotNull;
import java.util.Collection;

public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {
  Employee findByUser_Username(@NotNull String username);

  Collection<Employee> findAllByManager(Employee manager);
}
