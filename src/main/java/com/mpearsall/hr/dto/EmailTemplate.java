package com.mpearsall.hr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class EmailTemplate {
  private final String templateName;
  private final Map<String, Object> arguments;

  public String getTemplateName() {
    return "templates/" + templateName + ".vm";
  }
}
