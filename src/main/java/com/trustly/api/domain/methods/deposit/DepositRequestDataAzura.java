package com.trustly.api.domain.methods.deposit;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder(toBuilder = true)
@Jacksonized
@RequiredArgsConstructor
@JsonInclude(Include.NON_NULL)
public class DepositRequestDataAzura {

  /**
   * Same value that was returned in the response to the initial call to AzuraDeposit.
   */
  @JsonProperty(value = "AzuraSessionID", required = true)
  @NotBlank
  String azuraSessionId;

  /**
   * SELECT_ACCOUNT: Deposit will have specified account preselected.
   * SELECT_BANK: Deposit will have specified bank preselected.
   * NO_SELECTION: Deposit will not have a preselected account or bank.
   */
  @JsonProperty(value = "Action", required = true)
  @NotBlank
  String action;

  /**
   * Id of the account that user wants to use. Should be the value from the field ‘accountid’ that is returned from the AzuraDeposit call.
   * Note: Required if Action is SELECT_ACCOUNT
   */
  @JsonProperty("AccountID")
  String accountId;

  /**
   * Id of the bank to use. Should be the value from the field ‘bankid’ that is returned from the AzuraDeposit call.
   * Note: Required if Action is SELECT_BANK
   */
  @JsonProperty("BankID")
  String bankId;
}
