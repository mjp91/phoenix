package com.mpearsall.hr.config.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class TotpAuthenticationToken extends UsernamePasswordAuthenticationToken {
  private String totpCode;

  public TotpAuthenticationToken(Object principal, Object credentials, String totpCode) {
    super(principal, credentials);
    this.totpCode = totpCode;
  }

  public TotpAuthenticationToken(Object principal, Object credentials, String totpCode, Collection<? extends GrantedAuthority> authorities) {
    super(principal, credentials, authorities);
    this.totpCode = totpCode;
  }

  public String getTotpCode() {
    return totpCode;
  }
}
