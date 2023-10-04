package com.trustly.api;

import com.trustly.api.client.TrustlyApiClient;
import com.trustly.api.client.TrustlyApiClientSettings;
import com.trustly.api.domain.common.RecipientOrSenderInformation;
import com.trustly.api.domain.exceptions.TrustlyErrorResponseException;
import com.trustly.api.domain.exceptions.TrustlyRequestException;
import com.trustly.api.domain.methods.accountpayout.AccountPayoutRequestData;
import com.trustly.api.domain.methods.accountpayout.AccountPayoutRequestDataAttributes;
import com.trustly.api.domain.methods.cancelcharge.CancelChargeRequestData;
import com.trustly.api.domain.methods.charge.ChargeRequestData;
import com.trustly.api.domain.methods.charge.ChargeRequestDataAttributes;
import com.trustly.api.domain.methods.deposit.DepositRequestData;
import com.trustly.api.domain.methods.deposit.DepositRequestDataAttributes;
import com.trustly.api.domain.methods.deposit.DepositResponseData;
import com.trustly.api.domain.methods.merchantsettlement.MerchantSettlementRequestData;
import com.trustly.api.domain.methods.merchantsettlement.MerchantSettlementResponseData;
import com.trustly.api.domain.methods.refund.RefundRequestData;
import com.trustly.api.domain.methods.refund.RefundRequestDataAttributes;
import com.trustly.api.domain.methods.registeraccount.RegisterAccountRequestData;
import com.trustly.api.domain.methods.registeraccount.RegisterAccountRequestDataAttributes;
import com.trustly.api.domain.methods.registeraccount.RegisterAccountResponseData;
import com.trustly.api.domain.methods.registeraccountpayout.RegisterAccountPayoutRequestData;
import com.trustly.api.domain.methods.registeraccountpayout.RegisterAccountPayoutRequestDataAttributes;
import com.trustly.api.domain.methods.registeraccountpayout.RegisterAccountPayoutResponseData;
import com.trustly.api.domain.methods.selectaccount.SelectAccountRequestData;
import com.trustly.api.domain.methods.selectaccount.SelectAccountRequestDataAttributes;
import com.trustly.api.domain.methods.selectaccount.SelectAccountResponseData;
import com.trustly.api.domain.methods.withdraw.WithdrawRequestData;
import com.trustly.api.domain.methods.withdraw.WithdrawRequestDataAttributes;
import com.trustly.api.domain.methods.withdraw.WithdrawResponseData;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * These test will only work if a local environment exists. At the USER_HOME root these files is needed: trustly_client_username.txt - your
 * username trustly_client_password.txt - your password trustly_client_public.pem - your public key trustly_client_public.pem - your private
 * key
 * <p>
 * If those files exist and are correct you are able to make requests to Trustly's test environment
 */
class RequestsTest {

  private final TrustlyApiClientSettings settings = TrustlyApiClientSettings.forTest()
    .withCredentialsFromUserHome()
    .withCertificatesFromUserHome()
    .andTrustlyCertificate();

  @Test
  void testDeposit() throws Exception {

    try (TrustlyApiClient client = new TrustlyApiClient(settings)) {

      DepositRequestData request = DepositRequestData.builder()
        .notificationUrl("https://fake.test.notification.trustly.com")
        .endUserId("john.doe@trustly.com")
        .messageId(UUID.randomUUID().toString())
        .attributes(
          DepositRequestDataAttributes.builder()
            .currency("EUR")
            .amount("100.00")
            .firstname("John")
            .lastname("Doe")
            .email("john.doe@trustly.com")
            .country("SE")
            .locale("sv_SE")
            .shopperStatement("Trustly Test Deposit")
            .successUrl("https://google.com")
            .failURL("https://google.com")
            .mobilePhone("0701234567")
            .build()
        )
        .build();

      DepositResponseData response = client.deposit(request);

      Assertions.assertNotNull(response);
      Assertions.assertNotNull(response.getUrl());
    }
  }

