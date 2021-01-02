package com.mpearsall.hr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordReset {
  @NotEmpty
  private String token;

  @NotEmpty
  private String password;

  private String totpCode;
}
