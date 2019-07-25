package com.mpearsall.hr.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class User implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotEmpty
  @Column(unique = true)
  private final String username;

  @NotEmpty
  @Email
  @Column(unique = true)
  private final String email;

  @NotEmpty
  private final String fullName;

  @ManyToMany
  private Collection<Role> roles;

  @JsonIgnore
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles.stream().map(Role::getPrivileges).flatMap(Collection::stream)
        .map(Privilege::getName)
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toUnmodifiableSet());
  }

  @JsonIgnore
  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @JsonIgnore
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isEnabled() {
    return true;
  }
}
