package com.trustly.api.domain.notifications;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trustly.api.domain.base.AbstractFromTrustlyRequestData;
import com.trustly.api.domain.base.EmptyRequestDataAttributes;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.experimental.SuperBuilder;

@Value
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@NonFinal
@RequiredArgsConstructor
public class AbstractCreditDebitPendingPayoutNotificationData extends
  AbstractFromTrustlyRequestData<EmptyRequestDataAttributes> {

  @JsonProperty(value = "amount")
  String amount;

  @JsonProperty(value = "currency")
  String currency;

  @JsonProperty(value = "messageid")
  String messageId;

  @JsonProperty(value = "orderid")
  String orderId;

  @JsonProperty(value = "enduserid")
  String endUserId;

  @JsonProperty(value = "notificationid")
  String notificationId;

  @JsonProperty(value = "timestamp")
  String timestamp;
}
