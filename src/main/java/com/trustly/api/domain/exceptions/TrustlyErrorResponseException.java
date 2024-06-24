package com.trustly.api.domain.exceptions;

import static com.trustly.api.domain.Models.*;

public class TrustlyErrorResponseException extends AbstractTrustlyApiException {

  private final transient JsonRpcError responseError;

  public TrustlyErrorResponseException(String message, Exception cause, JsonRpcError responseError) {
    super(message + " - " + responseError, cause);
    this.responseError = responseError;
  }

  public JsonRpcError getResponseError() {
    return responseError;
  }
}
