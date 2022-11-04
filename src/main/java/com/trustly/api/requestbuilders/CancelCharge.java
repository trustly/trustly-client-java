package com.trustly.api.requestbuilders;

import com.trustly.api.commons.Method;
import com.trustly.api.data.request.Request;
import com.trustly.api.data.request.RequestParameters;
import com.trustly.api.data.request.requestdata.CancelChargeData;
import com.trustly.api.security.SignatureHandler;

public class CancelCharge {

  private final Request request = new Request();

  private CancelCharge(final Build builder) {
    final RequestParameters params = new RequestParameters();
    params.setUUID(SignatureHandler.generateNewUUID());
    params.setData(builder.data);

    request.setMethod(Method.CANCEL_CHARGE);
    request.setParams(params);
  }

  public Request getRequest() {
    return request;
  }

  public static class Build {

    private final CancelChargeData data = new CancelChargeData();

    public Build(final String orderId) {
      data.setOrderId(orderId);
    }

    public Request getRequest() {
      return new CancelCharge(this).getRequest();
    }
  }
}
