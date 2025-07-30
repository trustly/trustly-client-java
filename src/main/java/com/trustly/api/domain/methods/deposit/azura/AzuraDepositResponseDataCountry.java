package com.trustly.api.domain.methods.deposit.azura;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@AllArgsConstructor
@Jacksonized
public class AzuraDepositResponseDataCountry {

  /**
   * Country code ISO 3166-1 alpha-2
   */
  @JsonProperty("countrycode")
  String countryCode;

  /**
   * An object with mappings from two-letter ISO 639 language code to a link to Trustly’s terms and conditions.
   */
  @JsonProperty("termsandconditions")
  Map<String, String> termsAndConditions;

  /**
   * An object with mappings from two-letter ISO 639 language code to a link to Trustly’s privacy policy.
   */
  @JsonProperty("privacypolicy")
  Map<String, String> privacyPolicy;

  /**
   * Available banks in this country.
   */
  @JsonProperty("banks")
  List<AzuraDepositResponseDataBank> banks;
}
