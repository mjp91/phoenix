package com.mpearsall.hr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DateRange {
  private LocalDate startDate;

  private LocalDate endDate;
}
