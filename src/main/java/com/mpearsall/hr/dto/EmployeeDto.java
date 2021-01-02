package com.mpearsall.hr.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.mpearsall.hr.entity.secondary.employee.Address;
import com.mpearsall.hr.entity.secondary.employee.EmployeeWeek;
import com.mpearsall.hr.view.View;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class EmployeeDto extends EntityDto {
  private UserDto user;
  private DepartmentDto department;
  private String reference;
  private JobRoleDto jobRole;

  @JsonView(View.Admin.class)
  private Address address;

  @JsonView(View.Admin.class)
  private String telephoneNumber;

  @JsonView(View.Admin.class)
  private String mobileNumber;

  private String extensionNumber;
  private String profileFileName;
  private LocalDate dateOfBirth;
  private LocalDate serviceStartDate;
  private LocalDate serviceEndDate;
  private EmployeeDto manager;
  private EmployeeWeek employeeWeek;
}
