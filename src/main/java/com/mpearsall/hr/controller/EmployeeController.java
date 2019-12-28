package com.mpearsall.hr.controller;

import com.mpearsall.hr.dto.EmployeeAnniversary;
import com.mpearsall.hr.entity.employee.Employee;
import com.mpearsall.hr.repository.EmployeeRepository;
import com.mpearsall.hr.service.EmployeeService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employee")
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

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Optional<Employee> findById(@PathVariable Long id) {
    return employeeRepository.findById(id);
  }

  @GetMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Employee byUserId(@PathVariable Long id) {
    return employeeRepository.findByUser_Id(id);
  }

  @GetMapping(value = "/upcoming-birthdays", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<EmployeeAnniversary> byUpcomingBirthday() {
    return employeeService.findAllWithUpcomingBirthdays();
  }

  @GetMapping(value = "/upcoming-service-anniversaries", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<EmployeeAnniversary> byUpcomingServiceAnniversaries() {
    return employeeService.findAllWithUpcomingServiceAnniversaries();
  }

  @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Employee save(@RequestBody Employee employee) {
    return employeeService.save(employee);
  }
}
