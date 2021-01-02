package com.mpearsall.hr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Leaver implements Serializable {
  @NotNull
  private Long employeeId;

  @NotNull
  private LocalDate leaveDate;
}
