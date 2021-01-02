package com.mpearsall.hr.config.security;

import com.mpearsall.hr.config.CustomUserDetailsMapper;
import com.mpearsall.hr.config.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  private final CustomUserDetailsMapper customUserDetailsMapper;
  private final CustomUserDetailsService customUserDetailsService;
  private final PasswordEncoder passwordEncoder;

  @Value("${hr.ldap.enabled:false}")
  private boolean ldapEnabled;

  @Value("${hr.base-url:#{null}}")
  private String baseUrl;

  @Value("${hr.jwt.secret}")
  private String jwtSecret;

  @Value("${spring.ldap.urls:#{null}}")
  private String ldapUrl;

  public SecurityConfig(CustomUserDetailsMapper customUserDetailsMapper, CustomUserDetailsService customUserDetailsService,
                        PasswordEncoder passwordEncoder) {
    this.customUserDetailsMapper = customUserDetailsMapper;
    this.customUserDetailsService = customUserDetailsService;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    if (ldapEnabled) {
      auth.ldapAuthentication()
          .userDetailsContextMapper(customUserDetailsMapper)
          .userDnPatterns("uid={0},ou=people")
          .groupSearchBase("ou=groups")
          .contextSource()
          .url(ldapUrl)
          .and()
          .passwordCompare()
          .passwordEncoder(passwordEncoder)
          .passwordAttribute("userPassword");
    }

    auth.authenticationProvider(customDaoAuthenticationProvider());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors()
        .and()
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/api/calendar/**").permitAll()
        .antMatchers("/api/resource/**").permitAll()
        .antMatchers("/api/users/password-reset").permitAll()
        .antMatchers("/api/users/2fa-register").permitAll()
        .antMatchers("/api/users/forgotten-password/{username}").permitAll()
        .antMatchers("/api/**").authenticated()
        .antMatchers("/h2-console/**").permitAll()
        .anyRequest().permitAll()
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
        .and()
        .addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtSecret))
        .addFilter(new JwtAuthorizationFilter(authenticationManager(), jwtSecret))
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    // disable in production
    http.headers().frameOptions().disable();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    final CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.setAllowedOrigins(baseUrl != null ? Collections.singletonList(baseUrl) : Collections.singletonList("*"));
    corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
    corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
    corsConfiguration.setExposedHeaders(Collections.singletonList(HttpHeaders.AUTHORIZATION));
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfiguration);

    return source;
  }

  @Bean
  public DaoAuthenticationProvider customDaoAuthenticationProvider() {
    CustomDaoAuthenticationProvider authProvider = new CustomDaoAuthenticationProvider();
    authProvider.setUserDetailsService(customUserDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder);
    return authProvider;
  }
}
