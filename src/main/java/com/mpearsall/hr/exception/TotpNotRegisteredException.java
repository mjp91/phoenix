package com.mpearsall.hr.exception;

import org.springframework.security.authentication.AccountStatusException;

public class TotpNotRegisteredException extends AccountStatusException {
  public TotpNotRegisteredException(String msg) {
    super(msg);
  }

  public TotpNotRegisteredException(String msg, Throwable t) {
    super(msg, t);
  }
}
