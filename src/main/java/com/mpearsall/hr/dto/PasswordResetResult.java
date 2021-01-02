package com.mpearsall.hr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetResult implements Serializable {
  private boolean success;

  private boolean totpRequired;

  private String totpUrl;

  private String message;
}
