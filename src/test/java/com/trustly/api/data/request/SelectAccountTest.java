package com.trustly.api.data.request;

import com.trustly.api.SignedAPI;
import com.trustly.api.data.TrustlyApiSettings;
import com.trustly.api.data.response.Response;
import com.trustly.api.requestbuilders.SelectAccount;
import java.util.UUID;
import java.util.function.Supplier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SelectAccountTest {

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

    Request selectAccountRequest = new SelectAccount.Build(
        "https://notify.me",
        "EndUserId",
        UUID_SUPPLIER.get().toString()
    )
        // Attributes
        .pspMerchant("Merchant Ltd.")
        .shopperStatement("MyBrand.com")
        .merchantCategoryCode("5499")
        .pspMerchantURL("www.merchant.com")
        .getRequest();

    Response selectAccountResponse = api.sendRequest(selectAccountRequest);

    Assertions.assertNotNull(selectAccountResponse);
    Assertions.assertNotNull(selectAccountResponse.getResult());
    Assertions.assertNull(selectAccountResponse.getError());
  }
}
