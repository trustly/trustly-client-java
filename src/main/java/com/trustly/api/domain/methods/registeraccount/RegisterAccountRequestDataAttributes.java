package com.trustly.api.domain.methods.registeraccount;

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
public class RegisterAccountRequestDataAttributes extends AbstractRequestParamsDataAttributes {

  /**
   * The end-user's date of birth.
   *
   * <pre>{@code 1979-01-31}</pre>
   */
  @JsonProperty(value = "DateOfBirth")
  String dateOfBirth;

  /**
   * The mobile phonenumber to the account holder in international format. This is used for KYC and AML routines.
   *
   * <pre>{@code +46709876543}</pre>
   */
  @JsonProperty(value = "MobilePhone")
  String mobilePhone;

  /**
   * The account holder's social security number / personal number / birth number / etc. Useful for some banks for identifying transactions
   * and KYC/AML.
   *
   * <pre>{@code 790131-1234}</pre>
   */
  @JsonProperty(value = "NationalIdentificationNumber")
  String nationalIdentificationNumber;

  /**
   * The ISO 3166-1-alpha-2 code of the account holder's country.
   *
   * <pre>{@code SE}</pre>
   */
  @JsonProperty(value = "AddressCountry")
  String addressCountry;

  /**
   * Postal code of the account holder.
   *
   * <pre>{@code SE-11253}</pre>
   */
  @JsonProperty(value = "AddressPostalCode")
  String addressPostalCode;

  /**
   * City of the account holder.
   *
   * <pre>{@code Stockholm}</pre>
   */
  @JsonProperty(value = "AddressCity")
  String addressCity;

  /**
   * Street address of the account holder.
   *
   * <pre>{@code  Main street 1}</pre>
   */
  @JsonProperty(value = "AddressLine1")
  String addressLine1;

  /**
   * Additional address information of the account holder.
   *
   * <pre>{@code Apartment 123, 2 stairs up}</pre>
   */
  @JsonProperty(value = "AddressLine2")
  String addressLine2;

  /**
   * The entire address of the account holder. This attribute should only be used if you are unable to provide the address information in
   * the 5 separate attributes above (AddressCountry, AddressPostalCode, AddressCity, AddressLine1 and AddressLine2).
   *
   * <pre>{@code Birgerstreet 14, SE-11411, Stockholm, Sweden}</pre>
   */
  @JsonProperty(value = "Address")
  String address;

  /**
   * The email address of the account holder.
   *
   * <pre>{@code test@trustly.com}</pre>
   */
  @JsonProperty(value = "Email")
  @Email
  String email;

}
