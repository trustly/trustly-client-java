package com.trustly.api.domain.exceptions;

public class TrustlyNoNotificationClientException extends AbstractTrustlyApiException {

  public TrustlyNoNotificationClientException(String message) {
    super(message);
  }

  public TrustlyNoNotificationClientException(String message, Exception cause) {
    super(message, cause);
  }
}
