package com.trustly.api.domain.methods.registeraccount;

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
public class RegisterAccountResponseData extends AbstractResponseResultData {

  /**
   * The globally unique AccountID the account was assigned in our system.
   *
   * <pre>{@code 7653385737}</pre>
   */
  @JsonProperty(value = "accountid")
  String accountId;

  /**
   * The clearinghouse for this account.
   *
   * <pre>{@code SWEDEN}</pre>
   */
  @JsonProperty(value = "clearinghouse")
  String clearingHouse;

  /**
   * The name of the bank for this account.
   *
   * <pre>{@code Skandiabanken}</pre>
   */
  @JsonProperty(value = "bank")
  String bank;

  /**
   * A descriptor for this account that is safe to show to the end user.
   *
   * <pre>{@code ***4057}</pre>
   */
  @JsonProperty(value = "descriptor")
  String descriptor;
}
