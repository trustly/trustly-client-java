package com.trustly.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.trustly.api.client.JsonRpcFactory;
import com.trustly.api.client.JsonRpcValidator;
import com.trustly.api.client.TrustlyApiClient;
import com.trustly.api.domain.Models.AckData;
import com.trustly.api.domain.Models.DepositRequest;
import com.trustly.api.domain.Models.RegisterAccountResponse;
import com.trustly.api.domain.Models.SelectAccountRequest;
import com.trustly.api.exceptions.TrustlyValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.io.InputStream;
import java.util.UUID;

@Execution(ExecutionMode.CONCURRENT)
class SerializerTest {

  @Test
  void testSerializingDepositWithoutValidation() {
    Serializer serializer = new Serializer();
    JsonRpcFactory factory = new JsonRpcFactory();

    DepositRequest.Params.Data request = new DepositRequest.Params.Data();
    request.setUsername("merchant_username");
    request.setPassword("merchant_password");
    request.setNotificationUrl("URL_to_your_notification_service");
    request.setEndUserId("12345");
    request.setMessageId("your_unique_deposit_id");

    DepositRequest.Params.Data.Attributes attributes = new DepositRequest.Params.Data.Attributes();
    attributes.setLocale("sv_SE");
    attributes.setCurrency("SEK");
    attributes.setIp("123.123.123.123");
    attributes.setMobilePhone("+46709876543");
    attributes.setFirstname("John");
    attributes.setLastname("Doe");
    attributes.setNationalIdentificationNumber("790131-1234");
    attributes.setCountry("SE");
    attributes.setEmail("test@trustly.com");
    attributes.setSuccessUrl("http://www.google.com/q?success");
    attributes.setFailUrl("http://www.google.com/q?fail");
    attributes.setShopperStatement("Shop");

    request.setAttributes(attributes);

    var jsonRpc = factory.create(request, "Deposit", UUID.randomUUID().toString());

    String serialized = serializer.serializeData(jsonRpc.getParams().getData());
    String expected = "AttributesCountrySECurrencySEKEmailtest@trustly.comFailURLhttp://www.google.com/q?failFirstnameJohnIP123.123.123.123LastnameDoeLocalesv_SEMobilePhone+46709876543NationalIdentificationNumber790131-1234ShopperStatementShopSuccessURLhttp://www.google.com/q?successEndUserID12345MessageIDyour_unique_deposit_idNotificationURLURL_to_your_notification_servicePasswordmerchant_passwordUsernamemerchant_username";

    Assertions.assertEquals(expected, serialized);
  }

  @Test
  void serializeResponseWithNonNullAnyMapAsPojo() {

    RegisterAccountResponse.Result.Data data = RegisterAccountResponse.Result.Data.builder()
      .accountID("123456789")
      .bank("BankA")
      .clearingHouse("SWEDEN")
      .build();

    Serializer serializer = new Serializer();
    String serialized = serializer.serializeData(data);

    Assertions.assertEquals("accountid123456789bankBankAclearinghouseSWEDEN", serialized);
  }

  @Test
  void serializeResponseWithNonEmptyAnyMapAsPOJO() {

    RegisterAccountResponse.Result.Data data = RegisterAccountResponse.Result.Data.builder()
      .accountID("123456789")
      .bank("BankA")
      .clearingHouse("SWEDEN")
      .additionalProperty("key", "value")
      .build();

    Serializer serializer = new Serializer();
    String serialized = serializer.serializeData(data);

    Assertions.assertEquals("accountid123456789bankBankAclearinghouseSWEDENkeyvalue", serialized);
  }

