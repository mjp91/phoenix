package com.mpearsall.hr.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mpearsall.hr.config.tenancy.TenantContext;
import com.mpearsall.hr.entity.primary.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

  private final String jwtSecret;

  public JwtAuthorizationFilter(AuthenticationManager authenticationManager, String jwtSecret) {
    super(authenticationManager);
    this.jwtSecret = jwtSecret;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
    String header = request.getHeader(SecurityConstants.HEADER_STRING);

    if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
      chain.doFilter(request, response);
      return;
    }

    Claims claims;
    try {
      claims = getTokenBody(request);
    } catch (JwtException e) {
      chain.doFilter(request, response);
      return;
    }

    setTenant(claims);
    UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(claims);

    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    chain.doFilter(request, response);
  }

  private void setTenant(Claims claims) {
    if (claims != null) {
      final String tenantId = (String) claims.get(JwtAuthenticationFilter.CLAIM_TENANT_ID);

      TenantContext.setTenantId(tenantId);
    }
  }

  private UsernamePasswordAuthenticationToken getAuthentication(Claims claims) {
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = null;

    if (claims != null) {
      UserDetails user = new ObjectMapper().convertValue(claims.get("user"), User.class);

      if (user != null) {
        usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
      }
    }

    return usernamePasswordAuthenticationToken;
  }

  /**
   * Parses and returns the body of the JWT token
   *
   * @param request the http request
   * @return token body
   */
  private Claims getTokenBody(HttpServletRequest request) {
    String token = request.getHeader(SecurityConstants.HEADER_STRING);
    Claims body = null;

    if (token != null) {
      body = Jwts.parser()
          .setSigningKey(jwtSecret.getBytes())
          .parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX, ""))
          .getBody();
    }

    return body;
  }
}
