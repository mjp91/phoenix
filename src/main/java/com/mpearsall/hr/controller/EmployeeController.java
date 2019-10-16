package com.mpearsall.hr.controller;

import com.mpearsall.hr.entity.employee.Employee;
import com.mpearsall.hr.repository.EmployeeRepository;
import com.mpearsall.hr.service.EmployeeService;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
@Secured("ROLE_ADMIN")
public class EmployeeController {
  private final EmployeeRepository employeeRepository;
  private final EmployeeService employeeService;

  public EmployeeController(EmployeeRepository employeeRepository, EmployeeService employeeService) {
    this.employeeRepository = employeeRepository;
    this.employeeService = employeeService;
  }

  @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Employee> index() {
    return employeeRepository.findAll();
  }

  @GetMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Employee byUserId(@PathVariable Long id) {
    return employeeRepository.findByUser_Id(id);
  }

  @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Employee save(@RequestBody Employee employee) {
    return employeeService.save(employee);
  }
}
