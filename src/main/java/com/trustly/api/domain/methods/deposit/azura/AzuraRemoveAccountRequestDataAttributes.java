package com.trustly.api.domain.methods.deposit.azura;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.trustly.api.domain.base.AbstractRequestParamsDataAttributes;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class AzuraRemoveAccountRequestDataAttributes extends AbstractRequestParamsDataAttributes {

  /**
   * Same value that was returned in the response to the initial call to AzuraDeposit.
   */
  @JsonProperty(value = "AzuraSessionID")
  String azuraSessionId;

  /**
   * ID of the account to be removed. This should be the same value as returned in the response to the initial AzuraDeposit call.
   */
  @JsonProperty(value = "AccountID")
  String accountId;
}
