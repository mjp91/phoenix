package com.mpearsall.hr;

import com.mpearsall.hr.dto.CreateUser;
import com.mpearsall.hr.entity.primary.user.Role;
import com.mpearsall.hr.repository.primary.UserRepository;
import com.mpearsall.hr.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@Slf4j
public class PostBoot implements CommandLineRunner {

  private final UserRepository userRepository;
  private final UserService userService;

  @Value("${hr.admin.password:#{null}}")
  private String adminPassword;

  public PostBoot(UserRepository userRepository, UserService userService) {
    this.userRepository = userRepository;
    this.userService = userService;
  }

  @Override
  public void run(String... args) {
    // create admin account for default client
    if (userRepository.count() == 0 && adminPassword != null) {
      log.info("Creating admin user");

      final CreateUser createUser = new CreateUser("admin", adminPassword, "admin@example.com", "Admin");

      userService.createUser(createUser, null, Collections.singletonList(Role.SUPER_ADMIN));
    }
  }
}
