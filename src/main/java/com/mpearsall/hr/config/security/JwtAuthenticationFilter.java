package com.mpearsall.hr.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mpearsall.hr.config.tenancy.DataSourceBasedMultiTenantConnectionProviderImpl;
import com.mpearsall.hr.dto.ApplicationError;
import com.mpearsall.hr.dto.Login;
import com.mpearsall.hr.entity.primary.user.User;
import com.mpearsall.hr.util.ApplicationErrorFactory;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import static com.mpearsall.hr.config.security.SecurityConstants.*;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  public static final String CLAIM_TENANT_ID = "tenant";

  private final AuthenticationManager authenticationManager;
  private final String jwtSecret;

  public JwtAuthenticationFilter(AuthenticationManager authenticationManager, String jwtSecret) {
    this.authenticationManager = authenticationManager;
    this.jwtSecret = jwtSecret;

    setFilterProcessesUrl(SecurityConstants.AUTH_LOGIN_URL);
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    try {
      final Login login = new ObjectMapper().readValue(request.getInputStream(), Login.class);

      return authenticationManager.authenticate(
          new TotpAuthenticationToken(login.getUsername(), login.getPassword(), login.getTotpCode(), new ArrayList<>())
      );
    } catch (IOException e) {
      // fixme
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
    final User user = (User) authResult.getPrincipal();

    final String client = user.getClient() != null ? user.getClient().getName()
        : DataSourceBasedMultiTenantConnectionProviderImpl.DEFAULT_TENANT_ID;

    final String token = Jwts.builder()
        .setSubject(user.getUsername())
        .claim("user", new ObjectMapper().convertValue(user, Map.class))
        .claim(CLAIM_TENANT_ID, client)
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .signWith(SignatureAlgorithm.HS512, jwtSecret.getBytes())
        .compact();
    response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);

    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.getWriter().write(new ObjectMapper().writeValueAsString(user));
  }

  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
    final ApplicationError applicationError = ApplicationErrorFactory.generate(failed);

    response.setStatus(applicationError.getStatus().value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.getWriter().write(new ObjectMapper().writeValueAsString(applicationError));
  }
}
