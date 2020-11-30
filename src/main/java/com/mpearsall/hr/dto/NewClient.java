package com.mpearsall.hr.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class NewClient {
  private String name;
  private LocalDateTime expiry;
  private CreateUser user;
}
