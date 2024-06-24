package com.trustly.api.domain.exceptions;

public class TrustlyRejectionException extends AbstractTrustlyApiException {

  private final Object reason;

  public TrustlyRejectionException(String message, Object reason) {
    super(message);
    this.reason = reason;
  }

  public Object getReason() {
    return reason;
  }
}
