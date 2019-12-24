package com.mpearsall.hr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ApplicationError implements Serializable {
  private HttpStatus status;
  private String message;
  private ApplicationErrorType type;
}
