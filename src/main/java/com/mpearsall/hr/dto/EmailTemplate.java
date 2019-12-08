package com.mpearsall.hr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class EmailTemplate {
  public static final String APPROVED_TEMPLATE = "approved";
  public static final String PASSWORD_RESET_TEMPLATE = "password_reset";
  public static final String PASSWORD_RESET_COMPLETE_TEMPLATE = "password_reset_complete";

  private final String templateName;
  private final Map<String, Object> arguments;

  public String getTemplateName() {
    return "templates/" + templateName + ".vm";
  }
}
