package com.trustly.api.domain.methods.balance;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Value
@SuperBuilder
@AllArgsConstructor
@Jacksonized
public class BalanceResponseDataEntry {

  /**
   * The currency
   */
  @JsonProperty("currency")
  String currency;

  /**
   * The balance with 2 decimals
   */
  @JsonProperty("balance")
  String balance;
}
