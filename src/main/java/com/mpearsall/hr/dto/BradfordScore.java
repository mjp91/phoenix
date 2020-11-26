package com.mpearsall.hr.dto;

import com.mpearsall.hr.entity.secondary.employee.Employee;
import com.mpearsall.hr.entity.secondary.holiday.CompanyYear;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BradfordScore {
  private Employee employee;
  private CompanyYear companyYear;
  private int score;
}
