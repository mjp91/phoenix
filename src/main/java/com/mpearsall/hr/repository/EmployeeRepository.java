package com.mpearsall.hr.repository;

import com.mpearsall.hr.entity.Employee;
import com.mpearsall.hr.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.validation.constraints.NotNull;
import java.util.Collection;

public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {
  Employee findByUser(@NotNull User user);

  Collection<Employee> findAllByManager(Employee manager);
}
