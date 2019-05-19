package com.mpearsall.hr.controller;

import com.mpearsall.hr.entity.User;
import com.mpearsall.hr.repository.UserRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
  private final UserRepository userRepository;

  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<User> index() {
    return userRepository.findAll();
  }
}
