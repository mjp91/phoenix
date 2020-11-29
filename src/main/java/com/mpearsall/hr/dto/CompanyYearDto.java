package com.mpearsall.hr.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class CompanyYearDto extends EntityDto {
  private String name;
  private LocalDate yearStart;
  private LocalDate yearEnd;
}
