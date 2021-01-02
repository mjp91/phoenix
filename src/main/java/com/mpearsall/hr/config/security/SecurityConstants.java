package com.mpearsall.hr.config.security;

class SecurityConstants {
  static final String AUTH_LOGIN_URL = "/api/login";
  static final long EXPIRATION_TIME = 864_000_000; // 10 days
  static final String TOKEN_PREFIX = "Bearer ";
  static final String HEADER_STRING = "Authorization";

  private SecurityConstants() {
  }
}
