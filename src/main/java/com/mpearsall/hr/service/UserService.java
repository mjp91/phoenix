package com.mpearsall.hr.service;

import com.mpearsall.hr.dto.Email;
import com.mpearsall.hr.dto.EmailTemplate;
import com.mpearsall.hr.dto.PasswordReset;
import com.mpearsall.hr.dto.UserDto;
import com.mpearsall.hr.entity.user.Role;
import com.mpearsall.hr.entity.user.User;
import com.mpearsall.hr.repository.RoleRepository;
import com.mpearsall.hr.repository.UserRepository;
import com.mpearsall.hr.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.mpearsall.hr.dto.EmailTemplate.PASSWORD_RESET_COMPLETE_TEMPLATE;
import static com.mpearsall.hr.dto.EmailTemplate.PASSWORD_RESET_TEMPLATE;
import static java.util.Collections.singletonList;

@Service
@Slf4j
public class UserService {
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final ServerService serverService;
  private final EmailService emailService;
  private final EmployeeService employeeService;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, RoleRepository roleRepository,
                     ServerService serverService, EmailService emailService,
                     EmployeeService employeeService, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.serverService = serverService;
    this.emailService = emailService;
    this.employeeService = employeeService;
    this.passwordEncoder = passwordEncoder;
  }

  public User createLocalUserFromLdap(DirContextOperations ctx, String username) {
    // create user
    User user = new User(username, ctx.getStringAttribute("mail"), ctx.getStringAttribute("cn"));
    user.setRoles(getDefaultRoles());
    user = userRepository.save(user);

    // create employee
    employeeService.createEmployee(user);

    return user;
  }

  @Transactional
  public User createUser(UserDto userDto) {
    // create user
    User user = new User(userDto.getUsername(), userDto.getEmail(), userDto.getFullName());
    user.setRoles(getDefaultRoles());
    user.setCredentialsExpired(true);
    user = userRepository.save(user);

    // create employee
    employeeService.createEmployee(user);

    // require password
    resetPasswordRequest(user);

    return user;
  }

  private List<Role> getDefaultRoles() {
    final List<Role> defaultRoles = new ArrayList<>();
    defaultRoles.add(roleRepository.findRoleByName(Role.USER).orElseThrow());

    return defaultRoles;
  }

  public void resetPasswordRequest(User user) {
    if (user.isLdap()) {
      log.warn("Reset password request ignored for LDAP user '{}'", user.getUsername());
      return;
    }

    final String resetToken = TokenUtil.randomToken(8);

    user.setPasswordResetToken(resetToken);

    // send email
    final String passwordResetUrl = serverService.getBaseUrl() + "/password-reset/" + resetToken;
    final Map<String, Object> args = Map.of(
        "fullName", user.getFullName(),
        "passwordResetUrl", passwordResetUrl
    );

    final EmailTemplate emailTemplate = new EmailTemplate(PASSWORD_RESET_TEMPLATE, args);
    final Email email = new Email(singletonList(user.getEmail()), "Password Reset");

    emailService.sendHtml(email, emailTemplate);
  }

  @Transactional
  public void resetPassword(PasswordReset passwordReset) {
    final User user = userRepository.findByPasswordResetToken(passwordReset.getToken()).orElseThrow();
    user.setPassword(passwordEncoder.encode(passwordReset.getPassword()));
    user.setPasswordResetToken(null);
    user.setCredentialsExpired(false);

    final EmailTemplate emailTemplate = new EmailTemplate(PASSWORD_RESET_COMPLETE_TEMPLATE, Map.of(
        "fullName", user.getFullName()
    ));
    final Email email = new Email(singletonList(user.getEmail()), "Password Reset");

    emailService.sendHtml(email, emailTemplate);
  }
}
