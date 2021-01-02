package com.mpearsall.hr.controller;

import com.mpearsall.hr.entity.primary.user.Role;
import com.mpearsall.hr.repository.primary.RoleRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
  private final RoleRepository roleRepository;

  public RoleController(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Role> index() {
    return StreamSupport.stream(roleRepository.findAll().spliterator(), false)
        .filter(role -> !role.getName().equals(Role.SUPER_ADMIN))
        .collect(Collectors.toSet());
  }
}
