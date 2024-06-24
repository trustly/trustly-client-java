package com.trustly.api;

import com.trustly.api.client.TrustlyApiClient;
import com.trustly.api.client.TrustlyApiClientSettings;
import com.trustly.api.domain.exceptions.TrustlyErrorResponseException;
import com.trustly.api.domain.exceptions.TrustlyRequestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import static com.trustly.api.domain.Models.*;

import java.util.UUID;

/**
 * These test will only work if a local environment exists. At the USER_HOME root these files is needed: trustly_client_username.txt - your
 * username trustly_client_password.txt - your password trustly_client_public.pem - your public key trustly_client_public.pem - your private
 * key
 * <p>
 * If those files exist and are correct you are able to make requests to Trustly's test environment
 */
@Execution(ExecutionMode.CONCURRENT)
class RequestsTest {

  private final TrustlyApiClientSettings settings = TrustlyApiClientSettings.forTest()
    .withCredentialsFromUserHome()
    .withCertificatesFromUserHome()
    .andTrustlyCertificate();

  @Test
  void testDeposit() throws Exception {

    try (TrustlyApiClient client = new TrustlyApiClient(settings)) {

      DepositRequest.Params.Data request = DepositRequest.Params.Data.builder()
        .notificationUrl("https://fake.test.notification.trustly.com")
        .endUserId("john.doe@trustly.com")
        .messageId(UUID.randomUUID().toString())
        .attributes(
          DepositRequest.Params.Data.Attributes.builder()
            .currency("EUR")
            .amount("100.00")
            .firstname("John")
            .lastname("Doe")
            .email("john.doe@trustly.com")
            .country("SE")
            .locale("sv_SE")
            .shopperStatement("Trustly Test Deposit")
            .successUrl("https://google.com")
            .failUrl("https://google.com")
            .mobilePhone("0701234567")
            .build()
        )
        .build();

      DepositResponse.Result.Data response = client.deposit(request);

      Assertions.assertNotNull(response);
      Assertions.assertNotNull(response.getURL());
    }
  }

  @Test
  void testAccountPayout() {

    try (TrustlyApiClient client = new TrustlyApiClient(settings)) {

      AccountPayoutRequest.Params.Data data = AccountPayoutRequest.Params.Data.builder()
        .accountId("AccountID")
        .accountId("AccountID")
        .endUserId("EndUserId")
        .messageId("MessageId")
        .amount("99.99")
        .currency("SEK")
        .notificationUrl("https://notify.me")
        .attributes(
          AccountPayoutRequest.Params.Data.Attributes.builder()
            .pspMerchant("Merchant Ltd.")
            .shopperStatement("MyBrand.com")
            .externalReference("23423525234")
            .merchantCategoryCode("5499")
            .pspMerchantUrl("www.merchant.com")
            .senderInformation(
              SenderInformation.builder()
                .partytype(RecipientOrSenderInformation.PartyTypeKind.PERSON)
                .address("Street 1, 12345 Barcelona")
                .countryCode("SE")
                .firstname("Steve")
                .lastname("Smith")
                .customerId("123456789")
                .dateOfBirth("1990-03-31")
                .build()
            )
            .build()
        )
        .build();

      try {

        client.accountPayout(data);
        Assertions.fail("Should fail");

      } catch (TrustlyRequestException ex) {

        Assertions.assertEquals(TrustlyErrorResponseException.class, ex.getCause().getClass());
        Assertions.assertEquals(620, ((TrustlyErrorResponseException) ex.getCause()).getResponseError().getCode());
      }
    }
  }

  @Test
  void testCancelCharge() {

    try (TrustlyApiClient client = new TrustlyApiClient(settings)) {

      CancelChargeRequest.Params.Data data = CancelChargeRequest.Params.Data.builder()
        .orderId("orderId")
        .build();

      try {

        client.cancelCharge(data);
        Assertions.fail("Should fail, since there has been no user interaction to select an account with selectAccount");

      } catch (TrustlyRequestException ex) {

        Assertions.assertEquals(TrustlyErrorResponseException.class, ex.getCause().getClass());
        Assertions.assertEquals(620, ((TrustlyErrorResponseException) ex.getCause()).getResponseError().getCode());
      }
    }
  }

