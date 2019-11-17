package com.mpearsall.hr.service;

import com.mpearsall.hr.HrApplicationTests;
import com.mpearsall.hr.entity.absence.Absence;
import com.mpearsall.hr.entity.employee.Employee;
import com.mpearsall.hr.entity.holiday.CompanyYear;
import com.mpearsall.hr.exception.InvalidDetailsException;
import com.mpearsall.hr.factory.CompanyYearFactory;
import com.mpearsall.hr.repository.AbsenceRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;

public class AbsenceServiceTest extends HrApplicationTests {
  @Autowired
  private AbsenceService absenceService;

  @Autowired
  private CompanyYearFactory companyYearFactory;

  @Autowired
  private EmployeeService employeeService;

  @Autowired
  private AbsenceRepository absenceRepository;

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
    final CompanyYear companyYear = companyYearFactory.generateForCurrentYear();

    final int bradfordScore = absenceService.calculateBradfordScore(currentUserEmployee, companyYear);
    Assert.assertEquals(1, bradfordScore);
  }
}
