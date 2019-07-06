package com.mpearsall.hr.controller;

import com.mpearsall.hr.dto.ApplicationError;
import com.mpearsall.hr.exception.InvalidDetailsException;
import com.mpearsall.hr.exception.PermissionException;
import com.mpearsall.hr.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
  private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler({
      InvalidDetailsException.class
  })
  protected ResponseEntity<Object> handleInvalidDetails(RuntimeException ex) {
    return handleException(HttpStatus.BAD_REQUEST, ex);
  }

  @ExceptionHandler({
      PermissionException.class
  })
  protected ResponseEntity<Object> handlePermissionException(RuntimeException ex) {
    return handleException(HttpStatus.FORBIDDEN, ex);
  }

  @ExceptionHandler({
      ResourceNotFoundException.class
  })
  protected ResponseEntity<Object> handleResourceNotFound(RuntimeException ex) {
    return handleException(HttpStatus.NOT_FOUND, ex);
  }

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<Object> handleUnexpectedException(Exception ex) {
    return handleException(HttpStatus.INTERNAL_SERVER_ERROR, ex);
  }

  private ResponseEntity<Object> handleException(HttpStatus httpStatus, Exception ex) {
    LOGGER.error(ex.getMessage(), ex);
    return new ResponseEntity<>(new ApplicationError(ex.getMessage()), new HttpHeaders(), httpStatus);
  }
}
