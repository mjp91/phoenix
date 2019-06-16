package com.mpearsall.hr.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class HolidayRequest implements Serializable {
  private String name;

  private LocalDate startDate;

  private LocalDate endDate;
}
