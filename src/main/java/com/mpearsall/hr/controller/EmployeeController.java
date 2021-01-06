package com.mpearsall.hr.controller;

import com.mpearsall.hr.dto.DtoMapper;
import com.mpearsall.hr.dto.EmployeeAnniversary;
import com.mpearsall.hr.dto.EmployeeDto;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
  private final EmployeeRepository employeeRepository;
  private final EmployeeService employeeService;
  private final UserRepository userRepository;
  private final DtoMapper dtoMapper;

  public EmployeeController(EmployeeRepository employeeRepository, EmployeeService employeeService,
                            UserRepository userRepository, DtoMapper dtoMapper) {
    this.employeeRepository = employeeRepository;
    this.employeeService = employeeService;
    this.userRepository = userRepository;
    this.dtoMapper = dtoMapper;
  }

  @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<EmployeeDto> index() {
    return toDto(employeeRepository.findAll());
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public EmployeeDto findById(@PathVariable Long id) {
    return dtoMapper.toEmployeeDto(employeeRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(id, Employee.class)));
  }

  @GetMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public EmployeeDto byUserId(@PathVariable Long id) {
    return dtoMapper.toEmployeeDto(employeeRepository.findByUser(id)
        .orElseThrow(() -> new ResourceNotFoundException(id, Employee.class)));
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
  public EmployeeDto save(@RequestBody EmployeeDto employee) {
    return dtoMapper.toEmployeeDto(employeeService.save(dtoMapper.toEmployee(employee)));
  }

  @PostMapping(value = "/leave", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Secured({Role.ADMIN, Role.SUPER_ADMIN})
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

  private List<EmployeeDto> toDto(Iterable<Employee> employees) {
    return StreamSupport.stream(employees.spliterator(), false)
        .map(dtoMapper::toEmployeeDto)
        .collect(Collectors.toList());
  }
}
