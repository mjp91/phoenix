package com.mpearsall.hr.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class ServerService {
  @Value("${hr.base-url:#{null}}")
  private String baseUrl;

  public String getBaseUrl() {
    String result = baseUrl;

    if (baseUrl == null) {
      final ServletUriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentContextPath();
      result = urlBuilder.build().toUriString();
    }

    return result;
  }
}
