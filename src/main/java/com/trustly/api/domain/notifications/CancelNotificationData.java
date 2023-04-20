package com.trustly.api.domain.notifications;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trustly.api.domain.base.AbstractFromTrustlyRequestData;
import com.trustly.api.domain.base.EmptyRequestDataAttributes;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Value
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Jacksonized
public class CancelNotificationData extends AbstractFromTrustlyRequestData<EmptyRequestDataAttributes> {

  @JsonProperty("messageid")
  String messageId;

  @JsonProperty("orderid")
  String orderId;

  @JsonProperty("notificationid")
  String notificationId;

  @JsonProperty("enduserid")
  String endUserId;

  @JsonProperty("timestamp")
  String timestamp;
}
