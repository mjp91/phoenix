package com.mpearsall.hr.controller;

import com.mpearsall.hr.dto.License;
import com.mpearsall.hr.entity.primary.user.Role;
import com.mpearsall.hr.service.LicenseService;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/license")
public class LicenseController {
  private final LicenseService licenseService;

  public LicenseController(LicenseService licenseService) {
    this.licenseService = licenseService;
  }

  @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
  @Secured(Role.ADMIN)
  public License get() {
    return licenseService.getLicense();
  }
}
