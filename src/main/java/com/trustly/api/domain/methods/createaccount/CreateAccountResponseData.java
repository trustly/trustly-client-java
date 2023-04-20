package com.trustly.api.domain.methods.createaccount;

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
public class CreateAccountResponseData extends AbstractResponseResultData {

  /**
   * The globally unique AccountID the account was assigned in our system.
   */
  @JsonProperty("accountid")
  String accountId;

  /**
   * The clearinghouse for this account.
   */
  @JsonProperty("clearinghouse")
  String clearingHouse;

  /**
   * The name of the bank for this account.
   */
  @JsonProperty("bank")
  String bank;

  /**
   * A descriptor for this account that is safe to show to the end user.
   */
  @JsonProperty("descriptor")
  String descriptor;
}
