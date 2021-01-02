package com.mpearsall.hr.entity.secondary.employee;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@Embeddable
public class EmployeeDay implements Serializable {
  @NotNull
  private LocalTime start;
  @NotNull
  private LocalTime end;

  public EmployeeDay() {
    this.start = LocalTime.of(9, 0);
    this.end = LocalTime.of(17, 0);
  }
}
