package com.mpearsall.hr.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class HolidayDto extends EntityDto {
  private String name;
  private EmployeeDto employee;
  private CompanyYearDto companyYear;
  private Boolean approved;
  private String disapprovalReason;
  private boolean cancelled = false;
  private Collection<HolidayDateDto> holidayDates;
}
