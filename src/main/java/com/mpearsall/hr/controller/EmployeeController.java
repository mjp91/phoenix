package com.mpearsall.hr.controller;

import com.mpearsall.hr.entity.Employee;
import com.mpearsall.hr.repository.EmployeeRepository;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
@Secured("ROLE_ADMIN")
public class EmployeeController {
  private final EmployeeRepository employeeRepository;

  public EmployeeController(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public Iterable<Employee> index() {
    return employeeRepository.findAll();
  }

  @GetMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public Employee byUserId(@PathVariable Long id) {
    return employeeRepository.findByUser_Id(id);
  }

  @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public Employee save(@RequestBody Employee employee) {
    return employeeRepository.save(employee);
  }
}
