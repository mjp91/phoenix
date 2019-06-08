package com.mpearsall.hr.service;

import com.mpearsall.hr.entity.Employee;
import com.mpearsall.hr.entity.User;
import com.mpearsall.hr.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
  private final EmployeeRepository employeeRepository;
  private final UserService userService;

  public EmployeeService(EmployeeRepository employeeRepository, UserService userService) {
    this.employeeRepository = employeeRepository;
    this.userService = userService;
  }

  public Employee getCurrentUserEmployee() {
    final User currentUser = userService.getCurrentUser();

    Employee employee = null;
    if (currentUser != null) {
      employee = employeeRepository.findByUser(currentUser);
    }

    return employee;
  }
}
