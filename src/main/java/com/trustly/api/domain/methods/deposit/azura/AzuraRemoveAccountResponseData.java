package com.trustly.api.domain.methods.deposit.azura;

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
public class AzuraRemoveAccountResponseData extends AbstractResponseResultData  {

  /**
   * 1 if the account was removed. 0 if it was not. If not removed there will be an additional error code in the rejected field.
   */
  @JsonProperty("result")
  String result;

  /**
   * If the account was not removed there will be an error code here. Any of:
   * ERROR_ACCOUNT_NOT_FOUND - No account exists with this id.
   * ERROR_AZURA_SESSION_NOT_FOUND - If the session could not be found.
   */
  @JsonProperty("rejected")
  String rejected;
}
