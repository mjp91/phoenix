package com.mpearsall.hr.controller;

import com.mpearsall.hr.entity.Department;
import com.mpearsall.hr.repository.DepartmentRepository;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/department")
public class DepartmentController {
  private final DepartmentRepository departmentRepository;

  public DepartmentController(DepartmentRepository departmentRepository) {
    this.departmentRepository = departmentRepository;
  }

  @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Department> index() {
    return departmentRepository.findAll();
  }

  @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @Secured("ROLE_ADMIN")
  public Department save(@RequestBody Department department) {
    return departmentRepository.save(department);
  }
}
