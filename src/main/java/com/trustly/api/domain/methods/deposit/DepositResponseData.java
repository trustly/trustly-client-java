package com.trustly.api.domain.methods.deposit;

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
public class DepositResponseData extends AbstractResponseResultData {

  /**
   * The OrderID specified when calling the method.
   */
  @JsonProperty("orderid")
  String orderId;

  /**
   * The URL that should be loaded so that the end-user can complete the deposit.
   */
  @JsonProperty("url")
  String url;
}
