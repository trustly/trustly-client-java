package com.trustly.api.domain.methods.charge;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.trustly.api.domain.base.AbstractToTrustlyRequestData;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.validator.constraints.URL;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Jacksonized
@JsonInclude(Include.NON_NULL)
public class ChargeRequestData extends AbstractToTrustlyRequestData<ChargeRequestDataAttributes> {

  /**
   * The AccountID received from an account notification which shall be charged.
   */
  @JsonProperty(value = "AccountID", required = true)
  @NotBlank
  String accountId;

  /**
   * The URL to which notifications for this payment should be sent to.This URL should be hard to guess and not contain a? ("question
   * mark").
   */
  @JsonProperty(value = "NotificationURL", required = true)
  @NotBlank
  @URL
  String notificationURL;

  /**
   * ID, username, hash or anything uniquely identifying the end-user being charged.
   * <p>
   * Preferably the same ID/username as used in the merchant's own backoffice in order to simplify for the merchant's support department.
   */
  @JsonProperty(value = "EndUserID", required = true)
  @NotBlank
  String endUserId;

  /**
   * Your unique ID for the charge.
   */
  @JsonProperty(value = "MessageID", required = true)
  @NotBlank
  String messageId;

  /**
   * The amount to charge with exactly two decimals.Only digits. Use dot (.) as decimal separator.
   */
  @JsonProperty(value = "Amount", required = true)
  @NotBlank
  String amount;

  /**
   * The currency of the amount to charge.
   */
  @JsonProperty(value = "Currency", required = true)
  @NotBlank
  String currency;
}

