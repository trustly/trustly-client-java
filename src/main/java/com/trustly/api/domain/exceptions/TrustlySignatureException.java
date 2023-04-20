package com.trustly.api.domain.exceptions;

public class TrustlySignatureException extends AbstractTrustlyApiException {

  public TrustlySignatureException(String message) {
    super(message);
  }

  public TrustlySignatureException(String message, Exception cause) {
    super(message, cause);
  }
}
