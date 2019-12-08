package com.mpearsall.hr.controller;

import com.mpearsall.hr.dto.PasswordReset;
import com.mpearsall.hr.dto.UserDto;
import com.mpearsall.hr.entity.user.Role;
import com.mpearsall.hr.entity.user.User;
import com.mpearsall.hr.repository.UserRepository;
import com.mpearsall.hr.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
  private final UserService userService;
  private final UserRepository userRepository;

  public UserController(UserService userService, UserRepository userRepository) {
    this.userService = userService;
    this.userRepository = userRepository;
  }

  @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
  @Secured(Role.ADMIN)
  public Iterable<User> index() {
    return userRepository.findAll();
  }

  @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @Secured(Role.ADMIN)
  public User byId(@PathVariable Long id) {
    return userRepository.findById(id).orElseThrow();
  }

  @PutMapping(path = "/register", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  @Secured(Role.ADMIN)
  public User registerUser(@Valid @RequestBody UserDto userDto) {
    return userService.createUser(userDto);
  }

  @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @Secured(Role.ADMIN)
  public User save(@RequestBody User user) {
    return userRepository.save(user);
  }

  @PatchMapping(path = "/password-reset", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void resetPassword(@Valid @RequestBody PasswordReset passwordReset) {
    userService.resetPassword(passwordReset);
  }
}
