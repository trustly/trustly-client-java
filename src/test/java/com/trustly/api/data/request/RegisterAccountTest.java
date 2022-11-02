package com.trustly.api.data.request;

import com.trustly.api.SignedAPI;
import com.trustly.api.data.TrustlyApiSettings;
import com.trustly.api.data.response.Response;
import com.trustly.api.requestbuilders.RegisterAccount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class RegisterAccountTest {

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
  void testRegisterAccount() {

    Request registerAccountRequest = new RegisterAccount.Build(
        "123123",
        "SWEDEN",
        "6112",
        "69706212",
        "Steve",
        "Smith"
    )
        .addressCountry("SE")
        .dateOfBirth("1990-01-20")
        .email("test@trustly.com")
        .getRequest();

    Response registerAccountResponse = api.sendRequest(registerAccountRequest);

    Assertions.assertNotNull(registerAccountResponse.getResult().getData());
    Assertions.assertNull(registerAccountResponse.getError());
  }
}
