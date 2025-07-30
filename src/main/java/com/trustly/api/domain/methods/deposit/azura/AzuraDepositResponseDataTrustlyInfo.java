package com.trustly.api.domain.methods.deposit.azura;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@AllArgsConstructor
@Jacksonized
public class AzuraDepositResponseDataTrustlyInfo {

  /**
   * Trustly assets.
   */
  @JsonProperty("trustlylogo1")
  String trustlyLogo1;

  /**
   * Trustly assets.
   */
  @JsonProperty("trustlylogo2")
  String trustlyLogo2;

  /**
   * Trustly assets.
   */
  @JsonProperty("trustlylogo3")
  String trustlyLogo3;

  /**
   * Trustly assets.
   */
  @JsonProperty("trustlylogo4")
  String trustlyLogo4;
}
