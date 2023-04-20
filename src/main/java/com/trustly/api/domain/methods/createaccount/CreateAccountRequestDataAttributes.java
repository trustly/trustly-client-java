package com.trustly.api.domain.methods.createaccount;

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
import lombok.extern.jackson.Jacksonized;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Jacksonized
@JsonInclude(Include.NON_NULL)
public class CreateAccountRequestDataAttributes extends AbstractRequestParamsDataAttributes {

  /**
   * The date of birth of the account holder(ISO 8601).
   */
  @JsonProperty(value = "DateOfBirth")
  String dateOfBirth;

  /**
   * The mobile phonenumber to the account holder in international format.This is used for KYC and AML routines.
   */
  @JsonProperty(value = "MobilePhone")
  String mobilePhone;

  /**
   * The account holder's social security number / personal number / birth number / etc. Useful for some banks for identifying transactions
   * and KYC/AML.
   */
  @JsonProperty(value = "NationalIdentificationNumber")
  String nationalIdentificationNumber;

  /**
   * The ISO 3166-1-alpha-2 code of the account holder's country.
   */
  @JsonProperty(value = "AddressCountry")
  String addressCountry;

  /**
   * Postal code of the account holder.
   */
  @JsonProperty(value = "AddressPostalCode")
  String addressPostalCode;

  /**
   * City of the account holder.
   */
  @JsonProperty(value = "AddressCity")
  String addressCity;

  /**
   * Street address of the account holder.
   */
  @JsonProperty(value = "AddressLine1")
  String addressLine1;

  /**
   * Additional address information of the account holder.
   */
  @JsonProperty(value = "AddressLine2")
  String addressLine2;

  /**
   * The entire address of the account holder. This attribute should only be used if you are unable to provide the address information in
   * the 5 separate attributes:
   *
   * <ul>
   *   <li>AddressCountry</li>
   *   <li>AddressPostalCode</li>
   *   <li>AddressCity</li>
   *   <li>AddressLine1</li>
   *   <li>AddressLine2</li>
   * </ul>
   */
  @JsonProperty(value = "Address")
  String address;

  /**
   * The email address of the account holder.
   */
  @JsonProperty(value = "Email")
  @Email
  String email;
}
