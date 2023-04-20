package com.trustly.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.trustly.api.client.JsonRpcSigner;
import com.trustly.api.domain.base.IRequest;
import com.trustly.api.domain.base.IRequestParams;
import com.trustly.api.domain.base.IRequestParamsData;
import com.trustly.api.domain.base.IResponseResultData;
import com.trustly.api.domain.base.JsonRpcRequest;
import com.trustly.api.domain.base.JsonRpcResponse;

class NoOpJsonRpcSigner implements JsonRpcSigner {

  @Override
  public <T extends IRequestParamsData> JsonRpcRequest<T> sign(JsonRpcRequest<T> request) {
    return request;
  }

  @Override
  public <T extends IResponseResultData> JsonRpcResponse<T> sign(JsonRpcResponse<T> response) {
    return response;
  }

  @Override
  public <D extends IRequestParamsData, P extends IRequestParams<D>> void verify(IRequest<P> request) {

  }

  @Override
  public <T extends IResponseResultData> void verify(JsonRpcResponse<T> response, JsonNode nodeResponse) {

  }
}
