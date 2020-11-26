package com.mpearsall.hr.repository.secondary;

import com.mpearsall.hr.entity.secondary.Department;
import com.mpearsall.hr.entity.secondary.employee.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.validation.constraints.NotNull;
import java.util.Collection;

public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {
  Employee findByUser(@NotNull Long user);

  boolean existsByUser(@NotNull Long user);

  @Query("SELECT e FROM Employee e" +
      " WHERE e.department = ?1" +
      " AND e.serviceEndDate IS NULL")
  Collection<Employee> findByDepartment(Department department);

  @Override
  @Query("SELECT e FROM Employee e" +
      " WHERE e.serviceEndDate IS NULL")
  Iterable<Employee> findAll();
}
