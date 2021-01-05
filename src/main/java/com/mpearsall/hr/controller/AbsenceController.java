package com.mpearsall.hr.controller;

import com.mpearsall.hr.config.CustomUserDetailsService;
import com.mpearsall.hr.dto.*;
import com.mpearsall.hr.entity.primary.user.Role;
import com.mpearsall.hr.entity.secondary.absence.Absence;
import com.mpearsall.hr.entity.secondary.employee.Employee;
import com.mpearsall.hr.entity.secondary.holiday.CompanyYear;
import com.mpearsall.hr.repository.secondary.AbsenceRepository;
import com.mpearsall.hr.repository.secondary.EmployeeRepository;
import com.mpearsall.hr.service.AbsenceService;
import com.mpearsall.hr.service.CompanyYearService;
import com.mpearsall.hr.service.EmployeeService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/absence")
public class AbsenceController {
  private final AbsenceRepository absenceRepository;
  private final EmployeeRepository employeeRepository;
  private final EmployeeService employeeService;
  private final CompanyYearService companyYearService;
  private final AbsenceService absenceService;
  private final CustomUserDetailsService customUserDetailsService;
  private final DtoMapper dtoMapper;

  public AbsenceController(AbsenceRepository absenceRepository, EmployeeRepository employeeRepository,
                           EmployeeService employeeService, CompanyYearService companyYearService,
                           AbsenceService absenceService, CustomUserDetailsService customUserDetailsService, DtoMapper dtoMapper) {
    this.absenceRepository = absenceRepository;
    this.employeeRepository = employeeRepository;
    this.companyYearService = companyYearService;
    this.employeeService = employeeService;
    this.absenceService = absenceService;
    this.customUserDetailsService = customUserDetailsService;
    this.dtoMapper = dtoMapper;
  }

  @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<AbsenceDto> getAbsences() {
    final Employee employee = employeeService.getCurrentUserEmployee();

    return toDto(absenceRepository.findAllByEmployee(employee, Pageable.unpaged()));
  }

  @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
  @Secured({Role.ADMIN, Role.SUPER_ADMIN})
  public Iterable<AbsenceDto> getAllAbsences() {
    return toDto(absenceRepository.findAll(Pageable.unpaged()));
  }

  @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public AbsenceDto getAbsence(@PathVariable Long id) {
    return dtoMapper.toAbsenceDto(absenceRepository.findById(id).orElseThrow());
  }

  @GetMapping(path = "/days", produces = MediaType.APPLICATION_JSON_VALUE)
  public DaysAbsent getDaysAbsent() {
    final Employee currentUserEmployee = employeeService.getCurrentUserEmployee();
    final CompanyYear currentCompanyYear = companyYearService.getCurrentCompanyYear();

    final int totalDays = absenceService.calculateDaysAbsent(currentUserEmployee, currentCompanyYear);

    return new DaysAbsent(currentUserEmployee, currentCompanyYear, totalDays);
  }

  @GetMapping(path = "/authorisation", produces = MediaType.APPLICATION_JSON_VALUE)
  public Collection<AbsenceDto> getPendingAuthorisation() {
    Collection<Absence> result;

    // get user's employee record
    final Employee employee = employeeService.getCurrentUserEmployee();

    if (customUserDetailsService.currentUserHasRole(Role.ADMIN)) {
      result = absenceRepository.findAllPendingAuthorisation();
    } else {
      result = absenceRepository.findAllPendingAuthorisationForManager(employee);
    }

    return toDto(result);
  }

  @PutMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public AbsenceDto create(@RequestBody AbsenceDto absence) {
    return dtoMapper.toAbsenceDto(absenceService.create(dtoMapper.toAbsence(absence)));
  }

  @PatchMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public AbsenceDto update(@PathVariable Long id, @RequestBody AbsenceUpdate absenceUpdate) {
    return dtoMapper.toAbsenceDto(absenceService.update(id, absenceUpdate));
  }

  @PatchMapping(path = "/authorise/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public AbsenceDto authorise(@PathVariable Long id) {
    return dtoMapper.toAbsenceDto(absenceService.authorise(id));
  }

  @PatchMapping(path = "/unauthorise/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public AbsenceDto unauthorise(@PathVariable Long id, @RequestBody Map<String, Object> body) {
    return dtoMapper.toAbsenceDto(absenceService.unauthorise(id, (String) body.get("reason")));
  }

  @PatchMapping(path = "/cancel/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public AbsenceDto cancel(@PathVariable Long id) {
    return dtoMapper.toAbsenceDto(absenceService.cancel(id));
  }

  @GetMapping(path = "/bradford-score/{employeeId}")
  @Secured({Role.ADMIN, Role.SUPER_ADMIN})
  public BradfordScore bradfordScore(@PathVariable Long employeeId) {
    final Employee employee = employeeRepository.findById(employeeId).orElseThrow();
    final CompanyYear currentCompanyYear = companyYearService.getCurrentCompanyYear();

    final int bradfordScore = absenceService.calculateBradfordScore(employee, currentCompanyYear);

    return new BradfordScore(employee, currentCompanyYear, bradfordScore);
  }

  private List<AbsenceDto> toDto(Iterable<Absence> absences) {
    return StreamSupport.stream(absences.spliterator(), false)
        .map(dtoMapper::toAbsenceDto)
        .collect(Collectors.toList());
  }
}
