package com.mpearsall.hr.exception;

public class ResourceNotFoundException extends RuntimeException {
  private Long id;
  private Class<?> type;

  public ResourceNotFoundException(Long id, Class<?> type) {
    super("Resource of type " + type + " with ID " + id + " not found");
    this.id = id;
    this.type = type;
  }

  public Long getId() {
    return id;
  }

  public Class<?> getType() {
    return type;
  }
}
