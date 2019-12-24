package com.mpearsall.hr.dto;

public enum ApplicationErrorType {
  BAD_REQUEST,
  UNAUTHORIZED,
  TOTP_REQUIRED,
  TOTP_NOT_REGISTERED,
  NOT_FOUND,
  FORBIDDEN,
  UNEXPECTED
}
