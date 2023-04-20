package com.trustly.api.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.trustly.api.domain.base.IRequest;
import com.trustly.api.domain.base.IRequestParams;
import com.trustly.api.domain.base.IRequestParamsData;
import com.trustly.api.domain.base.IResponseResultData;
import com.trustly.api.domain.base.JsonRpcRequest;
import com.trustly.api.domain.base.JsonRpcResponse;
import com.trustly.api.domain.exceptions.TrustlySignatureException;

public interface JsonRpcSigner {

  <T extends IRequestParamsData> JsonRpcRequest<T> sign(JsonRpcRequest<T> request);

  <T extends IResponseResultData> JsonRpcResponse<T> sign(JsonRpcResponse<T> response);

  <D extends IRequestParamsData, P extends IRequestParams<D>> void verify(IRequest<P> request) throws TrustlySignatureException;

  <T extends IResponseResultData> void verify(JsonRpcResponse<T> response, JsonNode nodeResponse) throws TrustlySignatureException;
}
