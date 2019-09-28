package com.mpearsall.hr.controller;

import com.mpearsall.hr.dto.ApplicationError;
import com.mpearsall.hr.exception.InvalidDetailsException;
import com.mpearsall.hr.exception.PermissionException;
import com.mpearsall.hr.exception.ResourceNotFoundException;
import com.mpearsall.hr.exception.StorageFileNotFoundException;
import com.mpearsall.hr.util.ApplicationErrorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
  private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler({
      InvalidDetailsException.class
  })
  protected ResponseEntity<ApplicationError> handleInvalidDetails(RuntimeException ex) {
    return handleException(HttpStatus.BAD_REQUEST, ex);
  }

  @ExceptionHandler({
      PermissionException.class,
      AccessDeniedException.class
  })
  protected ResponseEntity<ApplicationError> handlePermissionException(RuntimeException ex) {
    return handleException(HttpStatus.FORBIDDEN, ex);
  }

  @ExceptionHandler({
      ResourceNotFoundException.class,
      NoSuchElementException.class,
      StorageFileNotFoundException.class
  })
  protected ResponseEntity<ApplicationError> handleResourceNotFound(RuntimeException ex) {
    return handleException(HttpStatus.NOT_FOUND, ex);
  }

  @ExceptionHandler(TransactionSystemException.class)
  protected ResponseEntity<ApplicationError> handleTransactionSystemException(TransactionSystemException ex) {
    return handleUnexpectedException(ex.getRootCause());
  }

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<ApplicationError> handleUnexpectedException(Throwable ex) {
    return handleException(null, ex);
  }

  private ResponseEntity<ApplicationError> handleException(HttpStatus httpStatus, Throwable ex) {
    LOGGER.error(ex.getMessage(), ex);
    final ApplicationError applicationError = ApplicationErrorFactory.generate(ex);

    if (httpStatus == null) {
      httpStatus = applicationError.getStatus();
    } else {
      applicationError.setStatus(httpStatus);
    }

    return new ResponseEntity<>(applicationError, new HttpHeaders(), httpStatus);
  }
}
