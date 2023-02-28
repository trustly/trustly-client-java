package com.trustly.api.domain.methods.cancelcharge;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.trustly.api.domain.base.AbstractResponseResultData;
import com.trustly.api.domain.base.IWithRejectionResult;
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
public class CancelChargeResponseData extends AbstractResponseResultData implements IWithRejectionResult {

  /**
   * {@code "1"} if the Charge could be canceled, and {@code "0"} otherwise.
   */
  @JsonProperty("result")
  @JsonSerialize(using = StringBooleanSerializer.class)
  @JsonDeserialize(using = StringBooleanDeserializer.class)
  boolean result;

  /**
   * If the CancelCharge was NOT accepted and result 0 is sent, a textual code describing the rejection reason will be sent here.
   * <p>
   * For a successful CancelCharge, this will be {@code null}.
   */
  @JsonProperty("rejected")
  @JsonInclude(Include.ALWAYS)
  String rejected;
}
