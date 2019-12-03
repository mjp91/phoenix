package com.mpearsall.hr.controller;

import com.mpearsall.hr.dto.AbsenceUpdate;
import com.mpearsall.hr.dto.BradfordScore;
import com.mpearsall.hr.entity.absence.Absence;
import com.mpearsall.hr.entity.employee.Employee;
import com.mpearsall.hr.entity.holiday.CompanyYear;
import com.mpearsall.hr.entity.user.Role;
import com.mpearsall.hr.repository.AbsenceRepository;
import com.mpearsall.hr.repository.EmployeeRepository;
import com.mpearsall.hr.service.AbsenceService;
import com.mpearsall.hr.service.CompanyYearService;
import com.mpearsall.hr.service.EmployeeService;
import com.mpearsall.hr.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/api/absence")
public class AbsenceController {
  private final AbsenceRepository absenceRepository;
  private final EmployeeRepository employeeRepository;
  private final EmployeeService employeeService;
  private final CompanyYearService companyYearService;
  private final AbsenceService absenceService;
  private final UserService userService;

  public AbsenceController(AbsenceRepository absenceRepository, EmployeeRepository employeeRepository,
                           EmployeeService employeeService, CompanyYearService companyYearService,
                           AbsenceService absenceService, UserService userService) {
    this.absenceRepository = absenceRepository;
    this.employeeRepository = employeeRepository;
    this.companyYearService = companyYearService;
    this.employeeService = employeeService;
    this.absenceService = absenceService;
    this.userService = userService;
  }

  @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public Page<Absence> getAbsences() {
    final Employee employee = employeeService.getCurrentUserEmployee();

    return absenceRepository.findAllByEmployee(employee, Pageable.unpaged());
  }

  @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
  @Secured(Role.ADMIN)
  public Page<Absence> getAllAbsences() {
    return absenceRepository.findAll(Pageable.unpaged());
  }

  @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Absence getAbsence(@PathVariable Long id) {
    return absenceRepository.findById(id).orElseThrow();
  }

  @GetMapping(path = "/authorisation", produces = MediaType.APPLICATION_JSON_VALUE)
  public Collection<Absence> getPendingAuthorisation() {
    Collection<Absence> result;

    // get user's employee record
    final Employee employee = employeeService.getCurrentUserEmployee();

    if (userService.currentUserHasRole(Role.ADMIN)) {
      result = absenceRepository.findAllPendingAuthorisation();
    } else {
      result = absenceRepository.findAllPendingAuthorisationForManager(employee);
    }

    return result;
  }

  @PutMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public Absence create(@RequestBody Absence absence) {
    return absenceService.create(absence);
  }

  @PatchMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Absence update(@PathVariable Long id, @RequestBody AbsenceUpdate absenceUpdate) {
    return absenceService.update(id, absenceUpdate);
  }

  @PatchMapping(path = "/authorise/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Absence authorise(@PathVariable Long id) {
    return absenceService.authorise(id);
  }

  @PatchMapping(path = "/unauthorise/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Absence unauthorise(@PathVariable Long id, @RequestBody Map<String, Object> body) {
    return absenceService.unauthorise(id, (String) body.get("reason"));
  }

  @PatchMapping(path = "/cancel/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Absence cancel(@PathVariable Long id) {
    return absenceService.cancel(id);
  }

  @GetMapping(path = "/bradford-score/{employeeId}")
  @Secured(Role.ADMIN)
  public BradfordScore bradfordScore(@PathVariable Long employeeId) {
    final Employee employee = employeeRepository.findById(employeeId).orElseThrow();
    final CompanyYear currentCompanyYear = companyYearService.getCurrentCompanyYear();

    final int bradfordScore = absenceService.calculateBradfordScore(employee, currentCompanyYear);

    return new BradfordScore(employee, currentCompanyYear, bradfordScore);
  }
}
