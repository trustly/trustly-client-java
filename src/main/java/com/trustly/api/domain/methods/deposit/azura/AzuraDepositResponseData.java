package com.trustly.api.domain.methods.deposit.azura;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trustly.api.domain.base.AbstractResponseResultData;
import java.util.List;
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
public class AzuraDepositResponseData extends AbstractResponseResultData  {

  /**
   * ID that you will send in the next call.
   */
  @JsonProperty("azurasessionid")
  String azuraSessionId;

  /**
   * Array of objects containing information about the found accounts, sorted with the best match listed first.
   */
  @JsonProperty("accounts")
  List<AzuraDepositResponseDataAccount> accounts;

  /**
   * Trustly assets.
   */
  @JsonProperty("trustlyinfo")
  AzuraDepositResponseDataTrustlyInfo trustlyInfo;

  /**
   * A list of countries and their banks, which can be utilized to display bank logos in the checkout.
   */
  @JsonProperty("countries")
  List<AzuraDepositResponseDataCountry> countries;
}
