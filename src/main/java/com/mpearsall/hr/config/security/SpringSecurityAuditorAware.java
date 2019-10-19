package com.mpearsall.hr.config.security;

import com.mpearsall.hr.entity.user.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SpringSecurityAuditorAware implements AuditorAware<User> {
  public Optional<User> getCurrentAuditor() {
    Optional<User> result;

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      result = Optional.empty();
    } else {
      final Object principal = authentication.getPrincipal();
      if (principal instanceof User) {
        result = Optional.of((User) principal);
      } else {
        result = Optional.empty();
      }
    }


    return result;
  }
}
