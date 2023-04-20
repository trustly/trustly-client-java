package com.trustly.api.domain.exceptions;

public class TrustlyConnectionException extends AbstractTrustlyApiException {

  public TrustlyConnectionException(String message) {
    super(message);
  }

  public TrustlyConnectionException(String message, Exception cause) {
    super(message, cause);
  }
}
