package com.mpearsall.hr.entity.primary.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mpearsall.hr.entity.primary.client.Client;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "\"user\"", indexes = {@Index(columnList = "client_id")})
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

  @JsonBackReference
  @ManyToOne
  private Client client;

  @NotEmpty
  @Email
  @Column(unique = true)
  private final String email;

  @NotEmpty
  private final String fullName;

  @ManyToMany(fetch = FetchType.EAGER)
  private Collection<Role> roles = new ArrayList<>();

  @NotEmpty
  private String calendarToken = UUID.randomUUID().toString();

  @NotNull
  private boolean credentialsExpired;

  @Column(unique = true)
  private String passwordResetToken;

  @NotNull
  private boolean ldap = false;

  @NotNull
  private boolean totpEnabled = true;

  @JsonIgnore
  private String totpSecret;

  @NotNull
  private boolean enabled = true;

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

  @Override
  public boolean isEnabled() {
    return enabled;
  }

  public void addRole(Role role) {
    this.roles.add(role);
  }
}
