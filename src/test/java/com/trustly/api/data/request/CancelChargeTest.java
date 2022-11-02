package com.trustly.api.data.request;

import com.trustly.api.SignedAPI;
import com.trustly.api.data.TrustlyApiSettings;
import com.trustly.api.data.response.Response;
import com.trustly.api.requestbuilders.CancelCharge;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CancelChargeTest {

  private SignedAPI api;

  @BeforeEach
  void setup() {
    api = new SignedAPI();

    // Init for test environment
    api.init(
        TrustlyApiSettings.getClientPrivateKeyFromUserHome(),
        TrustlyApiSettings.getClientPublicKeyFromUserHome(),
        TrustlyApiSettings.getClientUsername(),
        TrustlyApiSettings.getClientPassword(),
        true
    );
  }

  @Test
  void testCancelCharge() {

    Request cancelChargeRequest = new CancelCharge.Build("accountId")
        .getRequest();

    Response cancelChargeResponse = api.sendRequest(cancelChargeRequest);

    // Order won't exist, assert error response
    Assertions.assertNotNull(cancelChargeResponse);
    Assertions.assertNotNull(cancelChargeResponse.getError());
  }

}
