package com.mpearsall.hr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ApplicationError implements Serializable {
  private String message;
}
