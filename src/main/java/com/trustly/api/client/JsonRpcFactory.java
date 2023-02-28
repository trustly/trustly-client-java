package com.trustly.api.client;

import com.trustly.api.domain.base.IRequestParamsData;
import com.trustly.api.domain.base.JsonRpcRequest;
import com.trustly.api.domain.base.RequestParams;
import java.util.UUID;

public class JsonRpcFactory {

  public <D extends IRequestParamsData> JsonRpcRequest<D> create(D requestData, String method) {
    return this.create(requestData, method, null);
  }

  public <D extends IRequestParamsData> JsonRpcRequest<D> create(D requestData, String method, String uuid) {

    if (uuid == null) {
      uuid = UUID.randomUUID().toString();
    }

    return JsonRpcRequest.<D>builder()
      .method(method)
      .params(
        RequestParams.<D>builder()
          .uuid(uuid)
          .data(requestData)
          .build()
      )
      .build();
  }
}
