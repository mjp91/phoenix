package com.mpearsall.hr.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class Login implements Serializable {
  private String username;
  private String password;

  public void setUsername(String username) {
    this.username = username.trim();
  }
}
