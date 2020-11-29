package com.mpearsall.hr.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class HolidayEntitlementDto extends EntityDto {
  private EmployeeDto employee;
  private CompanyYearDto companyYear;
  private double holidayEntitlementHours;
}
