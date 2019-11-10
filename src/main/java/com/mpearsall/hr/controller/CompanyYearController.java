package com.mpearsall.hr.controller;

import com.mpearsall.hr.entity.holiday.CompanyYear;
import com.mpearsall.hr.entity.user.Role;
import com.mpearsall.hr.repository.CompanyYearRepository;
import com.mpearsall.hr.service.CompanyYearService;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/company-year")
public class CompanyYearController {
  private final CompanyYearRepository companyYearRepository;
  private final CompanyYearService companyYearService;

  public CompanyYearController(CompanyYearRepository companyYearRepository, CompanyYearService companyYearService) {
    this.companyYearRepository = companyYearRepository;
    this.companyYearService = companyYearService;
  }

  @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<CompanyYear> companyYears() {
    return companyYearRepository.findAll();
  }

  @Secured(Role.ADMIN)
  @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public CompanyYear save(@RequestBody CompanyYear companyYear) {
    return companyYearService.save(companyYear);
  }
}
