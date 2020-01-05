package com.mpearsall.hr.service;

import com.mpearsall.hr.HrApplicationTests;
import com.mpearsall.hr.entity.absence.Absence;
import com.mpearsall.hr.entity.employee.Employee;
import com.mpearsall.hr.exception.InvalidDetailsException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

public class AbsenceServiceTest extends HrApplicationTests {
  @Autowired
  private AbsenceService absenceService;

  @Autowired
  private EmployeeService employeeService;

  @Test
  @WithMockUser(username = "matt")
  public void create() {
    generateValidAbsence();
  }

  @Test(expected = InvalidDetailsException.class)
  @WithMockUser(username = "matt")
  public void createWithBackwardDates() {
    final Absence absence = new Absence();
    absence.setStart(LocalDate.now());
    absence.setEnd(LocalDate.now().minusDays(1L));

    absenceService.create(absence);
  }

  @Test(expected = InvalidDetailsException.class)
  @WithMockUser(username = "matt")
  public void createWithFutureDates() {
    final Absence absence = new Absence();
    absence.setStart(LocalDate.now().plusDays(1L));
    absence.setEnd(LocalDate.now().plusDays(2L));

    absenceService.create(absence);
  }

  @Test
  @WithMockUser(username = "matt")
  public void calculateBradfordScore() {
    final Employee currentUserEmployee = employeeService.getCurrentUserEmployee();

    generateValidAbsence();

    final int bradfordScore = absenceService.calculateBradfordScore(currentUserEmployee, companyYear);
    Assert.assertEquals(1, bradfordScore);
  }

  private void generateValidAbsence() {
    final Employee employee = employeeService.getCurrentUserEmployee();
    final Set<DayOfWeek> daysWorked = Employee.getDaysWorked(employee);
    final DayOfWeek dayOfWeek = daysWorked.stream().findAny().orElseThrow();

    final LocalDate absenceDate = LocalDate.now().with(dayOfWeek);

    final Absence absence = new Absence();
    absence.setReason("Sickness");
    absence.setStart(absenceDate);
    absence.setEnd(absenceDate);

    absenceService.create(absence);
  }
}
