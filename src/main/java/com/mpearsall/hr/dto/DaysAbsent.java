package com.mpearsall.hr.dto;

import com.mpearsall.hr.entity.employee.Employee;
import com.mpearsall.hr.entity.holiday.CompanyYear;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DaysAbsent {
  private Employee employee;
  private CompanyYear companyYear;
  private int daysAbsent;
}
