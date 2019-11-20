package com.mpearsall.hr.service;

import com.mpearsall.hr.dto.AbsenceUpdate;
import com.mpearsall.hr.entity.absence.Absence;
import com.mpearsall.hr.entity.employee.Employee;
import com.mpearsall.hr.entity.holiday.CompanyYear;
import com.mpearsall.hr.entity.user.Role;
import com.mpearsall.hr.exception.InvalidDetailsException;
import com.mpearsall.hr.exception.PermissionException;
import com.mpearsall.hr.repository.AbsenceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;

@Service
public class AbsenceService {
  private final AbsenceRepository absenceRepository;
  private final EmployeeService employeeService;
  private final CompanyYearService companyYearService;
  private final UserService userService;

  public AbsenceService(AbsenceRepository absenceRepository, EmployeeService employeeService,
                        CompanyYearService companyYearService, UserService userService) {
    this.absenceRepository = absenceRepository;
    this.employeeService = employeeService;
    this.companyYearService = companyYearService;
    this.userService = userService;
  }

  @Transactional
  public Absence create(Absence absence) {
    final Employee currentUserEmployee = employeeService.getCurrentUserEmployee();

    absence.setEmployee(currentUserEmployee);
    absence.setCompanyYear(companyYearService.getCurrentCompanyYear());

    validateNewAbsence(absence);

    // approved by default
    if (currentUserEmployee.getManager() == null) {
      absence.setAuthorized(true);
    }

    return absenceRepository.save(absence);
  }

  @Transactional
  public Absence update(Long id, AbsenceUpdate absenceUpdate) {
    final Absence absence = absenceRepository.findById(id).orElseThrow();
    absence.setReason(absenceUpdate.getReason());

    return absenceRepository.save(absence);
  }

  private void validateNewAbsence(Absence absence) {
    if (!absence.isNew()) {
      throw new InvalidDetailsException("Absence already exists");
    }

    final LocalDate start = absence.getStart();
    final LocalDate end = absence.getEnd();

    // check not backwards
    if (start.isAfter(end)) {
      throw new InvalidDetailsException("Start date must be before end date");
    }

    // check not in the future
    if (start.isAfter(LocalDate.now())) {
      throw new InvalidDetailsException("Start date is in the future");
    }

    // check at least 1 day is worked in the range
    final Employee employee = absence.getEmployee();
    final int daysBetween = EmployeeService.calculateDaysWorkedBetween(employee, start, end).size();
    if (daysBetween == 0) {
      throw new InvalidDetailsException("No dates are worked in the specified range");
    }

    // check no overlap with existing absences
    final Collection<Absence> existingAbsences = absenceRepository.findAllInRange(start, end, employee);
    if (!existingAbsences.isEmpty()) {
      throw new InvalidDetailsException("An existing absence overlaps with the specified range");
    }
  }

  public int calculateBradfordScore(Employee employee, CompanyYear companyYear) {
    // B = S^2 * D
    final Collection<Absence> absences = absenceRepository.findAllByEmployeeAndCompanyYear(employee, companyYear);

    int totalDays = 0;
    for (Absence absence : absences) {
      totalDays += EmployeeService.calculateDaysWorkedBetween(employee, absence.getStart(), absence.getEnd()).size();
    }

    return (absences.size() * absences.size()) * totalDays;
  }

  @Transactional
  public Absence authorise(Long id) {
    return modifyAbsenceAuthorisation(id, true);
  }

  @Transactional
  public Absence unauthorise(Long id, String reason) {
    final Absence absence = modifyAbsenceAuthorisation(id, false);
    absence.setUnauthorizeReason(reason);

    return absence;
  }

  private Absence modifyAbsenceAuthorisation(Long id, boolean authorized) {
    final Absence absence = absenceRepository.findById(id).orElseThrow();

    if (!employeeService.isManager(absence.getEmployee()) && !userService.currentUserHasRole(Role.ADMIN)) {
      throw new PermissionException("User is not the absence creator's manager");
    }

    absence.setAuthorized(authorized);

    return absence;
  }

  @Transactional
  public Absence cancel(Long id) {
    final Absence absence = absenceRepository.findById(id).orElseThrow();

    if (!employeeService.isManager(absence.getEmployee()) && !userService.currentUserHasRole(Role.ADMIN)) {
      throw new PermissionException("User is not the absence creator's manager");
    }

    absence.setCancelled(true);

    return absence;
  }
}
