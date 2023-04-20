package com.trustly.api.domain.methods.withdraw;

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
public class WithdrawResponseData extends AbstractResponseResultData {

  /**
   * The globally unique OrderID the withdrawal was assigned in our system.
   */
  @JsonProperty("orderid")
  long orderId;

  /**
   * The URL that should be loaded so that the end-user can complete the withdrawal.
   */
  @JsonProperty("url")
  String url;
}
