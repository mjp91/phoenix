package com.mpearsall.hr.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class AbsenceDto extends EntityDto {
  private EmployeeDto employee;
  private CompanyYearDto companyYear;
  private String reason;
  private LocalDate start;
  private LocalDate end;
  private Boolean authorized;
  private String unauthorizeReason;
  private boolean cancelled = false;
  private List<AttachmentDto> attachments;
}
