package com.trustly.api.domain.methods.accountledger;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@AllArgsConstructor
@Jacksonized
public class AccountLedgerResponseDataEntry {

  /**
   * Your userid in our system.
   */
  @JsonProperty("userid")
  String userId;

  /**
   * The datestamp for when this ledger row affected your balance in our system.
   */
  @JsonProperty("datestamp")
  String datestamp;

  /**
   * The globally unique OrderID that resulted in this ledger record.
   */
  @JsonProperty("orderid")
  String orderId;

  /**
   * The name of the bookkeeping account this ledger record belongs to.
   */
  @JsonProperty("accountname")
  String accountName;

  /**
   * Your unique MessageID that you used to create the order that resulted in this ledger record.
   */
  @JsonProperty("messageid")
  String messageId;

  /**
   * A human friendly description of this ledger record.
   */
  @JsonProperty("transactiontype")
  String transactionType;

  /**
   * The currency of the amount in this ledger record.
   */
  @JsonProperty("currency")
  String currency;

  /**
   * The amount your balance in our system was affected with due to this ledger record. May contain a lot of decimals.
   */
  @JsonProperty("amount")
  String amount;

  /**
   * An ID meaning different things for different payment methods, you probably don't need this data.
   */
  @JsonProperty("gluepayid")
  String gluepayId;
}
