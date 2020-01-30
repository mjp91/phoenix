package com.mpearsall.hr.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class License implements Serializable {
  // license details
  private String authenticationToken;
  private int seats;
  private LocalDateTime expiry;
}
