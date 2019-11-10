package com.mpearsall.hr.service;

import com.mpearsall.hr.HrApplicationTests;
import com.mpearsall.hr.entity.absence.Absence;
import com.mpearsall.hr.exception.InvalidDetailsException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;

public class AbsenceServiceTest extends HrApplicationTests {
  @Autowired
  private AbsenceService absenceService;

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
}
