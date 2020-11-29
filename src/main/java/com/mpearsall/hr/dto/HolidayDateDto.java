package com.mpearsall.hr.dto;

import com.mpearsall.hr.entity.secondary.holiday.HolidayPeriod;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class HolidayDateDto extends EntityDto {
  private LocalDate date;
  private HolidayPeriod holidayPeriod;
}
