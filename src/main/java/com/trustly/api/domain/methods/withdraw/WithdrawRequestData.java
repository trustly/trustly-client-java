package com.trustly.api.domain.methods.withdraw;

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
public class WithdrawRequestData extends AbstractToTrustlyRequestData<WithdrawRequestDataAttributes> {

  /**
   * The URL to which notifications for this payment should be sent to. This URL should be hard to guess and not contain a ? ("question
   * mark").
   */
  @JsonProperty(value = "NotificationURL")
  @NotBlank
  @URL
  String notificationUrl;

  /**
   * ID, username, hash or anything uniquely identifying the end-user requesting the withdrawal, Preferably the same ID/username as used in
   * the merchant's own backoffice in order to simplify for the merchant's support department.
   */
  @JsonProperty(value = "EndUserID")
  @NotBlank
  String endUserId;

  /**
   * Your unique ID for the withdrawal.
   */
  @JsonProperty(value = "MessageID")
  @NotBlank
  String messageId;

  /**
   * The currency of the end-user's account in the merchant's system.
   */
  @JsonProperty(value = "Currency")
  @NotBlank
  String currency;
}

