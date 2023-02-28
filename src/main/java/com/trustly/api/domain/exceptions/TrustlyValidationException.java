package com.trustly.api.domain.exceptions;

public class TrustlyValidationException extends AbstractTrustlyApiException {

  public TrustlyValidationException(String message) {
    super(message);
  }

  public TrustlyValidationException(String message, Exception cause) {
    super(message, cause);
  }
}
