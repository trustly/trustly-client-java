package com.trustly.api.domain.exceptions;

public class TrustlyRejectionException extends AbstractTrustlyApiException {

  private String reason;

  public TrustlyRejectionException(String message) {
    super(message);
  }

  public TrustlyRejectionException(String message, String reason) {
    super(message);
    this.reason = reason;
  }

  public TrustlyRejectionException(String message, Exception cause) {
    super(message, cause);
  }

  public String getReason() {
    return reason;
  }
}
