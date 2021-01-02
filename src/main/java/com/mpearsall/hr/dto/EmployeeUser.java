package com.mpearsall.hr.dto;

import com.mpearsall.hr.entity.primary.user.User;
import com.mpearsall.hr.entity.secondary.employee.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeUser implements Serializable {
  private Employee employee;
  private User user;
}
