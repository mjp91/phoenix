package com.mpearsall.hr.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Schedule {
  private final LicenseService licenseService;

  public Schedule(LicenseService licenseService) {
    this.licenseService = licenseService;
  }

  @Scheduled(fixedDelayString = "PT1H")
  void updateLicense() {
    log.info("Updating license");
    licenseService.updateLicense();
  }
}
