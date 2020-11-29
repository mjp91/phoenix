package com.mpearsall.hr.dto;

import com.mpearsall.hr.HrApplicationTests;
import com.mpearsall.hr.entity.secondary.absence.Absence;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class DtoMapperTest extends HrApplicationTests {
  @Autowired
  private DtoMapper dtoMapper;

  @Test
  void toAbsenceDto() {
    final Absence absence = new Absence();
    absence.setReason("Test");

    final AbsenceDto absenceDto = dtoMapper.toAbsenceDto(absence);
    Assertions.assertNotNull(absenceDto);
  }
}
