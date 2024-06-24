package com.trustly.api.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.trustly.api.domain.exceptions.TrustlySignatureException;

import static com.trustly.api.domain.Models.*;

public interface JsonRpcSigner {

  <D extends AbstractRequestData, P extends JsonRpcRequestParams<D>> JsonRpcRequest<P> sign(JsonRpcRequest<P> request);

  <D, T extends ResponseResult<D>> JsonRpcResponse<T> sign(JsonRpcResponse<T> response);

//  <D, P extends JsonRpcNotificationParams<D>> void verify(JsonRpcNotification<P> request) throws TrustlySignatureException;

  void verify(String method, String uuid, JsonNode dataNode, String expectedSignature) throws TrustlySignatureException;

  <D, T extends ResponseResult<D>> void verify(JsonRpcResponse<T> response, JsonNode nodeResponse) throws TrustlySignatureException;
}
