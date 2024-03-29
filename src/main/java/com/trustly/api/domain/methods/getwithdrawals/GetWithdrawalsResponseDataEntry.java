package com.trustly.api.domain.methods.getwithdrawals;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Value
@SuperBuilder
@AllArgsConstructor
@Jacksonized
public class GetWithdrawalsResponseDataEntry {

  /**
   * Reference code for the withdrawal generated by Trustly.
   */
  @JsonProperty("reference")
  String reference;

  /**
   * Date and time when the withdrawal was last updated.
   */
  @JsonProperty("modificationdate")
  String modificationDate;

  /**
   * OrderID of the withdrawal
   */
  @JsonProperty("orderid")
  String orderId;

  /**
   * Date and time when the withdrawal request was received.
   */
  @JsonProperty("datestamp")
  String datestamp;

  /**
   * The current state of the withdrawal.
   * <p>
   * Examples are:
   * <ul>
   *   <li>EXECUTING</li>
   *   <li>EXECUTED</li>
   *   <li>PENDING</li>
   *   <li>QUEUED</li>
   *   <li>PREPARING</li>
   *   <li>PREPARED</li>
   *   <li>BOUNCED</li>
   *   <li>ERROR</li>
   *   <li>FAILED</li>
   *   <li>RETURNED</li>
   *   <li>CONFIRMED</li>
   * </ul>
   *
   * <p>
   * It's important that no logic is built on the merchant side based on any specific transferState.
   * New states can be added, and existing states can be changed or removed without notice.
   */
  @JsonProperty("transferstate")
  String transferState;

  /**
   * The amount of the withdrawal.
   */
  @JsonProperty("amount")
  String amount;

  /**
   * The accountid of the receiving account.
   */
  @JsonProperty("accountid")
  String accountid;

  /**
   * The currency of the withdrawal.
   */
  @JsonProperty("currency")
  String currency;

  /**
   * The estimated date and time for when the funds will be available on the receiving bank account.If this information is not available it
   * will be null.
   */
  @JsonProperty("eta")
  String eta;
}
