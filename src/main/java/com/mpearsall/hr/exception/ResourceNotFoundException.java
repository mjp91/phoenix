package com.mpearsall.hr.exception;

public class ResourceNotFoundException extends RuntimeException {
  public ResourceNotFoundException(Object id, Class<?> type) {
    super(String.format("Resource of type %s with ID '%s' not found", type.getSimpleName(), id));
  }
}
