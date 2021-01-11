package com.mpearsall.hr.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class DepartmentDto extends EntityDto {
  private String title;
  private DepartmentDto parent;
}
