package com.trustly.api.domain.notifications;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.trustly.api.domain.base.AbstractFromTrustlyRequestData;
import com.trustly.api.domain.common.StringBooleanDeserializer;
import com.trustly.api.domain.common.StringBooleanSerializer;
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
public class AccountNotificationData extends AbstractFromTrustlyRequestData<AccountNotificationDataAttributes> {

  @JsonProperty("messageid")
  String messageId;

  @JsonProperty("orderid")
  String orderId;

  @JsonProperty("notificationid")
  String notificationId;

  @JsonProperty("accountid")
  String accountId;

  @JsonSerialize(using = StringBooleanSerializer.class)
  @JsonDeserialize(using = StringBooleanDeserializer.class)
  boolean verified;
}
