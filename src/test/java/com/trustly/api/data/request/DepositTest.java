package com.trustly.api.data.request;

import com.trustly.api.SignedAPI;
import com.trustly.api.commons.Currency;
import com.trustly.api.data.TrustlyApiSettings;
import com.trustly.api.data.response.Response;
import com.trustly.api.requestbuilders.Deposit;
import java.util.UUID;
import java.util.function.Supplier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


class DepositTest {

  private static final Supplier<UUID> UUID_SUPPLIER = UUID::randomUUID;
  private static final Logger LOGGER = LoggerFactory.getLogger(DepositTest.class);

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
  void testDeposit() {

    Request depositRequest = new Deposit.Build(
        "https://test.trustly.com/trustlynotification",
        UUID_SUPPLIER.get().toString(),
        UUID_SUPPLIER.get().toString(),
        Currency.SEK,
        "Jon",
        "Doe",
        "test@example.com"
    )
        .externalReference("23423525234")
        .pspMerchant("Merchant Ltd.")
        .pspMerchantURL("www.merchant.com")
        .merchantCategoryCode("5499")
        .amount("22")
        .getRequest();

    Response depositResponse = api.sendRequest(depositRequest);

    Assertions.assertNotNull(depositResponse.getResult().getData());
    Assertions.assertNull(depositResponse.getError());
  }
}
