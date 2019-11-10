package com.mpearsall.hr.service;

import com.mpearsall.hr.entity.absence.Absence;
import com.mpearsall.hr.entity.employee.Employee;
import com.mpearsall.hr.exception.InvalidDetailsException;
import com.mpearsall.hr.repository.AbsenceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;

@Service
public class AbsenceService {
  private final AbsenceRepository absenceRepository;
  private final EmployeeService employeeService;

  public AbsenceService(AbsenceRepository absenceRepository, EmployeeService employeeService) {
    this.absenceRepository = absenceRepository;
    this.employeeService = employeeService;
  }

  @Transactional
  public Absence create(Absence absence) {
    absence.setEmployee(employeeService.getCurrentUserEmployee());

    validateAbsence(absence);

    return absenceRepository.save(absence);
  }

  private void validateAbsence(Absence absence) {
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
}
