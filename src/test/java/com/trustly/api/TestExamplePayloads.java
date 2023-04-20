package com.trustly.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trustly.api.client.TrustlyApiClient;
import com.trustly.api.client.TrustlyApiClientSettings;
import com.trustly.api.domain.base.IResponseResultData;
import com.trustly.api.domain.base.IToTrustlyRequestParams;
import com.trustly.api.domain.exceptions.TrustlyErrorResponseException;
import com.trustly.api.domain.exceptions.TrustlyRequestException;
import com.trustly.api.domain.methods.accountledger.AccountLedgerRequestData;
import com.trustly.api.domain.methods.accountledger.AccountLedgerResponseData;
import com.trustly.api.domain.methods.accountpayout.AccountPayoutRequestData;
import com.trustly.api.domain.methods.accountpayout.AccountPayoutResponseData;
import com.trustly.api.domain.methods.approvewithdrawal.ApproveWithdrawalRequestData;
import com.trustly.api.domain.methods.approvewithdrawal.ApproveWithdrawalResponseData;
import com.trustly.api.domain.methods.balance.BalanceRequestData;
import com.trustly.api.domain.methods.balance.BalanceResponseData;
import com.trustly.api.domain.methods.cancelcharge.CancelChargeRequestData;
import com.trustly.api.domain.methods.cancelcharge.CancelChargeResponseData;
import com.trustly.api.domain.methods.charge.ChargeRequestData;
import com.trustly.api.domain.methods.charge.ChargeResponseData;
import com.trustly.api.domain.methods.denywithdrawal.DenyWithdrawalRequestData;
import com.trustly.api.domain.methods.denywithdrawal.DenyWithdrawalResponseData;
import com.trustly.api.domain.methods.deposit.DepositRequestData;
import com.trustly.api.domain.methods.deposit.DepositResponseData;
import com.trustly.api.domain.methods.getwithdrawals.GetWithdrawalsRequestData;
import com.trustly.api.domain.methods.getwithdrawals.GetWithdrawalsResponseData;
import com.trustly.api.domain.methods.refund.RefundRequestData;
import com.trustly.api.domain.methods.refund.RefundResponseData;
import com.trustly.api.domain.methods.registeraccount.RegisterAccountRequestData;
import com.trustly.api.domain.methods.registeraccount.RegisterAccountResponseData;
import com.trustly.api.domain.methods.selectaccount.SelectAccountRequestData;
import com.trustly.api.domain.methods.selectaccount.SelectAccountResponseData;
import com.trustly.api.domain.methods.settlementreport.SettlementReportRequestData;
import com.trustly.api.domain.methods.settlementreport.SettlementReportResponseData;
import com.trustly.api.domain.methods.withdraw.WithdrawRequestData;
import com.trustly.api.domain.methods.withdraw.WithdrawResponseData;
import com.trustly.api.request.HttpRequester;
import com.trustly.api.util.TrustlyStreamUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestExamplePayloads {

  private final TrustlyApiClientSettings settings = TrustlyApiClientSettings.forTest()
    .withCredentials("merchant_username", "merchant_password")
    .withCertificatesFromStreams(
      // We use the same certificates as those found at https://test.trustly.com/signaturetester/
      NotificationsTest.class.getResourceAsStream("/keys/merchant_public_key.pem"),
      NotificationsTest.class.getResourceAsStream("/keys/merchant_private_key.pem")
    )
    .andTrustlyCertificate();

  @Test
  void testInvalidParametersResponse() throws Exception {

    try {

      this.doRequestResponse(
        WithdrawRequestData.class, WithdrawResponseData.class,
        "Withdraw",
        "/requests/withdraw.json", "/responses/error_invalid_parameters.json"
      );

      Assertions.fail("Should have failed");

    } catch (TrustlyRequestException ex) {

      Assertions.assertEquals(TrustlyErrorResponseException.class, ex.getCause().getClass());
      TrustlyErrorResponseException responseException = (TrustlyErrorResponseException) ex.getCause();
      Assertions.assertEquals(623, responseException.getResponseError().getCode());
    }
  }

  @Test
  void testRequestAndResponsePayload() throws Exception {

    this.doRequestResponse(
      AccountLedgerRequestData.class, AccountLedgerResponseData.class,
      "AccountLedger",
      "/requests/accountledger.json", "/responses/accountledger.json"
    );

    // Same request, but this time we will respond with an Invalid Parameters error.
    Assertions.assertThrows(TrustlyRequestException.class, () -> this.doRequestResponse(
      AccountLedgerRequestData.class, AccountLedgerResponseData.class,
      "AccountLedger",
      "/requests/accountledger.json", "/responses/error_invalid_parameters.json"
    ));

    this.doRequestResponse(
      AccountPayoutRequestData.class, AccountPayoutResponseData.class,
      "AccountPayout",
      "/requests/accountpayout_1.json", "/responses/accountpayout.json"
    );
    this.doRequestResponse(
      AccountPayoutRequestData.class, AccountPayoutResponseData.class,
      "AccountPayout",
      "/requests/accountpayout_2.json", "/responses/accountpayout.json"
    );
    this.doRequestResponse(
      AccountPayoutRequestData.class, AccountPayoutResponseData.class,
      "AccountPayout",
      "/requests/accountpayout_3.json", "/responses/accountpayout.json"
    );

    this.doRequestResponse(
      ApproveWithdrawalRequestData.class, ApproveWithdrawalResponseData.class,
      "ApproveWithdrawal",
      "/requests/approvewithdrawal.json", "/responses/approvewithdrawal.json"
    );

    this.doRequestResponse(
      BalanceRequestData.class, BalanceResponseData.class,
      "Balance",
      "/requests/balance.json", "/responses/balance.json"
    );

    Assertions.assertThrows(TrustlyRequestException.class, () -> this.doRequestResponse(
      CancelChargeRequestData.class, CancelChargeResponseData.class,
      "CancelCharge",
      "/requests/cancelcharge.json", "/responses/cancelcharge_fail.json"
    ));
    this.doRequestResponse(
      CancelChargeRequestData.class, CancelChargeResponseData.class,
      "CancelCharge",
      "/requests/cancelcharge.json", "/responses/cancelcharge_ok.json"
    );

    this.doRequestResponse(
      ChargeRequestData.class, ChargeResponseData.class,
      "Charge",
      "/requests/charge_1.json", "/responses/charge.json"
    );
    this.doRequestResponse(
      ChargeRequestData.class, ChargeResponseData.class,
      "Charge",
      "/requests/charge_2.json", "/responses/charge.json"
    );

    this.doRequestResponse(
      DenyWithdrawalRequestData.class, DenyWithdrawalResponseData.class,
      "DenyWithdrawal",
      "/requests/denywithdrawal.json", "/responses/denywithdrawal.json"
    );

    this.doRequestResponse(
      DepositRequestData.class, DepositResponseData.class,
      "Deposit",
      "/requests/deposit_1.json", "/responses/deposit.json"
    );
    this.doRequestResponse(
      DepositRequestData.class, DepositResponseData.class,
      "Deposit",
      "/requests/deposit_2.json", "/responses/deposit.json"
    );
    this.doRequestResponse(
      DepositRequestData.class, DepositResponseData.class,
      "Deposit",
      "/requests/deposit_3.json", "/responses/deposit.json"
    );
    this.doRequestResponse(
      DepositRequestData.class, DepositResponseData.class,
      "Deposit",
      "/requests/deposit_ideal.json", "/responses/deposit.json"
    );

    this.doRequestResponse(
      GetWithdrawalsRequestData.class, GetWithdrawalsResponseData.class,
      "GetWithdrawals",
      "/requests/getwithdrawals.json", "/responses/getwithdrawals.json"
    );

    this.doRequestResponse(
      RefundRequestData.class, RefundResponseData.class,
      "Refund",
      "/requests/refund.json", "/responses/refund.json"
    );

    this.doRequestResponse(
      RegisterAccountRequestData.class, RegisterAccountResponseData.class,
      "RegisterAccount",
      "/requests/registeraccount.json", "/responses/registeraccount.json"
    );
    this.doRequestResponse(
      RegisterAccountRequestData.class, RegisterAccountResponseData.class,
      "RegisterAccount",
      "/requests/registeraccount_2.json", "/responses/registeraccount.json"
    );

    this.doRequestResponse(
      SelectAccountRequestData.class, SelectAccountResponseData.class,
      "SelectAccount",
      "/requests/selectaccount.json", "/responses/selectaccount.json"
    );

    this.doRequestResponse(
      SettlementReportRequestData.class, SettlementReportResponseData.class,
      "ViewAutomaticSettlementDetailsCSV",
      "/requests/settlementreport.json", "/responses/settlementreport.json"
    );

    this.doRequestResponse(
      WithdrawRequestData.class, WithdrawResponseData.class,
      "Withdraw",
      "/requests/withdraw.json", "/responses/withdraw.json"
    );
  }

  private <T extends IToTrustlyRequestParams, R extends IResponseResultData> void doRequestResponse(
    Class<T> requestClass,
    Class<R> responseClass,
    String method,
    String requestClassPath,
    String responseClassPath
  ) throws Exception {

    try (InputStream responseStream = TestExamplePayloads.class.getResourceAsStream(responseClassPath)) {

      assert responseStream != null;

      String responseString = TrustlyStreamUtils.readerToString(new InputStreamReader(responseStream, StandardCharsets.UTF_8));

      String requestUuid = UUID.randomUUID().toString();
      HttpRequester fakeHttpRequester = (settings, request) -> responseString.replace("11111111-1111-1111-1111-111111111111", requestUuid);

      try (TrustlyApiClient client = new TrustlyApiClient(settings, new NoOpJsonRpcSigner(), fakeHttpRequester)) {
        try (InputStream requestStream = TestExamplePayloads.class.getResourceAsStream(requestClassPath)) {

          T requestData = new ObjectMapper().readValue(requestStream, requestClass);
          client.sendRequest(requestData, responseClass, method, requestUuid);
        }
      }
    }
  }
}
