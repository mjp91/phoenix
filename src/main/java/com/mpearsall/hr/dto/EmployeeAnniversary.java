package com.mpearsall.hr.dto;

import com.mpearsall.hr.entity.secondary.employee.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeAnniversary implements Serializable {
  private LocalDate date;
  private List<Employee> employees;
}