  @Test
  void serializeResponseWithNonEmptyAnyMapAsNode() {

    RegisterAccountResponse.Result.Data data = RegisterAccountResponse.Result.Data.builder()
      .accountID("123456789")
      .bank("BankA")
      .clearingHouse("SWEDEN")
      .additionalProperty("key", "value")
      .build();

    ObjectNode dataNode = TrustlyApiClientSettings.DEFAULT_OBJECT_MAPPER.valueToTree(data);
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

      var rpcResponse = client.createResponsePackage(
        "account",
        "e76ffbe5-e0f9-4402-8689-f868ed2021f8",
        AckData.builder()
          .status(AckData.Status.OK)
          .build()
      );

      String serialized = serializer.serializeData(rpcResponse.getResult().getData());

      Assertions.assertEquals("statusOK", serialized);

      var signature = signer.sign(
        rpcResponse.getResult().getData(),
        rpcResponse.getResult().getMethod(),
        rpcResponse.getResult().getUUID()
      );

      Assertions.assertEquals(
        "J28IN0yXZN3dlV2ikg4nQKwnP98kso8lzpmuwBcfbXr8i3XeEyydRM4jRwsOOeF0ilGuXyr1Kyb3+1j4mVtgU0SwjVgBHWrYPMegNeykY3meto/aoATH0mvop4Ex1OKO7w/S/ktR2J0J5Npn/EuiKGiVy5GztHYTh9hWmZBCElYPZf4Rsd1CJQJAPlZeAuRcrb5dnbiGJvTEaL/7VLcPT27oqAUefSNb/zNt5yL+wH6BihlkpZ/mtE61lX5OpC46iql6hpsrlOBD3BroYfcwgk1t3YdcNOhVWrmkrlVptGQ/oy6T/LSIKbkG/tJsuV8sl6w1Z31IesK6MZDfSJbcXw==",
        signature
      );
    }
  }

  @Test
  void testMissingDepositShopperStatement() {

    JsonRpcFactory factory = new JsonRpcFactory();
    JsonRpcValidator validator = new JsonRpcValidator();

    var jsonRpc = factory.create(
      DepositRequest.Params.Data.builder()
        .username("merchant_username")
        .password("merchant_password")
        .notificationUrl("https://someurl.fake")
        .endUserId("12345")
        .messageId("your_unique_deposit_id")
        .attributes(
          DepositRequest.Params.Data.Attributes.builder()
            .country("SE")
            .locale("sv_SE")
            .currency("SEK")
            .ip("123.123.123.123")
            .mobilePhone("+46709876543")
            .firstname("John")
            .lastname("Doe")
            .nationalIdentificationNumber("790131-1234")
            .successUrl("https://google.com")
            .failUrl("https://google.com")
            .mobilePhone("0701234567")
            .email("name@site.com")
            .build()
        )
        .build(),
      "Deposit",
      UUID.randomUUID().toString()
    );

    jsonRpc.getParams().setSignature("<none>");

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

    var jsonRpc = factory.create(
      DepositRequest.Params.Data.builder()
        .username("merchant_username")
        .password("merchant_password")
        .notificationUrl("https://someurl.fake")
        .endUserId("12345")
        .messageId("your_unique_deposit_id")
        .attributes(
          DepositRequest.Params.Data.Attributes.builder()
            .country("SE")
            .locale("sv_SE")
            .currency("SEK")
            .ip("123.123.123.123")
            .mobilePhone("+46709876543")
            .lastname("Doe")
            .nationalIdentificationNumber("790131-1234")
            .shopperStatement("Shopper Statement")
            .successUrl("https://google.com")
            .failUrl("https://google.com")
            .email("name@site.com")
            .build()
        )
        .build(),
      "Deposit",
      UUID.randomUUID().toString()
    );

    jsonRpc.getParams().setSignature("<none>");

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

    var jsonRpc = factory.create(
      SelectAccountRequest.Params.Data.builder()
        .username("merchant_username")
        .password("merchant_password")
        .notificationUrl("https://someurl.fake")
        .endUserId("12345")
        .messageId("your_unique_deposit_id")
        .attributes(
          SelectAccountRequest.Params.Data.Attributes.builder()
            .country("SE")
            .locale("sv_SE")
            .ip("123.123.123.123")
            .mobilePhone("+46709876543")
            .firstname("John")
            .lastname("Doe")
            .nationalIdentificationNumber("790131-1234")
            .successUrl("https://google.com")
            .failUrl("https://google.com")
            .mobilePhone("0701234567")
            .build()
        )
        .build(),
      "SelectAccount",
      UUID.randomUUID().toString()
    );

    jsonRpc.getParams().setSignature("<none>");

    // ShopperStatement is NOT specified -- but we should NOT throw exception, since that validation group is not specified.
    validator.validate(jsonRpc);
  }

  @Test
  void testRequestData() throws Exception {

    var requestData = new RequestData(
      new SenderInformation("2020-01-02")
    );

    final var om = new ObjectMapper();
    final var jsonString = om.writeValueAsString(requestData);

    Assertions.assertEquals("{\"senderInformation\":{\"DateOfBirth\":\"2020-01-02\"}}", jsonString);

    final var value1 = om.readValue("\"foo\"", SingleProp.class);
    Assertions.assertEquals(value1.getKind(), "foo");

    Assertions.assertThrowsExactly(MismatchedInputException.class, () -> om.readValue("{\"kind\": \"foo\"}", SingleProp.class));
  }

  private static class RequestData {
    private final SenderInformation senderInformation;

    public RequestData(@JsonProperty(value = "senderInformation") SenderInformation senderInformation) {
      this.senderInformation = senderInformation;
    }

    public SenderInformation getSenderInformation() {
      return this.senderInformation;
    }
  }

  private static class SenderInformation {
    @JsonProperty(value = "DateOfBirth")
    private final String dateOfBirth;

    public SenderInformation(@JsonProperty(value = "DateOfBirth") String dateOfBirth) {
      this.dateOfBirth = dateOfBirth;
    }

    public String getDateOfBirth() {
      return this.dateOfBirth;
    }
  }

  private static class SingleProp {
    private final String kind;

    @JsonCreator
    public SingleProp(String kind) {
      this.kind = kind;
    }

    public String getKind() {
      return this.kind;
    }
  }
}
