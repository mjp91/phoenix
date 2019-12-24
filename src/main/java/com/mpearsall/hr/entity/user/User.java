package com.mpearsall.hr.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotEmpty
  @Column(unique = true)
  private final String username;

  @JsonIgnore
  private String password;

  @NotEmpty
  @Email
  @Column(unique = true)
  private final String email;

  @NotEmpty
  private final String fullName;

  @ManyToMany(fetch = FetchType.EAGER)
  private Collection<Role> roles;

  @NotEmpty
  private String calendarToken = UUID.randomUUID().toString();

  @NotNull
  private boolean credentialsExpired;

  @Column(unique = true)
  private String passwordResetToken;

  @NotNull
  private boolean ldap = false;

  @NotNull
  private boolean totpEnabled = false;

  @JsonIgnore
  private String totpSecret;

  @JsonIgnore
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles.stream()
        .map(Role::getName)
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toUnmodifiableSet());
  }

  public boolean hasRole(Role role) {
    return this.getRoles().contains(role);
  }

  @JsonIgnore
  public String getTotpSecret() {
    return totpSecret;
  }

  @JsonIgnore
  @Override
  public String getPassword() {
    return password;
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
    return !credentialsExpired;
  }

  @JsonIgnore
  @Override
  public boolean isEnabled() {
    return true;
  }
}
