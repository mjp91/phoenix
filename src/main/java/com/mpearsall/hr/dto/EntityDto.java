package com.mpearsall.hr.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public abstract class EntityDto {
  private Long id;
  private LocalDateTime createdDate;
}
