package com.trustly.api.domain.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder(toBuilder = true)
@Jacksonized
@RequiredArgsConstructor
public class RecipientOrSenderInformation {

  /**
   * Partytype can be "PERSON" or "ORGANISATION" (if the recipient or ultimate debtor is an organisation/company).
   */
  @JsonProperty("Partytype")
  @NotBlank
  String partytype;

  /**
   * First name of the person, or the name of the organisation.
   */
  @JsonProperty("Firstname")
  @NotBlank
  String firstname;

  /**
   * Last name of the person (NULL for organisation).
   */
  @JsonProperty("Lastname")
  @NotBlank
  String lastname;

  /**
   * The ISO 3166-1-alpha-2 code of the country that the recipient resides in.
   */
  @JsonProperty("CountryCode")
  @NotBlank
  @Pattern(regexp = "[A-Z]{2}")
  String countryCode;

  /**
   * Payment account number or an alternative consistent unique identifier(e.g.customer number). Note: this is not a transaction ID or
   * similar.This identifier must stay consistent across all transactions relating to this recipient (payee).
   */
  @JsonProperty("CustomerID")
  String customerID;

  /**
   * Full address of the recipient, excluding the country.
   */
  @JsonProperty("Address")
  String address;

  /**
   * Date of birth (YYYY-MM-DD) of the beneficiary, or organisational number for the organisation.
   */
  @JsonProperty("DateOfBirth")
  String dateOfBirth;
}
