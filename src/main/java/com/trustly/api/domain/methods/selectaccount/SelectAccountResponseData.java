package com.trustly.api.domain.methods.selectaccount;

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
public class SelectAccountResponseData extends AbstractResponseResultData {

  /**
   * The globally unique OrderID the account selection order was assigned in our system.
   *
   * <pre>{@code 7653345737}</pre>
   */
  @JsonProperty("orderid")
  String orderId;

  /**
   * The URL that should be loaded so that the end-user can complete the identification process.
   *
   * <pre>{@code https://trustly.com/_/2f6b14fa-446a-4364-92f8-84b738d589ff}</pre>
   */
  @JsonProperty("url")
  String url;
}
