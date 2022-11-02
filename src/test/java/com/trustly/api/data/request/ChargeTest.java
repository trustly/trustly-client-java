package com.trustly.api.data.request;

import com.trustly.api.SignedAPI;
import com.trustly.api.commons.Currency;
import com.trustly.api.data.TrustlyApiSettings;
import com.trustly.api.data.response.Response;
import com.trustly.api.requestbuilders.Charge;
import com.trustly.api.requestbuilders.SelectAccount;
import java.util.UUID;
import java.util.function.Supplier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChargeTest {

  private static final Supplier<UUID> UUID_SUPPLIER = UUID::randomUUID;

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
  void testSelectAccount() {

    Request chargeRequest = new Charge.Build(
        "accountId",
        "https://notify.me",
        "EndUserId",
        UUID_SUPPLIER.get().toString(),
        "99.90",
        Currency.SEK,
        "Shopper statement",
        "test@trustly.com"
    )
        // Attributes
        .pspMerchant("Merchant Ltd.")
        .externalReference("External reference")
        .merchantCategoryCode("5499")
        .pspMerchantURL("www.merchant.com")
        .getRequest();

    Response chargeResponse = api.sendRequest(chargeRequest);

    Assertions.assertNotNull(chargeResponse);
    Assertions.assertNotNull(chargeResponse.getError());
  }
}
