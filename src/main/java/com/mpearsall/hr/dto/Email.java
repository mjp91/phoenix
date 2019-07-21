package com.mpearsall.hr.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Email {
  private List<String> toAddresses = new ArrayList<>();

  private final List<String> ccAddresses = new ArrayList<>();

  private String subject = "";

  private String body = "";

  public Email(List<String> toAddresses, String subject) {
    this.toAddresses = toAddresses;
    this.subject = subject;
  }
}
