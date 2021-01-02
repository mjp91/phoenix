package com.mpearsall.hr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUser implements Serializable {
  @NotEmpty
  private String username;

  private String password;

  @NotEmpty
  private String email;

  @NotEmpty
  private String fullName;
}
