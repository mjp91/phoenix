package com.mpearsall.hr.config.security;

import com.mpearsall.hr.entity.primary.user.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SpringSecurityAuditorAware implements AuditorAware<Long> {
  public Optional<Long> getCurrentAuditor() {
    Optional<Long> result;

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      result = Optional.empty();
    } else {
      final Object principal = authentication.getPrincipal();
      if (principal instanceof User) {
        result = Optional.of(((User) principal).getId());
      } else {
        result = Optional.empty();
      }
    }

    return result;
  }
}
