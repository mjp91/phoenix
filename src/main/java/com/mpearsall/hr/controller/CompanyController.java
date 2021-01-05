package com.mpearsall.hr.controller;

import com.mpearsall.hr.entity.primary.user.Role;
import com.mpearsall.hr.entity.secondary.Company;
import com.mpearsall.hr.repository.secondary.CompanyRepository;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/company")
@Secured({Role.ADMIN, Role.SUPER_ADMIN})
public class CompanyController {
  private final CompanyRepository companyRepository;

  public CompanyController(CompanyRepository companyRepository) {
    this.companyRepository = companyRepository;
  }

  @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public Company find() {
    return Optional.ofNullable(companyRepository.find()).orElse(new Company());
  }

  @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Company save(@RequestBody Company company) {
    return companyRepository.save(company);
  }
}
