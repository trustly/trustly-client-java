package com.trustly.api.domain.methods.approvewithdrawal;

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
public class ApproveWithdrawalResponseData extends AbstractResponseResultData {

  /**
   * The OrderID specified when calling the method.
   */
  @JsonProperty(value = "orderid", required = true)
  long orderId;

  /**
   * 1 if the withdrawal could be approved and 0 otherwise.
   */
  @JsonProperty("result")
  @JsonSerialize(using = StringBooleanSerializer.class)
  @JsonDeserialize(using = StringBooleanDeserializer.class)
  boolean result;
}
