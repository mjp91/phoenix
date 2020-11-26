package com.mpearsall.hr.controller;

import com.mpearsall.hr.config.CustomUserDetailsService;
import com.mpearsall.hr.dto.*;
import com.mpearsall.hr.entity.primary.user.Role;
import com.mpearsall.hr.entity.primary.user.User;
import com.mpearsall.hr.repository.primary.UserRepository;
import com.mpearsall.hr.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
  private CustomUserDetailsService customUserDetailsService;
  private final UserService userService;
  private final UserRepository userRepository;

  public UserController(CustomUserDetailsService customUserDetailsService, UserService userService,
                        UserRepository userRepository) {
    this.customUserDetailsService = customUserDetailsService;
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
  public User registerUser(@Valid @RequestBody CreateUser createUser) {
    return userService.createUser(createUser);
  }

  @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @Secured(Role.ADMIN)
  public User save(@RequestBody UpdateUser updateUser) {
    return userService.updateUser(updateUser);
  }

  @PostMapping(path = "/forgotten-password/{username}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void requestPasswordReset(@PathVariable String username) {
    userRepository.findByUsername(username)
        .ifPresent(userService::resetPasswordRequest);
  }

  @PostMapping(path = "/change-password")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void changePassword(@Valid @RequestBody ChangePassword changePassword) {
    final User currentUser = (User) customUserDetailsService.getCurrentUserDetails(true);
    userService.changePassword(currentUser, changePassword);
  }

  @PatchMapping(path = "/password-reset", consumes = MediaType.APPLICATION_JSON_VALUE)
  public PasswordResetResult resetPassword(@Valid @RequestBody PasswordReset passwordReset) {
    return userService.resetPassword(passwordReset);
  }

  @PatchMapping(path = "/2fa-reset/{username}")
  @Secured(Role.ADMIN)
  public void reset2fa(@PathVariable String username) {
    userRepository.findByUsername(username)
        .ifPresent(userService::reset2fa);
  }

  @PatchMapping(path = "/2fa-register")
  public TotpRegister register2fa(@RequestBody Login login) {
    return userService.register2fa(login);
  }
}
