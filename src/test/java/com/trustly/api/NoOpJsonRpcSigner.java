package com.trustly.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.trustly.api.client.JsonRpcSigner;
import com.trustly.api.domain.exceptions.TrustlySignatureException;

import static com.trustly.api.domain.Models.*;

class NoOpJsonRpcSigner implements JsonRpcSigner {

  @Override
  public <D extends AbstractRequestData, P extends JsonRpcRequestParams<D>> JsonRpcRequest<P> sign(JsonRpcRequest<P> request) {
    request.getParams().setSignature(String.format("<%s>", NoOpJsonRpcSigner.class.getName()));
    return request;
  }

  @Override
  public <D, T extends ResponseResult<D>> JsonRpcResponse<T> sign(JsonRpcResponse<T> response) {
    response.getResult().setSignature(String.format("<%s>", NoOpJsonRpcSigner.class.getName()));
    return response;
  }

  @Override
  public void verify(String method, String uuid, JsonNode dataNode, String expectedSignature) {

  }

  @Override
  public <D, T extends ResponseResult<D>> void verify(JsonRpcResponse<T> response, JsonNode nodeResponse) {

  }
}
