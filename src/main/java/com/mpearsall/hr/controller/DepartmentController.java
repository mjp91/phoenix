package com.mpearsall.hr.controller;

import com.mpearsall.hr.dto.EmployeeSearchNode;
import com.mpearsall.hr.entity.primary.user.Role;
import com.mpearsall.hr.entity.secondary.Department;
import com.mpearsall.hr.repository.secondary.DepartmentRepository;
import com.mpearsall.hr.service.DepartmentService;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/department")
public class DepartmentController {
  private final DepartmentRepository departmentRepository;
  private final DepartmentService departmentService;

  public DepartmentController(DepartmentRepository departmentRepository, DepartmentService departmentService) {
    this.departmentRepository = departmentRepository;
    this.departmentService = departmentService;
  }

  @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Department> index() {
    return departmentRepository.findAll();
  }

  @GetMapping(path = "/employee", produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<EmployeeSearchNode> departmentEmployees() {
    return departmentService.getDepartmentEmployees();
  }

  @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @Secured({Role.ADMIN, Role.SUPER_ADMIN})
  public Department save(@RequestBody Department department) {
    return departmentRepository.save(department);
  }
}