  @Test
  void testCharge() {

    try (TrustlyApiClient client = new TrustlyApiClient(settings)) {

      ChargeRequest.Params.Data data = ChargeRequest.Params.Data.builder()
        .accountId("accountId")
        .notificationUrl("https://notify.me")
        .endUserId("EndUserId")
        .messageId(UUID.randomUUID().toString())
        .amount("99.90")
        .currency("SEK")
        .attributes(
          ChargeRequest.Params.Data.Attributes.builder()
            .shopperStatement("Shopper statement")
            .pspMerchant("Merchant Ltd.")
            .externalReference("External reference")
            .merchantCategoryCode("5499")
            .pspMerchantUrl("www.merchant.com")
            .email("test@trustly.com")
            .build()
        )
        .build();

      try {

        client.charge(data);
        Assertions.fail("Should fail, since there has been no user interaction to select an account with selectAccount");

      } catch (TrustlyRequestException ex) {

        Assertions.assertEquals(TrustlyErrorResponseException.class, ex.getCause().getClass());
        Assertions.assertEquals(620, ((TrustlyErrorResponseException) ex.getCause()).getResponseError().getCode());
      }
    }
  }

  @Test
  void testRefund() {

    try (TrustlyApiClient client = new TrustlyApiClient(settings)) {

      RefundRequest.Params.Data data = RefundRequest.Params.Data.builder()
        .orderId("123456")
        .amount("22")
        .currency("SEK")
        .attributes(
          RefundRequest.Params.Data.Attributes.builder()
            .externalReference("123")
            .build()
        )
        .build();

      try {

        client.refund(data);
        Assertions.fail("Should fail");

      } catch (TrustlyRequestException ex) {

        Assertions.assertEquals(TrustlyErrorResponseException.class, ex.getCause().getClass());

        // ERROR_INVALID_ORDER_ID
        Assertions.assertEquals(655, ((TrustlyErrorResponseException) ex.getCause()).getResponseError().getCode());
      }
    }
  }

  @Test
  void testRegisterAccount() throws Exception {

    try (TrustlyApiClient client = new TrustlyApiClient(settings)) {

      RegisterAccountResponse.Result.Data registerAccountResponse = client.registerAccount(
        RegisterAccountRequest.Params.Data.builder()
          .accountNumber("69706212")
          .clearingHouse("SWEDEN")
          .bankNumber("6112")
          .endUserId("123123")
          .firstname("Steve")
          .lastname("Smith")
          .attributes(
            RegisterAccountRequest.Params.Data.Attributes.builder()
              .addressCountry("SE")
              .dateOfBirth("1990-01-20")
              .email("test@trustly.com")
              .build()
          )
          .build()
      );

      Assertions.assertNotNull(registerAccountResponse);
//      Assertions.assertNotNull(registerAccountResponse.getAccountID());
    }
  }

  @Test
  void testSelectAccount() throws Exception {

    try (TrustlyApiClient client = new TrustlyApiClient(settings)) {

      SelectAccountResponse.Result.Data selectAccountResponse = client.selectAccount(
        SelectAccountRequest.Params.Data.builder()
          .endUserId("EndUserId")
          .notificationUrl("https://notify.me")
          .messageId(UUID.randomUUID().toString())
          .attributes(
            SelectAccountRequest.Params.Data.Attributes.builder()
              .country("SE")
              .firstname("Steve")
              .lastname("Smith")
              .locale("en_US")
              .pspMerchant("Merchant Ltd.")
              .shopperStatement("MyBrand.com")
              .merchantCategoryCode("5499")
              .pspMerchantUrl("www.merchant.com")
              .successUrl("https://google.com")
              .failUrl("https://google.com")
              .build()
          )
          .build()
      );

      Assertions.assertNotNull(selectAccountResponse);
    }
  }

