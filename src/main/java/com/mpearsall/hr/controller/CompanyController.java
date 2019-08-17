package com.mpearsall.hr.controller;

import com.mpearsall.hr.entity.Company;
import com.mpearsall.hr.repository.CompanyRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/company")
public class CompanyController {
  private final CompanyRepository companyRepository;

  public CompanyController(CompanyRepository companyRepository) {
    this.companyRepository = companyRepository;
  }

  @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public Company find() {
    return companyRepository.find();
  }

  @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public Company save(@RequestBody Company company) {
    return companyRepository.save(company);
  }
}
