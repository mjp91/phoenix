package com.mpearsall.hr.config;

import com.mpearsall.hr.entity.User;
import com.mpearsall.hr.repository.UserRepository;
import org.springframework.ldap.core.DirContextOperations;
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
    final User user = userRepository.findByUsername(username);

    if (user == null) {
      throw new UsernameNotFoundException("User not found");
    }

    return user;
  }

  public UserDetails createLocalUser(DirContextOperations ctx, String username) {
    final User user = new User(username, ctx.getStringAttribute("mail"), ctx.getStringAttribute("cn"));
    return userRepository.save(user);
  }
}
