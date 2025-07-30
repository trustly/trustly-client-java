package com.trustly.api.domain.methods.deposit.azura;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.trustly.api.domain.base.AbstractRequestParamsDataAttributes;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
public class AzuraDepositRequestDataAttributes extends AbstractRequestParamsDataAttributes {

  /**
   * The email of the consumer.
   */
  @JsonProperty(value = "Email")
  @Email
  String email;

  /**
   * The mobile phone number of the end-user in international format.
   */
  @JsonProperty(value = "MobilePhone")
  String mobilePhone;

  /**
   * ID, username, hash or anything uniquely identifying the end-user requesting the deposit.
   * Preferably the same ID/username as used in the merchant's own backoffice in order to simplify
   * for the merchant's support department.
   */
  @JsonProperty(value = "EndUserID")
  String endUserID;

  /**
   * The ISO 3166-1-alpha-2 code of the end-user's country.
   */
  @JsonProperty(value = "Country", required = true)
  @NotBlank
  @Pattern(regexp = "[A-Z]{2}")
  String country;

  /**
   * The currency of the end-user's account in the merchant's system.
   */
  @JsonProperty(value = "Currency", required = true)
  @NotBlank
  String currency;

  /**
   * Only for Gaming
   * If it's set to 1, it means requiring KYC.
   */
  @JsonProperty(value = "RequestKYC")
  Integer requestKYC;

  /**
   * Only for Express Merchant Onboarding
   * Human-readable identifier of the consumer-facing merchant (e.g. legal name or trade name).
   */
  @JsonProperty(value = "PSPMerchant")
  String pspMerchant;

  /**
   * Only for Trustly Direct Debit.
   * Request a direct debit mandate from the selected account. 1 or 0.
   * See section "Direct Debit Mandates" below for details.
   */
  @JsonProperty(value = "RequestDirectDebitMandate")
  String requestDirectDebitMandate;

  /**
   * Only for Trustly Direct Debit.
   * In order to let the consumer choose to sign up for a recurring mandate,
   * the attribute QuickDeposit with the value 1 shall be included in the call.
   */
  @JsonProperty(value = "QuickDeposit")
  Integer quickDeposit;

}
