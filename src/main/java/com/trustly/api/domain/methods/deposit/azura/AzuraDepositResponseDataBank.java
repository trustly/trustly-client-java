package com.trustly.api.domain.methods.deposit.azura;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@AllArgsConstructor
@Jacksonized
public class AzuraDepositResponseDataBank {

  /**
   * Country code for the suggested account. ISO 3166-1 alpha-2
   */
  @JsonProperty("bankcountry")
  String bankCountry;

  /**
   * Bank code for the suggested account. This is 4 uppercase chars.
   */
  @JsonProperty("bankcode")
  String bankCode;

  /**
   * Name of the bank.
   */
  @JsonProperty("bankname")
  String bankName;

  /**
   * The ID of the bank. This ID should be used when initiating the deposit using SELECT_BANK action.
   */
  @JsonProperty("bankid")
  String bankId;

  /**
   * If the bank is out of order. If true it should not be possible to select the bank in the merchant´s checkout.
   */
  @JsonProperty("outoforder")
  Boolean outOfOrder;

  /**
   * Link to bank logo of the bank.
   */
  @JsonProperty("banklogoround")
  String bankLogoRound;
}
