package com.trustly.api.domain.notifications;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trustly.api.domain.base.AbstractRequestParamsDataAttributes;
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
public class AccountNotificationDataAttributes extends AbstractRequestParamsDataAttributes {

  /**
   * The clearinghouse for this account
   */
  @JsonProperty("clearinghouse")
  String clearinghouse;

  /**
   * The bank for this account
   */
  @JsonProperty("bank")
  String bank;

  /**
   * A text that is safe to show the enduser for identifying the account.Do not parse this text since it will be a different format for different accounts.
   */
  @JsonProperty("descriptor")
  String descriptor;

  /**
   * The last digits of the bank account number.This can be used for matching against received KYC data from your manual routines.
   */
  @JsonProperty("lastdigits")
  String lastDigits;

  /**
   * An ID that uniquely identifies the account holder.Note: The format of this field will for some countries look different than the example.
   */
  @JsonProperty("personid")
  String personId;

  /**
   * The name of the account holder
   */
  @JsonProperty("name")
  String name;

  /**
   * The address of the account holder
   */
  @JsonProperty("address")
  String address;

  /**
   * The zipcode of the account holder
   */
  @JsonProperty("zipcode")
  String zipcode;

  /**
   * The city of the account holder
   */
  @JsonProperty("city")
  String city;

  /**
   * 1 if a direct debit mandate exists for this account, 0 otherwise
   */
  @JsonProperty("directdebitmandate")
  Integer directDebitMandate;
}
