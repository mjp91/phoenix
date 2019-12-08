package com.mpearsall.hr.util;

import com.mpearsall.hr.dto.ApplicationError;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

public final class ApplicationErrorFactory {
  private ApplicationErrorFactory() {
  }

  public static ApplicationError generate(Throwable throwable) {
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    String message = throwable.getMessage();

    if (throwable instanceof ConstraintViolationException) {
      status = HttpStatus.BAD_REQUEST;

      message = ((ConstraintViolationException) throwable).getConstraintViolations().stream()
          .map(constraintViolation -> constraintViolation.getPropertyPath() + ": " + constraintViolation.getMessage())
          .collect(Collectors.joining(" | "));

    } else if (throwable instanceof DataIntegrityViolationException) {
      status = HttpStatus.BAD_REQUEST;

      message = "Data integrity violation";
    } else if (throwable instanceof MethodArgumentNotValidException) {
      status = HttpStatus.BAD_REQUEST;

      message = ((MethodArgumentNotValidException) throwable).getBindingResult().getFieldErrors().stream()
          .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
          .collect(Collectors.joining(" | "));
    }

    return new ApplicationError(status, message);
  }
}
