package com.mpearsall.hr.dto;

import com.mpearsall.hr.entity.secondary.holiday.HolidayPeriod;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class HolidayRequest implements Serializable {
  private String name;

  @NotNull
  private Long companyYearId;

  @NotNull
  private LocalDate startDate;

  @NotNull
  private LocalDate endDate;

  @NotNull
  private HolidayPeriod holidayPeriod = HolidayPeriod.ALL_DAY;
}
