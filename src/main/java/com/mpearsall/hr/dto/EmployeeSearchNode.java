package com.mpearsall.hr.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public abstract class EmployeeSearchNode implements Serializable {
  private String id;
  private String name;

  public EmployeeSearchNode(String id, String name) {
    this.id = id;
    this.name = name;
  }
}
