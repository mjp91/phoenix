package com.mpearsall.hr.config.security;

class SecurityConstants {
  static final String AUTH_LOGIN_URL = "/api/login";
  static final String SECRET = "?D(G+KbPeShVmYq3s6v9y$B&E)H@McQfTjWnZr4u7w!z%C*F-JaNdRgUkXp2s5v8";
  static final long EXPIRATION_TIME = 864_000_000; // 10 days
  static final String TOKEN_PREFIX = "Bearer ";
  static final String HEADER_STRING = "Authorization";

  private SecurityConstants() {
  }
}
