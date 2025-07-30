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
public class AzuraDepositResponseDataAccount {

  /**
   * Identifies the suggested account.
   * You will send this account id in the next call if the user chose to continue with this account.
   */
  @JsonProperty("accountid")
  String accountId;

  /**
   * A masked representation of the account number.
   */
  @JsonProperty("accountnumbermasked")
  String accountNumberMasked;

  /**
   * The last digits of the account number.
   */
  @JsonProperty("accountnumberlastdigits")
  String accountNumberLastDigits;

  /**
   * An object with bank data.
   */
  @JsonProperty("bank")
  AzuraDepositResponseDataBank bank;
}
