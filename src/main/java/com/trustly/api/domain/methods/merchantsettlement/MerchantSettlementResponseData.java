package com.trustly.api.domain.methods.merchantsettlement;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trustly.api.domain.base.AbstractResponseResultData;
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
public class MerchantSettlementResponseData extends AbstractResponseResultData {

  /**
   * The globally unique reference ID for the settlement transaction.
   */
  @JsonProperty("reference")
  long reference;
}
