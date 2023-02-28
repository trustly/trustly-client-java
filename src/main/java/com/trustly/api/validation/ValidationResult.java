package com.trustly.api.validation;

public class ValidationResult {

  private final String errorMessage;

  public ValidationResult(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public String getErrorMessage() {
    return errorMessage;
  }
}
