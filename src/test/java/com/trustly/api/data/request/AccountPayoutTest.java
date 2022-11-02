package com.trustly.api.data.request;

import com.trustly.api.SignedAPI;
import com.trustly.api.data.TrustlyApiSettings;
import com.trustly.api.data.response.Response;
import com.trustly.api.requestbuilders.AccountPayout;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AccountPayoutTest {

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
  void testAccountPayout() {

    Request accountPayoutRequest = new AccountPayout.Build(
        "https://notify.me",
        "AccountID",
        "EndUserId",
        "MessageId",
        "99.99",
        "SEK"
    )
        // Attributes
        .pspMerchant("Merchant Ltd.")
        .shopperStatement("MyBrand.com")
        .externalReference("23423525234")
        .merchantCategoryCode("5499")
        .pspMerchantURL("www.merchant.com")
        // Sender information
        .partytype("PERSON")
        .address("Street 1, 12345 Barcelona")
        .countryCode("SE")
        .firstname("Steve")
        .lastname("Smith")
        .customerID("123456789")
        .dateOfBirth("1990-03-31")
        .getRequest();

    Response accountPayoutResponse = api.sendRequest(accountPayoutRequest);

    Assertions.assertNotNull(accountPayoutResponse);
    Assertions.assertNotNull(accountPayoutResponse.getError());
  }
}
