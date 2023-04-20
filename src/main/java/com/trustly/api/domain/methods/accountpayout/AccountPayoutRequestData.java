package com.trustly.api.domain.methods.accountpayout;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.trustly.api.domain.base.AbstractToTrustlyRequestData;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Jacksonized
@JsonInclude(Include.NON_NULL)
public class AccountPayoutRequestData extends AbstractToTrustlyRequestData<AccountPayoutRequestDataAttributes> {

  /**
   * The URL to which notifications for this payment should be sent to. This URL should be hard to guess and not contain a ? ("question
   * mark").
   */
  @Pattern(regexp = "[^?]+")
  @JsonProperty(value = "NotificationURL", required = true)
  @NotBlank
  String notificationURL;

  /**
   * The AccountID received from an account notification to which the money shall be sent.
   */
  @JsonProperty(value = "AccountID", required = true)
  @NotBlank
  String accountId;

  /**
   * ID, username, hash or anything uniquely identifying the end-user requesting the withdrawal, Preferably the same ID/username as used in
   * the merchant's own backoffice in order to simplify for the merchant's support department.
   */
  @JsonProperty(value = "EndUserID", required = true)
  @NotBlank
  String endUserId;

  /**
   * Your unique ID for the payout. If the MessageID is a previously initiated P2P order then the payout will be attached to that P2P order
   * and the amount must be equal to or lower than the previously deposited amount.
   */
  @JsonProperty(value = "MessageID", required = true)
  @NotBlank
  String messageId;

  /**
   * The amount to send with exactly two decimals. Only digits. Use dot (.) as decimal separator. If the end-user holds a balance in the
   * merchant's system then the amount must have been deducted from that balance before calling this method.
   */
  @JsonProperty(value = "Amount", required = true)
  @NotBlank
  String amount;

  /**
   * The currency of the end-user's account in the merchant's system.
   */
  @JsonProperty(value = "Currency", required = true)
  @NotBlank
  String currency;
}

