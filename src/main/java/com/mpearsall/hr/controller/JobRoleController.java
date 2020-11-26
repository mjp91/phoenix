package com.mpearsall.hr.controller;

import com.mpearsall.hr.entity.secondary.employee.JobRole;
import com.mpearsall.hr.exception.ResourceNotFoundException;
import com.mpearsall.hr.repository.secondary.JobRoleRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/job-role")
public class JobRoleController {
  private final JobRoleRepository jobRoleRepository;

  public JobRoleController(JobRoleRepository jobRoleRepository) {
    this.jobRoleRepository = jobRoleRepository;
  }

  @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<JobRole> index() {
    return jobRoleRepository.findAll();
  }

  @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public JobRole byId(@PathVariable long id) {
    return jobRoleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id, JobRole.class));
  }

  @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public JobRole save(@RequestBody JobRole jobRole) {
    return jobRoleRepository.save(jobRole);
  }
}
