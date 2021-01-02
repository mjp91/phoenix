package com.mpearsall.hr.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collection;

@Data
@EqualsAndHashCode(callSuper = true)
public class EmployeeSearchDepartmentNode extends EmployeeSearchNode {
  private Collection<EmployeeSearchNode> children;

  public EmployeeSearchDepartmentNode() {
  }

  public EmployeeSearchDepartmentNode(Long id, String department, Collection<EmployeeSearchNode> children) {
    super("D" + id, department);
    this.children = children;
  }
}
