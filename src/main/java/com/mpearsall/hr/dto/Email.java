package com.mpearsall.hr.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Email {
  private final List<String> toAddresses = new ArrayList<>();

  private final List<String> ccAddresses = new ArrayList<>();

  private String subject = "";

  private String body = "";
}
