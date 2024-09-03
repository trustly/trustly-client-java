package com.trustly.api.client;

import static com.trustly.api.domain.Models.*;

import java.util.Objects;
import java.util.UUID;

public class JsonRpcFactory {

  public <TReqAttr extends AbstractRequestDataAttributes, TReqData extends AbstractRequestData<TReqAttr>> JsonRpcRequest<JsonRpcRequestParams<TReqData>> create(TReqData requestData, String method, String uuid) {

    return JsonRpcRequest.<JsonRpcRequestParams<TReqData>>builder()
      .method(method)
      .params(
        JsonRpcRequestParams.<TReqData>builder()
          .uuid(Objects.requireNonNullElseGet(uuid, () -> UUID.randomUUID().toString()))
          .data(requestData)
          .build()
      )
      .build();
  }
}
