package com.mpearsall.hr.service.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("hr.storage")
public class StorageProperties {
  private String location;

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }
}
