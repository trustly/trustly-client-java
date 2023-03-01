package com.trustly.api.domain.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Value
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@Jacksonized
public class AbstractNotificationRequestData<A extends AbstractRequestParamsDataAttributes>
  extends AbstractFromTrustlyRequestData<A> {

  @JsonProperty("messageid")
  String messageId;
  @JsonProperty("notificationid")
  String notificationId;
  @JsonProperty("orderid")
  String orderId;
}
