package com.trustly.api.domain.exceptions;

import com.trustly.api.domain.base.ResponseError;

public class TrustlyErrorResponseException extends AbstractTrustlyApiException {

  private final transient ResponseError responseError;

  public TrustlyErrorResponseException(String message, Exception cause, ResponseError responseError) {
    super(message + " - " + responseError, cause);
    this.responseError = responseError;
  }

  public ResponseError getResponseError() {
    return responseError;
  }
}
