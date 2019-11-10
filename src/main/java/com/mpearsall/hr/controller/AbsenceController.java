package com.mpearsall.hr.controller;

import com.mpearsall.hr.entity.absence.Absence;
import com.mpearsall.hr.entity.employee.Employee;
import com.mpearsall.hr.repository.AbsenceRepository;
import com.mpearsall.hr.service.AbsenceService;
import com.mpearsall.hr.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/absence")
public class AbsenceController {
  private final AbsenceRepository absenceRepository;
  private final EmployeeService employeeService;
  private final AbsenceService absenceService;

  public AbsenceController(AbsenceRepository absenceRepository, EmployeeService employeeService,
                           AbsenceService absenceService) {
    this.absenceRepository = absenceRepository;
    this.employeeService = employeeService;
    this.absenceService = absenceService;
  }

  @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public Page<Absence> getAbsences() {
    final Employee employee = employeeService.getCurrentUserEmployee();

    return absenceRepository.findAllByEmployee(employee, Pageable.unpaged());
  }

  @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public Absence save(@RequestBody Absence absence) {
    return absenceService.create(absence);
  }
}