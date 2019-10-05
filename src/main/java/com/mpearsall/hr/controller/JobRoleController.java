package com.mpearsall.hr.controller;

import com.mpearsall.hr.entity.employee.JobRole;
import com.mpearsall.hr.repository.JobRoleRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/job-role")
public class JobRoleController {
  private final JobRoleRepository jobRoleRepository;

  public JobRoleController(JobRoleRepository jobRoleRepository) {
    this.jobRoleRepository = jobRoleRepository;
  }

  @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public Iterable<JobRole> index() {
    return jobRoleRepository.findAll();
  }

  @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public JobRole save(@RequestBody JobRole jobRole) {
    return jobRoleRepository.save(jobRole);
  }
}
