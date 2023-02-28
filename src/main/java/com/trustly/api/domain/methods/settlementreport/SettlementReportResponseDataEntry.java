package com.trustly.api.domain.methods.settlementreport;

import com.fasterxml.jackson.annotation.JsonAlias;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor
@Jacksonized
public class SettlementReportResponseDataEntry {

  /**
   * The account the money was transferred from(if the amount is positive), or the account the money was transferred to(if the amount is
   * negative).
   */
  String accountName;

  /**
   * The monetary amount of the transaction, rounded down to two decimal places.
   */
  Double amount;

  /**
   * The three-letter currency code of the transaction.
   */
  String currency;

  /**
   * The timestamp of the transaction, including the UTC offset.As the timestamps are always in UTC, the offset is always +00
   *
   * <pre>{@code 2014-03-31 11:50:06.46106+00}</pre>
   */
  Instant datestamp;

  /**
   * MessageID of the order associated with the transaction, if available.
   */
  String messageId;

  /**
   * OrderID of the order associated with the transaction, if available.
   */
  String orderId;

  /**
   * The type of the order associated with the transaction, if available.Text See list of possible orderypes in the table below.
   */
  String orderType;

  /**
   * The sum of all amounts of the respective currency within the report.
   */
  Double total;

  /**
   * The username of the child merchant account.
   */
  String username;

  /**
   * The amount that the end user paid, if the currency is different from the requested deposit currency. For transactions where the payment
   * currency is the same as the requested currency, this field will be empty.
   */
  Double fxPaymentAmount;

  /**
   * The currency that the user paid with, if the currency is different from the requested deposit currency. For transactions where the
   * payment currency is the same as the requested currency, this field will be empty.
   */
  String fxPaymentCurrency;

  /**
   * The 10 digit reference that will show up on the merchant's bank statement for this automatic settlement batch. The same value will be
   * sent on every row in the report.
   */
  String settlementBankWithdrawalId;

  /**
   * Contains the ExternalReference value for Deposit, Charge, and Refund transactions if provided.Otherwise empty.
   */
  @JsonAlias("extraRef")
  String externalReference;
}
