package com.projects.microsensors.exception;

import java.io.Serial;

/**
 * Exception class representing HTTP 404(Not found).
 */
public class NotFoundException extends RuntimeException {
  @Serial
  private static final long serialVersionUID = 6539065432350987654L;

  public NotFoundException(String msg) {
    super(msg);
  }
}