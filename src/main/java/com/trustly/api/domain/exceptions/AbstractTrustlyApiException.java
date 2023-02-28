package com.trustly.api.domain.exceptions;

public abstract class AbstractTrustlyApiException extends Exception {

  protected AbstractTrustlyApiException(String message) {
    super(message);
  }

  protected AbstractTrustlyApiException(String message, Exception cause) {
    super(message, cause);
  }
}
