package com.trustly.api;

import com.trustly.api.client.TrustlyApiClient;
import com.trustly.api.client.TrustlyApiClientSettings;
import com.trustly.api.domain.common.RecipientOrSenderInformation;
import com.trustly.api.domain.exceptions.TrustlyErrorResponseException;
import com.trustly.api.domain.exceptions.TrustlyRequestException;
import com.trustly.api.domain.methods.accountpayout.AccountPayoutRequestData;
import com.trustly.api.domain.methods.accountpayout.AccountPayoutRequestDataAttributes;
import com.trustly.api.domain.methods.accountpayout.AccountPayoutResponseData;
import com.trustly.api.domain.methods.cancelcharge.CancelChargeRequestData;
import com.trustly.api.domain.methods.cancelcharge.CancelChargeResponseData;
import com.trustly.api.domain.methods.charge.ChargeRequestData;
import com.trustly.api.domain.methods.charge.ChargeRequestDataAttributes;
import com.trustly.api.domain.methods.charge.ChargeResponseData;
import com.trustly.api.domain.methods.deposit.DepositRequestData;
import com.trustly.api.domain.methods.deposit.DepositRequestDataAttributes;
import com.trustly.api.domain.methods.deposit.DepositResponseData;
import com.trustly.api.domain.methods.refund.RefundRequestData;
import com.trustly.api.domain.methods.refund.RefundRequestDataAttributes;
import com.trustly.api.domain.methods.refund.RefundResponseData;
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
 * If those files exist and is correct you are able to make requests to Trustlys test environment
 */
class TestRequestsNew {

  private final TrustlyApiClientSettings settings = TrustlyApiClientSettings.forTest()
    .withCredentialsFromUserHome()
    .withCertificatesFromUserHome()
    .andTrustlyCertificate();

  @Test
  void testDeposit() throws Exception {

    try (TrustlyApiClient client = new TrustlyApiClient(settings)) {

      DepositRequestData request = DepositRequestData.builder()
        .notificationUrl("https://fake.test.notification.trustly.com")
        .endUserId("pontus.eliason@trustly.com")
        .messageId(UUID.randomUUID().toString())
        .attributes(
          DepositRequestDataAttributes.builder()
            .currency("EUR")
            .amount("100.00")
            .firstname("John")
            .lastname("Doe")
            .email("pontus.eliason@trustly.com")
            .country("SE")
            .locale("sv_SE")
            .shopperStatement("Trustly Test Deposit")
            .build()
        )
        .build();

      DepositResponseData response = client.deposit(request);

      Assertions.assertNotNull(response);
      Assertions.assertNotNull(response.getOrderId());
    }
  }

//  private static final Supplier<UUID> UUID_SUPPLIER = UUID::randomUUID;
//
//  private SignedAPI api;

//  @BeforeEach
//  void setup() {
//    api = new SignedAPI();
//
//    // Init for test environment
//    api.init(
//      TrustlyApiSettings.getClientPrivateKey(),
//      "",
//      TrustlyApiSettings.getClientUsername(),
//      TrustlyApiSettings.getClientPassword(),
//      true
//    );
//  }

  @Test
  void testAccountPayout() throws Exception {

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

//      AccountPayoutResponseData accountPayoutResponse = client.accountPayout(
//
//      );

      try {

        client.accountPayout(data);
        Assertions.fail("Should fail");

      } catch (TrustlyRequestException ex) {

        Assertions.assertEquals(TrustlyErrorResponseException.class, ex.getCause().getClass());
        Assertions.assertEquals(620, ((TrustlyErrorResponseException) ex.getCause()).getResponseError().getCode());
      }

//      Assertions.assertNotNull(accountPayoutResponse);
//      Assertions.assertNotNull(accountPayoutResponse.getError());
    }
  }

  @Test
  void testCancelCharge() throws Exception {

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
  void testRefund() throws Exception {

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

//      RefundResponseData refundResponse = client.refund(
//
//      );

      try {

        client.refund(data);
        Assertions.fail("Should fail");

      } catch (TrustlyRequestException ex) {

        Assertions.assertEquals(TrustlyErrorResponseException.class, ex.getCause().getClass());

        // ERROR_INVALID_ORDER_ID
        Assertions.assertEquals(655, ((TrustlyErrorResponseException) ex.getCause()).getResponseError().getCode());
      }

//      Request refundRequest = new Refund.Build(
//        "123456",
//        "22",
//        Currency.SEK
//      )
//        .externalReference("123")
//        .getRequest();

      // Order won't exist, assert error
//      Response refundResponse = api.sendRequest(refundRequest);
//      Assertions.assertNotNull(refundResponse);
//      Assertions.assertNotNull(refundResponse.getError());
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

//      Request registerAccountPayoutRequest = new RegisterAccountPayout.Build(
//        "123123",
//        "SWEDEN",
//        "6112",
//        "69706212",
//        "Steve",
//        "Smith",
//        "https://test.trustly.com/trustlynotification",
//        uniqueMessageId,
//        "99.99",
//        "SEK"
//      )        // Attributes
//
//        .getRequest();

//      Response registerAccountPayoutResponse = api.sendRequest(registerAccountPayoutRequest);
      Assertions.assertNotNull(registerAccountPayoutResponse);
//      Assertions.assertNull(registerAccountPayoutResponse.getError());
    }
  }
}
