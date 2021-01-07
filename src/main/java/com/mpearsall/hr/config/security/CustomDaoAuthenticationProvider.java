package com.mpearsall.hr.config.security;

import com.mpearsall.hr.config.CustomUserDetailsService;
import com.mpearsall.hr.entity.primary.client.Client;
import com.mpearsall.hr.entity.primary.user.Role;
import com.mpearsall.hr.entity.primary.user.User;
import com.mpearsall.hr.exception.ClientExpiredException;
import com.mpearsall.hr.exception.TotpNotRegisteredException;
import com.mpearsall.hr.exception.TotpRequiredException;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;

public class CustomDaoAuthenticationProvider extends DaoAuthenticationProvider {

  @Override
  protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
    super.additionalAuthenticationChecks(userDetails, authentication);

    final User user = (User) userDetails;

    if (!CustomUserDetailsService.hasRole(userDetails, Role.SUPER_ADMIN) && !isClientValid(user.getClient())) {
      throw new ClientExpiredException();
    }

    final boolean totpEnabled = user.isTotpEnabled();

    if (!totpEnabled) {
      return;
    }

    final String totpSecret = user.getTotpSecret();

    if (totpSecret == null) {
      throw new TotpNotRegisteredException("TOTP secret not configured");
    }

    final String totpCode = ((TotpAuthenticationToken) authentication).getTotpCode();

    if (!isValidCode(totpCode)) {
      throw new TotpRequiredException("TOTP code missing");
    }

    final GoogleAuthenticator gAuth = new GoogleAuthenticator();
    if (!gAuth.authorize(totpSecret, Integer.parseInt(totpCode))) {
      throw new BadCredentialsException("Incorrect TOTP code");
    }
  }

  private boolean isClientValid(Client client) {
    return client == null || client.getExpiry().isAfter(LocalDateTime.now());
  }

  public static boolean isValidCode(String totpCode) {
    boolean result = true;

    if (totpCode == null || totpCode.isBlank()) {
      result = false;
    } else {
      try {
        Integer.parseInt(totpCode);
      } catch (NumberFormatException e) {
        result = false;
      }
    }

    return result;
  }
}
