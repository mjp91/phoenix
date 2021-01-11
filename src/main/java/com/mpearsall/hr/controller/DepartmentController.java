package com.mpearsall.hr.controller;

import com.mpearsall.hr.dto.DepartmentDto;
import com.mpearsall.hr.dto.DtoMapper;
import com.mpearsall.hr.dto.EmployeeSearchNode;
import com.mpearsall.hr.entity.primary.user.Role;
import com.mpearsall.hr.entity.secondary.Department;
import com.mpearsall.hr.exception.ResourceNotFoundException;
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
  private final DtoMapper dtoMapper;

  public DepartmentController(DepartmentRepository departmentRepository, DepartmentService departmentService, DtoMapper dtoMapper) {
    this.departmentRepository = departmentRepository;
    this.departmentService = departmentService;
    this.dtoMapper = dtoMapper;
  }

  @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public DepartmentDto find(@PathVariable long id) {
    return dtoMapper.toDepartmentDto(departmentRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(id, Department.class)));
  }

  @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<DepartmentDto> index() {
    return dtoMapper.toDepartmentDtos(departmentRepository.findAll());
  }

  @GetMapping(path = "/employee", produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<EmployeeSearchNode> departmentEmployees() {
    return departmentService.getDepartmentEmployees();
  }

  @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @Secured({Role.ADMIN, Role.SUPER_ADMIN})
  public DepartmentDto save(@RequestBody DepartmentDto department) {
    return dtoMapper.toDepartmentDto(departmentRepository.save(dtoMapper.toDepartment(department)));
  }
}
