package com.mpearsall.hr.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class NewClient {
  @NotEmpty
  private String name;
  @NotNull
  private LocalDateTime expiry;
  @NotNull
  private CreateUser user;
}
