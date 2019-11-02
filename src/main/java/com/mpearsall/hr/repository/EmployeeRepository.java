package com.mpearsall.hr.repository;

import com.mpearsall.hr.entity.Department;
import com.mpearsall.hr.entity.employee.Employee;
import com.mpearsall.hr.entity.user.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.validation.constraints.NotNull;
import java.util.Collection;

public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {
  Employee findByUser(@NotNull User user);

  boolean existsByUser(@NotNull User user);

  Employee findByUser_Username(@NotNull String username);

  Employee findByUser_Id(Long userId);

  Collection<Employee> findAllByManager(Employee manager);

  Collection<Employee> findByDepartment(Department department);
}
