package com.mpearsall.hr.repository;

import com.mpearsall.hr.entity.Department;
import com.mpearsall.hr.entity.employee.Employee;
import com.mpearsall.hr.entity.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.validation.constraints.NotNull;
import java.util.Collection;

public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {
  Employee findByUser(@NotNull User user);

  boolean existsByUser(@NotNull User user);

  Employee findByUser_Username(@NotNull String username);

  Employee findByUser_Id(Long userId);

  @Query("SELECT e FROM Employee e" +
      " WHERE e.department = ?1" +
      " AND e.serviceEndDate IS NULL")
  Collection<Employee> findByDepartment(Department department);

  @Override
  @Query("SELECT e FROM Employee e" +
      " WHERE e.serviceEndDate IS NULL")
  Iterable<Employee> findAll();
}
