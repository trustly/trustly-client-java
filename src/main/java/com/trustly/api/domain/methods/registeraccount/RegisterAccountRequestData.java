package com.trustly.api.domain.methods.registeraccount;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.trustly.api.domain.base.AbstractToTrustlyRequestData;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Jacksonized
@JsonInclude(Include.NON_NULL)
public class RegisterAccountRequestData extends AbstractToTrustlyRequestData<RegisterAccountRequestDataAttributes> {

  /**
   * ID, username, hash or anything uniquely identifying the end-user to be identified. Preferably the same ID/username as used in the
   * merchant's own backoffice in order to simplify for the merchant's support department
   */
  @JsonProperty(value = "EndUserID")
  @NotBlank
  String endUserId;

  /**
   * The clearing house of the end-user's bank account. Typically the name of a country in uppercase letters. See table
   * <a href="https://developers.trustly.com/emea/docs/registeraccount">here</a>
   * <pre>{@code SWEDEN}</pre>
   */
  @JsonProperty(value = "ClearingHouse")
  @NotBlank
  String clearingHouse;

  /**
   * The bank number identifying the end-user's bank in the given clearing house. For bank accounts in IBAN format you should just provide
   * an empty string (""). For non-IBAN format, see table <a href="https://developers.trustly.com/emea/docs/registeraccount">here</a>
   */
  @JsonProperty(value = "BankNumber")
  @NotBlank
  String bankNumber;

  /**
   * The account number, identifying the end-user's account in the bank. Can be either IBAN or country-specific format, see table
   * <a href="https://developers.trustly.com/emea/docs/registeraccount">here</a>
   */
  @JsonProperty(value = "AccountNumber")
  @NotBlank
  String accountNumber;

  /**
   * First name of the account holder (or the name of the company/organization)
   */
  @JsonProperty(value = "Firstname")
  @NotBlank
  String firstname;

  /**
   * Last name of the account holder (empty for organizations/companies)
   */
  @JsonProperty(value = "Lastname")
  @NotBlank
  String lastname;
}

