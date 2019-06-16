package com.mpearsall.hr.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class EmployeeDay implements Serializable {
  @NotNull
  private LocalTime start;
  @NotNull
  private LocalTime end;
}
