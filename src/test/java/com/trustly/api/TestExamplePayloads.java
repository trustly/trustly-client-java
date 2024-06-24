package com.trustly.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trustly.api.client.TrustlyApiClient;
import com.trustly.api.client.TrustlyApiClientSettings;
import com.trustly.api.domain.exceptions.TrustlyErrorResponseException;
import com.trustly.api.domain.exceptions.TrustlyRequestException;
import com.trustly.api.request.HttpRequester;
import com.trustly.api.util.TrustlyStreamUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static com.trustly.api.domain.Models.*;

@Execution(ExecutionMode.CONCURRENT)
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
        WithdrawRequest.Params.Data.class, WithdrawResponse.class,
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
      AccountLedgerRequest.Params.Data.class, AccountLedgerResponse.class,
      "AccountLedger",
      "/requests/accountledger.json", "/responses/accountledger.json"
    );

    // Same request, but this time we will respond with an Invalid Parameters error.
    Assertions.assertThrows(TrustlyRequestException.class, () -> this.doRequestResponse(
      AccountLedgerRequest.Params.Data.class, AccountLedgerResponse.class,
      "AccountLedger",
      "/requests/accountledger.json", "/responses/error_invalid_parameters.json"
    ));

    this.doRequestResponse(
      AccountPayoutRequest.Params.Data.class, AccountPayoutResponse.class,
      "AccountPayout",
      "/requests/accountpayout_1.json", "/responses/accountpayout.json"
    );
    this.doRequestResponse(
      AccountPayoutRequest.Params.Data.class, AccountPayoutResponse.class,
      "AccountPayout",
      "/requests/accountpayout_2.json", "/responses/accountpayout.json"
    );
    this.doRequestResponse(
      AccountPayoutRequest.Params.Data.class, AccountPayoutResponse.class,
      "AccountPayout",
      "/requests/accountpayout_3.json", "/responses/accountpayout.json"
    );

    this.doRequestResponse(
      ApproveWithdrawalRequest.Params.Data.class, ApproveWithdrawalResponse.class,
      "ApproveWithdrawal",
      "/requests/approvewithdrawal.json", "/responses/approvewithdrawal.json"
    );

    this.doRequestResponse(
      BalanceRequest.Params.Data.class, BalanceResponse.class,
      "Balance",
      "/requests/balance.json", "/responses/balance.json"
    );

    Assertions.assertThrows(TrustlyRequestException.class, () -> this.doRequestResponse(
      CancelChargeRequest.Params.Data.class, CancelChargeResponse.class,
      "CancelCharge",
      "/requests/cancelcharge.json", "/responses/cancelcharge_fail.json"
    ));
    this.doRequestResponse(
      CancelChargeRequest.Params.Data.class, CancelChargeResponse.class,
      "CancelCharge",
      "/requests/cancelcharge.json", "/responses/cancelcharge_ok.json"
    );

    this.doRequestResponse(
      ChargeRequest.Params.Data.class, ChargeResponse.class,
      "Charge",
      "/requests/charge_1.json", "/responses/charge.json"
    );
    this.doRequestResponse(
      ChargeRequest.Params.Data.class, ChargeResponse.class,
      "Charge",
      "/requests/charge_2.json", "/responses/charge.json"
    );

    this.doRequestResponse(
      DenyWithdrawalRequest.Params.Data.class, DenyWithdrawalResponse.class,
      "DenyWithdrawal",
      "/requests/denywithdrawal.json", "/responses/denywithdrawal.json"
    );

    this.doRequestResponse(
      DepositRequest.Params.Data.class, DepositResponse.class,
      "Deposit",
      "/requests/deposit_1.json", "/responses/deposit.json"
    );
    this.doRequestResponse(
      DepositRequest.Params.Data.class, DepositResponse.class,
      "Deposit",
      "/requests/deposit_2.json", "/responses/deposit.json"
    );
    this.doRequestResponse(
      DepositRequest.Params.Data.class, DepositResponse.class,
      "Deposit",
      "/requests/deposit_3.json", "/responses/deposit.json"
    );
    this.doRequestResponse(
      DepositRequest.Params.Data.class, DepositResponse.class,
      "Deposit",
      "/requests/deposit_ideal.json", "/responses/deposit.json"
    );

    this.doRequestResponse(
      GetWithdrawalsRequest.Params.Data.class, GetWithdrawalsResponse.class,
      "GetWithdrawals",
      "/requests/getwithdrawals.json", "/responses/getwithdrawals.json"
    );

    this.doRequestResponse(
      RefundRequest.Params.Data.class, RefundResponse.class,
      "Refund",
      "/requests/refund.json", "/responses/refund.json"
    );

    this.doRequestResponse(
      RegisterAccountRequest.Params.Data.class, RegisterAccountResponse.class,
      "RegisterAccount",
      "/requests/registeraccount.json", "/responses/registeraccount.json"
    );
    this.doRequestResponse(
      RegisterAccountRequest.Params.Data.class, RegisterAccountResponse.class,
      "RegisterAccount",
      "/requests/registeraccount_2.json", "/responses/registeraccount.json"
    );

    this.doRequestResponse(
      SelectAccountRequest.Params.Data.class, SelectAccountResponse.class,
      "SelectAccount",
      "/requests/selectaccount.json", "/responses/selectaccount.json"
    );

    this.doRequestResponse(
      SettlementReportRequest.Params.Data.class, SettlementReportResponse.class,
      "ViewAutomaticSettlementDetailsCSV",
      "/requests/settlementreport.json", "/responses/settlementreport.json"
    );

    this.doRequestResponse(
      WithdrawRequest.Params.Data.class, WithdrawResponse.class,
      "Withdraw",
      "/requests/withdraw.json", "/responses/withdraw.json"
    );
  }

  private <
    TResData,
    TResResult extends ResponseResult<TResData>,
    TRes extends JsonRpcResponse<TResResult>
    >
  void doRequestResponse(
    Class<? extends AbstractRequestData> requestDataClass,
    Class<TRes> responseDataClass,
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

          Assertions.assertNotNull(client.sendRequest(
            TrustlyApiClient.DEFAULT_OBJECT_MAPPER.readValue(requestStream, requestDataClass),
            responseDataClass,
            method,
            requestUuid
          ));
        }
      }
    }
  }
}
