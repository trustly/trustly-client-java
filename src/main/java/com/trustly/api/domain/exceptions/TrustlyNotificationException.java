package com.trustly.api.domain.exceptions;

public class TrustlyNotificationException extends AbstractTrustlyApiException {

  public TrustlyNotificationException(String message) {
    super(message);
  }

  public TrustlyNotificationException(String message, Exception cause) {
    super(message, cause);
  }
}
