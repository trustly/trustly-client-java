package com.trustly.api.domain.exceptions;

/**
 * Wrapping exception for general error situations. Check the actual exception using {@link Exception#getCause()}.
 */
public class TrustlyRequestException extends Exception {

  public TrustlyRequestException(Throwable cause) {
    super(cause);
  }
}
