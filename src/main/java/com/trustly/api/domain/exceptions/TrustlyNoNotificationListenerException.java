package com.trustly.api.domain.exceptions;

public class TrustlyNoNotificationListenerException extends AbstractTrustlyApiException {

  public TrustlyNoNotificationListenerException(String message) {
    super(message);
  }

  public TrustlyNoNotificationListenerException(String message, Exception cause) {
    super(message, cause);
  }
}
