package com.mpearsall.hr.dto;

import com.mpearsall.hr.entity.holiday.Holiday;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Collection;

@Data
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class TodaysHolidays implements Serializable {
  private final Holiday currentUserHoliday;

  private final Collection<Holiday> managedEmployeeHolidays;
}
