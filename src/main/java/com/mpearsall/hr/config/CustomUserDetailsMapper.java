package com.mpearsall.hr.config;

import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class CustomUserDetailsMapper extends LdapUserDetailsMapper {
  private final CustomUserDetailsService customUserDetailsService;

  public CustomUserDetailsMapper(CustomUserDetailsService customUserDetailsService) {
    this.customUserDetailsService = customUserDetailsService;
  }

  @Override
  public UserDetails mapUserFromContext(DirContextOperations ctx, String username, Collection<? extends GrantedAuthority> authorities) {
    UserDetails userDetails;
    try {
      userDetails = customUserDetailsService.loadUserByUsername(username);
    } catch (UsernameNotFoundException e) {
      // valid LDAP user but not found locally
      userDetails = customUserDetailsService.createLocalUser(ctx, username);
    }

    return userDetails;
  }
}
