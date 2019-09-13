package com.mpearsall.hr.controller;

import com.mpearsall.hr.entity.User;
import com.mpearsall.hr.repository.UserRepository;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Secured("ROLE_ADMIN")
public class UserController {
  private final UserRepository userRepository;

  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<User> index() {
    return userRepository.findAll();
  }

  @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public User byId(@PathVariable Long id) {
    return userRepository.findById(id).orElseThrow();
  }

  @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public User save(@RequestBody User user) {
    return userRepository.save(user);
  }
}
