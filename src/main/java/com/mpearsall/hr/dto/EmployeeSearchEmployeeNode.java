package com.mpearsall.hr.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EmployeeSearchEmployeeNode extends EmployeeSearchNode {
  public EmployeeSearchEmployeeNode(Long id, String name) {
    super("E" + id, name);
  }
}