  @Test
  void testAccountPayout() {

    try (TrustlyApiClient client = new TrustlyApiClient(settings)) {

      AccountPayoutRequestData data = AccountPayoutRequestData.builder()
        .accountId("AccountID")
        .accountId("AccountID")
        .endUserId("EndUserId")
        .messageId("MessageId")
        .amount("99.99")
        .currency("SEK")
        .notificationURL("https://notify.me")
        .attributes(
          AccountPayoutRequestDataAttributes.builder()
            .pspMerchant("Merchant Ltd.")
            .shopperStatement("MyBrand.com")
            .externalReference("23423525234")
            .merchantCategoryCode("5499")
            .pspMerchantUrl("www.merchant.com")
            .senderInformation(
              RecipientOrSenderInformation.builder()
                .partytype("PERSON")
                .address("Street 1, 12345 Barcelona")
                .countryCode("SE")
                .firstname("Steve")
                .lastname("Smith")
                .customerID("123456789")
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

      CancelChargeRequestData data = CancelChargeRequestData.builder()
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

      ChargeRequestData data = ChargeRequestData.builder()
        .accountId("accountId")
        .notificationURL("https://notify.me")
        .endUserId("EndUserId")
        .messageId(UUID.randomUUID().toString())
        .amount("99.90")
        .currency("SEK")
        .attributes(
          ChargeRequestDataAttributes.builder()
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

      RefundRequestData data = RefundRequestData.builder()
        .orderId("123456")
        .amount("22")
        .currency("SEK")
        .attributes(
          RefundRequestDataAttributes.builder()
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

      RegisterAccountResponseData registerAccountResponse = client.registerAccount(
        RegisterAccountRequestData.builder()
          .accountNumber("69706212")
          .clearingHouse("SWEDEN")
          .bankNumber("6112")
          .endUserId("123123")
          .firstname("Steve")
          .lastname("Smith")
          .attributes(
            RegisterAccountRequestDataAttributes.builder()
              .addressCountry("SE")
              .dateOfBirth("1990-01-20")
              .email("test@trustly.com")
              .build()
          )
          .build()
      );

      Assertions.assertNotNull(registerAccountResponse);
      Assertions.assertNotNull(registerAccountResponse.getAccountId());
    }
  }

  @Test
  void testSelectAccount() throws Exception {

    try (TrustlyApiClient client = new TrustlyApiClient(settings)) {

      SelectAccountResponseData selectAccountResponse = client.selectAccount(
        SelectAccountRequestData.builder()
          .endUserId("EndUserId")
          .notificationUrl("https://notify.me")
          .messageId(UUID.randomUUID().toString())
          .attributes(
            SelectAccountRequestDataAttributes.builder()
              .country("SE")
              .firstname("Steve")
              .lastname("Smith")
              .locale("en_US")
              .pspMerchant("Merchant Ltd.")
              .shopperStatement("MyBrand.com")
              .merchantCategoryCode("5499")
              .pspMerchantUrl("www.merchant.com")
              .successUrl("https://google.com")
              .failURL("https://google.com")
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

      WithdrawRequestData data = WithdrawRequestData.builder()
        .endUserId(UUID.randomUUID().toString())
        .messageId(UUID.randomUUID().toString())
        .notificationUrl("https://test.trustly.com/trustlynotification")
        .currency("SEK")
        .attributes(
          WithdrawRequestDataAttributes.builder()
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
            .failURL("https://google.com")
            .mobilePhone("0701234567")

            .build()
        )
        .build();

      WithdrawResponseData response = client.withdraw(data);

      Assertions.assertNotNull(response.getUrl());
      Assertions.assertNotEquals(0, response.getOrderId());
    }
  }

  @Test
  void testRegisterAccountPayout() throws Exception {

    try (TrustlyApiClient client = new TrustlyApiClient(settings)) {

      String uniqueMessageId = UUID.randomUUID().toString();

      RegisterAccountPayoutResponseData registerAccountPayoutResponse = client.registerAccountPayout(
        RegisterAccountPayoutRequestData.builder()
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
            RegisterAccountPayoutRequestDataAttributes.builder()
              .pspMerchant("Merchant Ltd.")
              .shopperStatement("MyBrand.com")
              .externalReference("23423525234")
              .merchantCategoryCode("5499")
              .pspMerchant("pspMerchant")
              .pspMerchantUrl("www.merchant.com")
              .merchantCategoryCode("5499")
              .senderInformation(
                RecipientOrSenderInformation.builder()
                  .partytype("PERSON")
                  .address("Street 1, 12345 Barcelona")
                  .countryCode("SE")
                  .firstname("Steve")
                  .lastname("Smith")
                  .customerID("123456789")
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

      MerchantSettlementResponseData merchantSettlementResponse = client.registerMerchantSettlement(
        MerchantSettlementRequestData.builder()
          .messageId(uniqueMessageId)
          .amount("4.99")
          .currency("EUR")
          .build()
      );

      Assertions.assertNotNull(merchantSettlementResponse);
      Assertions.assertNotEquals(0, merchantSettlementResponse.getReference());
    }
  }
}
