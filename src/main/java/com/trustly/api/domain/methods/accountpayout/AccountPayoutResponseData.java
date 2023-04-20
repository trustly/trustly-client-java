package com.trustly.api.domain.methods.accountpayout;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.trustly.api.domain.base.AbstractResponseResultData;
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
public class AccountPayoutResponseData extends AbstractResponseResultData {

  /**
   * The globally unique OrderID the account payout order was assigned in our system.
   */
  @JsonProperty("orderid")
  long orderId;

  /**
   * "1" if the payout could be accepted and "0" otherwise.
   */
  @JsonProperty("result")
  @JsonSerialize(using = StringBooleanSerializer.class)
  @JsonDeserialize(using = StringBooleanDeserializer.class)
  boolean result;
}
