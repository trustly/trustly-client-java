package com.trustly.api.domain.exceptions;

public class TrustlyRejectionException extends AbstractTrustlyApiException {

  private final String reason;

  public TrustlyRejectionException(String message, String reason) {
    super(message);
    this.reason = reason;
  }

  public String getReason() {
    return reason;
  }
}