  @Test
  void testWithdraw() throws Exception {

    try (TrustlyApiClient client = new TrustlyApiClient(settings)) {

      WithdrawRequest.Params.Data data = WithdrawRequest.Params.Data.builder()
        .endUserId(UUID.randomUUID().toString())
        .messageId(UUID.randomUUID().toString())
        .notificationUrl("https://test.trustly.com/trustlynotification")
        .currency("SEK")
        .attributes(
          WithdrawRequest.Params.Data.Attributes.builder()
            .firstname("Jon")
            .lastname("Doe")
            .email("test@example.com")
            .dateOfBirth("1990-01-20")
            .pspMerchant("Merchant Ltd.")
            .shopperStatement("MyBrand.com")
            .merchantCategoryCode("5499")
            .pspMerchantUrl("www.merchant.com")

            .country("SE")
            .locale("en_US")

            .successUrl("https://google.com")
            .failUrl("https://google.com")
            .mobilePhone("0701234567")

            .build()
        )
        .build();

      WithdrawResponse.Result.Data response = client.withdraw(data);

      Assertions.assertNotNull(response.getURL());
      Assertions.assertNotEquals(0, response.getOrderID());
    }
  }

  @Test
  void testRegisterAccountPayout() throws Exception {

    try (TrustlyApiClient client = new TrustlyApiClient(settings)) {

      String uniqueMessageId = UUID.randomUUID().toString();

      RegisterAccountPayoutResponse.Result.Data registerAccountPayoutResponse = client.registerAccountPayout(
        RegisterAccountPayoutRequest.Params.Data.builder()
          .endUserId("123123")
          .clearingHouse("SWEDEN")
          .bankNumber("6112")
          .accountNumber("69706212")
          .firstname("Steve")
          .lastname("Smith")
          .notificationUrl("https://test.trustly.com/trustlynotification")
          .messageId(uniqueMessageId)
          .amount("99.99")
          .currency("SEK")
          .attributes(
            RegisterAccountPayoutRequest.Params.Data.Attributes.builder()
              .pspMerchant("Merchant Ltd.")
              .shopperStatement("MyBrand.com")
              .externalReference("23423525234")
              .merchantCategoryCode("5499")
              .pspMerchant("pspMerchant")
              .pspMerchantUrl("www.merchant.com")
              .merchantCategoryCode("5499")
              .senderInformation(
                SenderInformation.builder()
                  .partytype(RecipientOrSenderInformation.PartyTypeKind.PERSON)
                  .address("Street 1, 12345 Barcelona")
                  .countryCode("SE")
                  .firstname("Steve")
                  .lastname("Smith")
                  .customerId("123456789")
                  .dateOfBirth("1990-03-31")
                  .build()
              )
              .addressCountry("SE")
              .dateOfBirth("1990-01-20")
              .email("test@trustly.com")
              .build()
          )
          .build()
      );

      Assertions.assertNotNull(registerAccountPayoutResponse);
    }
  }

  @Test
  void testMerchantSettlement() throws Exception {

    try (TrustlyApiClient client = new TrustlyApiClient(settings)) {

      String uniqueMessageId = UUID.randomUUID().toString();

      var ex = Assertions.assertThrows(TrustlyRequestException.class, () -> client.merchantSettlement(
        MerchantSettlementRequest.Params.Data.builder()
          .messageId(uniqueMessageId)
          .amount("4.99")
          .currency("EUR")
          .build()
      ));

      if (ex.getCause() instanceof TrustlyErrorResponseException) {
        Assertions.assertEquals("ERROR_NO_SUITABLE_BANK_ACCOUNT_FOUND", ((TrustlyErrorResponseException) ex.getCause()).getResponseError().getMessage());
      } else {
        Assertions.fail("Should be a wrapped error response exception");
      }
    }
  }
}
