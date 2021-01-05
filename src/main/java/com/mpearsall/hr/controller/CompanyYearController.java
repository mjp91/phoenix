package com.mpearsall.hr.controller;

import com.mpearsall.hr.entity.primary.user.Role;
import com.mpearsall.hr.entity.secondary.holiday.CompanyYear;
import com.mpearsall.hr.repository.secondary.CompanyYearRepository;
import com.mpearsall.hr.service.CompanyYearService;
import com.mpearsall.hr.service.EmployeeService;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/company-year")
public class CompanyYearController {
  private final CompanyYearRepository companyYearRepository;
  private final CompanyYearService companyYearService;
  private final EmployeeService employeeService;

  public CompanyYearController(CompanyYearRepository companyYearRepository, CompanyYearService companyYearService,
                               EmployeeService employeeService) {
    this.companyYearRepository = companyYearRepository;
    this.companyYearService = companyYearService;
    this.employeeService = employeeService;
  }

  @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<CompanyYear> companyYears() {
    return companyYearRepository.findAll();
  }

  @GetMapping(path = "/future", produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<CompanyYear> currentAndFutureCompanyYears() {
    return companyYearRepository.findAllCurrentAndFuture();
  }

  @Secured({Role.ADMIN, Role.SUPER_ADMIN})
  @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public CompanyYear save(@RequestBody CompanyYear companyYear) {
    companyYear = companyYearService.save(companyYear);

    employeeService.addDefaultHolidayEntitlement(companyYear);

    return companyYear;
  }
}
