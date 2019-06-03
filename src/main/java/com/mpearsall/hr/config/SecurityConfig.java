package com.mpearsall.hr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  private final CustomUserDetailsMapper customUserDetailsMapper;

  public SecurityConfig(CustomUserDetailsMapper customUserDetailsMapper) {
    this.customUserDetailsMapper = customUserDetailsMapper;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.ldapAuthentication()
        .userDetailsContextMapper(customUserDetailsMapper)
        .userSearchBase("ou=people")
        .userSearchFilter("(uid={0})")
        .groupSearchBase("ou=groups")
        .groupSearchFilter("member={0}")
        .passwordCompare()
        .passwordEncoder(passwordEncoder())
        .passwordAttribute("userPassword")
        .and()
        .contextSource()
        .root("dc=hr,dc=com")
        .ldif("classpath:users.ldif");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .anyRequest().authenticated()
        .and()
        .httpBasic()
        .and()
        .cors()
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.setAllowedOrigins(Collections.singletonList("*"));
    corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
    corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
    corsConfiguration.setExposedHeaders(Collections.singletonList("Authorization"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfiguration);

    return source;
  }
}
