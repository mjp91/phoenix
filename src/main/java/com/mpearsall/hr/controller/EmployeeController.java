package com.mpearsall.hr.controller;

import com.mpearsall.hr.dto.EmployeeAnniversary;
import com.mpearsall.hr.dto.EmployeeUser;
import com.mpearsall.hr.dto.Leaver;
import com.mpearsall.hr.entity.primary.user.Role;
import com.mpearsall.hr.entity.primary.user.User;
import com.mpearsall.hr.entity.secondary.employee.Employee;
import com.mpearsall.hr.exception.PermissionException;
import com.mpearsall.hr.exception.ResourceNotFoundException;
import com.mpearsall.hr.repository.primary.UserRepository;
import com.mpearsall.hr.repository.secondary.EmployeeRepository;
import com.mpearsall.hr.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
  private final EmployeeRepository employeeRepository;
  private final EmployeeService employeeService;
  private final UserRepository userRepository;

  public EmployeeController(EmployeeRepository employeeRepository, EmployeeService employeeService,
                            UserRepository userRepository) {
    this.employeeRepository = employeeRepository;
    this.employeeService = employeeService;
    this.userRepository = userRepository;
  }

  @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Employee> index() {
    return employeeRepository.findAll();
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public EmployeeUser findById(@PathVariable Long id) {
    final Employee employee = employeeRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(id, Employee.class));

    final User user = userRepository.findById(employee.getUser())
        .orElseThrow(() -> new ResourceNotFoundException(employee.getUser(), User.class));

    return new EmployeeUser(employee, user);
  }

  @GetMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Employee byUserId(@PathVariable Long id) {
    return employeeRepository.findByUser(id);
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

  @PostMapping(value = "/leave", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Secured(Role.ADMIN)
  public void leaver(@Valid @RequestBody Leaver leaver) {
    leave(leaver);
  }

  private void leave(Leaver leaver) {
    final Employee employee = employeeRepository.findById(leaver.getEmployeeId()).orElseThrow();

    if (employeeService.getCurrentUserEmployee().equals(employee)) {
      throw new PermissionException("User cannot disable themselves");
    }

    employeeService.leave(employee, leaver.getLeaveDate());

    final User user = userRepository.findById(employee.getUser())
        .orElseThrow(() -> new ResourceNotFoundException(employee.getUser(), User.class));
    user.setEnabled(false);
    userRepository.save(user);
  }
}
