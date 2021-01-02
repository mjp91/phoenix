package com.mpearsall.hr.config;

import com.mpearsall.hr.repository.primary.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
  private final UserRepository userRepository;

  public CustomUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }

  public static UserDetails getCurrentUserDetails() {
    return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }

  public UserDetails getCurrentUserDetails(boolean reload) {
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if (reload) {
      userDetails = loadUserByUsername(userDetails.getUsername());
    }

    return userDetails;
  }

  public boolean currentUserHasRole(String role) {
    return hasRole(getCurrentUserDetails(false), role);
  }

  public static boolean hasRole(UserDetails userDetails, String role) {
    return userDetails.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .anyMatch(s -> s.equals(role));
  }
}
