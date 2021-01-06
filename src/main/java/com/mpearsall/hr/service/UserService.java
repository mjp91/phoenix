package com.mpearsall.hr.service;

import com.mpearsall.hr.config.security.CustomDaoAuthenticationProvider;
import com.mpearsall.hr.dto.*;
import com.mpearsall.hr.entity.primary.client.Client;
import com.mpearsall.hr.entity.primary.user.Role;
import com.mpearsall.hr.entity.primary.user.User;
import com.mpearsall.hr.entity.secondary.Company;
import com.mpearsall.hr.exception.InvalidDetailsException;
import com.mpearsall.hr.repository.primary.RoleRepository;
import com.mpearsall.hr.repository.primary.UserRepository;
import com.mpearsall.hr.repository.secondary.CompanyRepository;
import com.mpearsall.hr.util.TokenUtil;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.mpearsall.hr.dto.EmailTemplate.PASSWORD_RESET_COMPLETE_TEMPLATE;
import static com.mpearsall.hr.dto.EmailTemplate.PASSWORD_RESET_TEMPLATE;
import static java.util.Collections.singletonList;

@Service
@Slf4j
public class UserService {
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final CompanyRepository companyRepository;
  private final ServerService serverService;
  private final EmailService emailService;
  private final EmployeeService employeeService;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, RoleRepository roleRepository,
                     CompanyRepository companyRepository, ServerService serverService,
                     EmailService emailService, EmployeeService employeeService,
                     PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.companyRepository = companyRepository;
    this.serverService = serverService;
    this.emailService = emailService;
    this.employeeService = employeeService;
    this.passwordEncoder = passwordEncoder;
  }

  public User createLocalUserFromLdap(DirContextOperations ctx, String username) {
    // create user
    User user = new User(username, ctx.getStringAttribute("mail"), ctx.getStringAttribute("cn"));
    user.setRoles(getRoles(getDefaultRoles()));
    user.setLdap(true);
    user = userRepository.save(user);

    // create employee
    employeeService.createEmployee(user);

    return user;
  }

  @Transactional
  public User createUser(CreateUser createUser, @Nullable Client client) {
    return createUser(createUser, client, getDefaultRoles());
  }

  @Transactional
  public User createUser(CreateUser createUser, @Nullable Client client, List<String> roleNames) {
    final Company company = companyRepository.find();

    // create user
    User user = new User(createUser.getUsername(), createUser.getEmail(), createUser.getFullName());

    // if password present set it
    final String password = createUser.getPassword();
    if (password != null) {
      user.setPassword(passwordEncoder.encode(password));
    }

    user.setClient(client);
    user.setRoles(getRoles(roleNames));
    user.setCredentialsExpired(password == null);
    user.setTotpEnabled(company == null || company.isTotpRequired());
    user = userRepository.save(user);

    // create employee
    employeeService.createEmployee(user);

    if (password == null) {
      // require password
      resetPasswordRequest(user);
    }

    return user;
  }

  private List<Role> getRoles(List<String> roleNames) {
    final List<Role> roles = new ArrayList<>();

    for (String roleName : roleNames) {
      roles.add(roleRepository.findRoleByName(roleName).orElseThrow());
    }

    return roles;
  }

  private List<String> getDefaultRoles() {
    return Collections.singletonList(Role.USER);
  }

  @Transactional
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
  public PasswordResetResult resetPassword(PasswordReset passwordReset) {
    boolean success = false;
    boolean totpRequired = false;
    String totpUrl = null;
    String message = null;

    final User user = userRepository.findByPasswordResetToken(passwordReset.getToken()).orElseThrow();

    if (user.isTotpEnabled()) {
      totpRequired = true;

      if (user.getTotpSecret() == null) {
        // need to register
        message = "Two factor authentication is required";
        totpUrl = register2fa(user).getTotpUrl();
      } else {
        // validate code
        if (CustomDaoAuthenticationProvider.isValidCode(passwordReset.getTotpCode())) {
          final GoogleAuthenticator gAuth = new GoogleAuthenticator();
          success = gAuth.authorize(user.getTotpSecret(), Integer.parseInt(passwordReset.getTotpCode()));

          if (!success) {
            message = "Two factor authentication code is incorrect";
          }
        } else {
          message = "Two factor authentication code is missing or invalid";
        }
      }
    } else {
      success = true;
    }

    if (success) {
      user.setPassword(passwordEncoder.encode(passwordReset.getPassword()));
      user.setPasswordResetToken(null);
      user.setCredentialsExpired(false);

      final EmailTemplate emailTemplate = new EmailTemplate(PASSWORD_RESET_COMPLETE_TEMPLATE, Map.of(
          "fullName", user.getFullName()
      ));
      final Email email = new Email(singletonList(user.getEmail()), "Password Reset");

      emailService.sendHtml(email, emailTemplate);
    }

    return new PasswordResetResult(success, totpRequired, totpUrl, message);
  }

  @Transactional
  public TotpRegister register2fa(Login login) {
    final User user = userRepository.findByUsername(login.getUsername()).orElseThrow();

    if (!checkCredentials(user, login.getPassword())) {
      throw new BadCredentialsException("Credentials incorrect");
    }

    if (user.getTotpSecret() != null) {
      throw new InvalidDetailsException("TOTP already registered");
    }

    return register2fa(user);
  }

  private TotpRegister register2fa(User user) {
    final GoogleAuthenticator gAuth = new GoogleAuthenticator();

    final GoogleAuthenticatorKey credentials = gAuth.createCredentials();
    user.setTotpSecret(credentials.getKey());

    final String totpUrl = GoogleAuthenticatorQRGenerator.getOtpAuthURL("Phoenix", user.getUsername(), credentials);
    return new TotpRegister(totpUrl, credentials.getVerificationCode(), credentials.getScratchCodes());
  }

  @Transactional
  public void changePassword(User user, ChangePassword changePassword) {
    if (user.isLdap()) {
      throw new InvalidDetailsException("User registered through LDAP, unable to change password");
    }

    if (!passwordEncoder.matches(changePassword.getCurrentPassword(), user.getPassword())) {
      throw new InvalidDetailsException("Current password is incorrect");
    }

    user.setPassword(passwordEncoder.encode(changePassword.getNewPassword()));
    userRepository.save(user);
  }

  @Transactional
  public void reset2fa(User user) {
    if (user.isLdap()) {
      throw new InvalidDetailsException(String.format("Cannot reset 2fa for LDAP user %s", user.getUsername()));
    }

    // clear secret to force re-register
    user.setTotpSecret(null);
  }

  private boolean checkCredentials(User user, String password) {
    return passwordEncoder.matches(password, user.getPassword());
  }

  @Transactional
  public User updateUser(UpdateUser updateUser) {
    final User user = userRepository.findById(updateUser.getId()).orElseThrow();

    final Collection<Role> roles = updateUser.getRoles();

    if (roles != null) {
      user.setRoles(roles);
    }

    final Boolean totpEnabled = updateUser.getTotpEnabled();
    if (totpEnabled != null) {
      final Company company = companyRepository.find();

      if (!totpEnabled && company.isTotpRequired()) {
        throw new InvalidDetailsException("Company configuration enforces 2FA");
      }

      user.setTotpEnabled(totpEnabled);
    }

    return userRepository.save(user);
  }
}
