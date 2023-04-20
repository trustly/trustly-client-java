package com.trustly.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.trustly.api.client.DefaultJsonRpcSigner;
import com.trustly.api.client.JsonRpcFactory;
import com.trustly.api.client.JsonRpcSigner;
import com.trustly.api.client.JsonRpcValidator;
import com.trustly.api.client.Serializer;
import com.trustly.api.client.TrustlyApiClient;
import com.trustly.api.client.TrustlyApiClientSettings;
import com.trustly.api.domain.base.JsonRpcRequest;
import com.trustly.api.domain.base.JsonRpcResponse;
import com.trustly.api.domain.base.NotificationResponse;
import com.trustly.api.domain.exceptions.TrustlyValidationException;
import com.trustly.api.domain.methods.deposit.DepositRequestData;
import com.trustly.api.domain.methods.deposit.DepositRequestDataAttributes;
import com.trustly.api.domain.methods.registeraccount.RegisterAccountResponseData;
import com.trustly.api.domain.methods.selectaccount.SelectAccountRequestData;
import com.trustly.api.domain.methods.selectaccount.SelectAccountRequestDataAttributes;
import java.io.InputStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SerializerTest {

  @Test
  void testSerializingDepositWithoutValidation() {
    Serializer serializer = new Serializer();
    JsonRpcFactory factory = new JsonRpcFactory();

    DepositRequestData request = new DepositRequestData();
    request.setUsername("merchant_username");
    request.setPassword("merchant_password");
    request.setNotificationUrl("URL_to_your_notification_service");
    request.setEndUserId("12345");
    request.setMessageId("your_unique_deposit_id");

    DepositRequestDataAttributes attributes = new DepositRequestDataAttributes();
    attributes.setLocale("sv_SE");
    attributes.setCurrency("SEK");
    attributes.setIp("123.123.123.123");
    attributes.setMobilePhone("+46709876543");
    attributes.setFirstname("John");
    attributes.setLastname("Doe");
    attributes.setNationalIdentificationNumber("790131-1234");

    request.setAttributes(attributes);

    JsonRpcRequest<DepositRequestData> jsonRpc = factory.create(request, "Deposit");

    String serialized = serializer.serializeData(jsonRpc.getParams().getData());
    String expected = "AttributesCurrencySEKFirstnameJohnIP123.123.123.123LastnameDoeLocalesv_SEMobilePhone+46709876543NationalIdentificationNumber790131-1234EndUserID12345MessageIDyour_unique_deposit_idNotificationURLURL_to_your_notification_servicePasswordmerchant_passwordUsernamemerchant_username";

    Assertions.assertEquals(expected, serialized);
  }

  @Test
  void serializeResponseWithNonNullAnyMapAsPojo() throws Exception {

    RegisterAccountResponseData data = RegisterAccountResponseData.builder()
      .accountId("123456789")
      .bank("BankA")
      .clearingHouse("SWEDEN")
      .build();

    Serializer serializer = new Serializer();
    String serialized = serializer.serializeData(data);

    Assertions.assertEquals("accountid123456789bankBankAclearinghouseSWEDENdescriptor", serialized);
  }

  @Test
  void serializeResponseWithNonEmptyAnyMapAsPOJO() throws Exception {

    RegisterAccountResponseData data = RegisterAccountResponseData.builder()
      .accountId("123456789")
      .bank("BankA")
      .clearingHouse("SWEDEN")
      .any("key", "value")
      .build();

    Serializer serializer = new Serializer();
    String serialized = serializer.serializeData(data);

    Assertions.assertEquals("accountid123456789bankBankAclearinghouseSWEDENdescriptorkeyvalue", serialized);
  }

  @Test
  void serializeResponseWithNonEmptyAnyMapAsNode() throws Exception {

    RegisterAccountResponseData data = RegisterAccountResponseData.builder()
      .accountId("123456789")
      .bank("BankA")
      .clearingHouse("SWEDEN")
      .any("key", "value")
      .build();

    ObjectNode dataNode = new ObjectMapper().valueToTree(data);
    dataNode.remove("descriptor");

    Serializer serializer = new Serializer();
    String serialized = serializer.serializeNode(dataNode);

    Assertions.assertEquals("accountid123456789bankBankAclearinghouseSWEDENkeyvalue", serialized);
  }

  @Test
  void testNullProperties() throws Exception {
    Serializer serializer = new Serializer();

    TrustlyApiClientSettings settings;
    try (InputStream merchantPrivateKey = this.getClass().getResourceAsStream("/keys/merchant_private_key.pem")) {
      try (InputStream merchantPublicKey = this.getClass().getResourceAsStream("/keys/merchant_public_key.pem")) {
        settings = TrustlyApiClientSettings
          .forTest()
          .withCredentials("merchant_username", "merchant_password")
          .withCertificatesFromStreams(merchantPublicKey, merchantPrivateKey)
          .andTrustlyCertificate();
      }
    }

    try (TrustlyApiClient client = new TrustlyApiClient(settings)) {
      JsonRpcSigner signer = new DefaultJsonRpcSigner(serializer, settings);

      JsonRpcResponse<NotificationResponse> rpcResponse = client.createResponsePackage(
        "account",
        "e76ffbe5-e0f9-4402-8689-f868ed2021f8",
        NotificationResponse.builder()
          .status("OK")
          .build()
      );

      String serialized = serializer.serializeData(rpcResponse.getData());

      Assertions.assertEquals("statusOK", serialized);

      JsonRpcResponse<NotificationResponse> signedResponse = signer.sign(rpcResponse);

      Assertions.assertEquals(
        "J28IN0yXZN3dlV2ikg4nQKwnP98kso8lzpmuwBcfbXr8i3XeEyydRM4jRwsOOeF0ilGuXyr1Kyb3+1j4mVtgU0SwjVgBHWrYPMegNeykY3meto/aoATH0mvop4Ex1OKO7w/S/ktR2J0J5Npn/EuiKGiVy5GztHYTh9hWmZBCElYPZf4Rsd1CJQJAPlZeAuRcrb5dnbiGJvTEaL/7VLcPT27oqAUefSNb/zNt5yL+wH6BihlkpZ/mtE61lX5OpC46iql6hpsrlOBD3BroYfcwgk1t3YdcNOhVWrmkrlVptGQ/oy6T/LSIKbkG/tJsuV8sl6w1Z31IesK6MZDfSJbcXw==",
        signedResponse.getSignature()
      );
    }
  }

  @Test
  void testMissingDepositShopperStatement() {

    JsonRpcFactory factory = new JsonRpcFactory();
    JsonRpcValidator validator = new JsonRpcValidator();

    JsonRpcRequest<DepositRequestData> jsonRpc = factory.create(
      DepositRequestData.builder()
        .username("merchant_username")
        .password("merchant_password")
        .notificationUrl("https://someurl.fake")
        .endUserId("12345")
        .messageId("your_unique_deposit_id")
        .attributes(
          DepositRequestDataAttributes.builder()
            .country("SE")
            .locale("sv_SE")
            .currency("SEK")
            .ip("123.123.123.123")
            .mobilePhone("+46709876543")
            .firstname("John")
            .lastname("Doe")
            .nationalIdentificationNumber("790131-1234")
            .successUrl("https://google.com")
            .failURL("https://google.com")
            .mobilePhone("0701234567")
            .email("name@site.com")
            .build()
        )
        .build(),
      "Deposit"
    );

    try {
      validator.validate(jsonRpc);
      Assertions.fail("Expected an exception since ShopperStatement is not specified");
    } catch (TrustlyValidationException ignored) {
      // Everything is fine
    }

    jsonRpc.getParams().getData().getAttributes().setShopperStatement("A Statement");

    Assertions.assertDoesNotThrow(() -> validator.validate(jsonRpc));
  }

  @Test
  void testMissingDepositFirstName() {

    JsonRpcFactory factory = new JsonRpcFactory();
    JsonRpcValidator validator = new JsonRpcValidator();

    JsonRpcRequest<DepositRequestData> jsonRpc = factory.create(
      DepositRequestData.builder()
        .username("merchant_username")
        .password("merchant_password")
        .notificationUrl("https://someurl.fake")
        .endUserId("12345")
        .messageId("your_unique_deposit_id")
        .attributes(
          DepositRequestDataAttributes.builder()
            .country("SE")
            .locale("sv_SE")
            .currency("SEK")
            .ip("123.123.123.123")
            .mobilePhone("+46709876543")
            .lastname("Doe")
            .nationalIdentificationNumber("790131-1234")
            .shopperStatement("Shopper Statement")
            .successUrl("https://google.com")
            .failURL("https://google.com")
            .email("name@site.com")
            .build()
        )
        .build(),
      "Deposit"
    );

    Assertions.assertThrows(
      TrustlyValidationException.class,
      () -> validator.validate(jsonRpc),
      "Expected an exception since firstName is not specified"
    );

    jsonRpc.getParams().getData().getAttributes().setFirstname("John");

    Assertions.assertDoesNotThrow(() -> validator.validate(jsonRpc));
  }

  @Test
  void testMissingNotDepositShopperStatement() throws Exception {

    JsonRpcFactory factory = new JsonRpcFactory();
    JsonRpcValidator validator = new JsonRpcValidator();

    JsonRpcRequest<SelectAccountRequestData> jsonRpc = factory.create(
      SelectAccountRequestData.builder()
        .username("merchant_username")
        .password("merchant_password")
        .notificationUrl("https://someurl.fake")
        .endUserId("12345")
        .messageId("your_unique_deposit_id")
        .attributes(
          SelectAccountRequestDataAttributes.builder()
            .country("SE")
            .locale("sv_SE")
            .ip("123.123.123.123")
            .mobilePhone("+46709876543")
            .firstname("John")
            .lastname("Doe")
            .nationalIdentificationNumber("790131-1234")
            .successUrl("https://google.com")
            .failURL("https://google.com")
            .mobilePhone("0701234567")
            .build()
        )
        .build(),
      "SelectAccount"
    );

    // ShopperStatement is NOT specified -- but we should NOT throw exception, since that validation group is not specified.
    validator.validate(jsonRpc);
  }
}
