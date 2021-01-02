package com.mpearsall.hr.exception;

import org.springframework.security.authentication.AccountStatusException;

public class TotpRequiredException extends AccountStatusException {
  public TotpRequiredException(String msg, Throwable t) {
    super(msg, t);
  }

  public TotpRequiredException(String msg) {
    super(msg);
  }
}
