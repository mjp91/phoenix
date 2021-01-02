package com.mpearsall.hr.exception;

public class InvalidDetailsException extends RuntimeException {
  public InvalidDetailsException(String message) {
    super(message);
  }
}
