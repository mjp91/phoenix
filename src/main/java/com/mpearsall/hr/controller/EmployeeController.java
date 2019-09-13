package com.mpearsall.hr.controller;

import com.mpearsall.hr.entity.Employee;
import com.mpearsall.hr.repository.EmployeeRepository;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
@Secured("ROLE_ADMIN")
public class EmployeeController {
  private final EmployeeRepository employeeRepository;

  public EmployeeController(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  @GetMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public Employee byUserId(@PathVariable Long id) {
    return employeeRepository.findByUser_Id(id);
  }
}
