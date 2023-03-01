package com.trustly.api.domain.methods.withdraw;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.trustly.api.domain.common.AbstractAmountConstrainedAccountDataAttributes;
import com.trustly.api.domain.common.RecipientOrSenderInformation;
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
public class WithdrawRequestDataAttributes extends AbstractAmountConstrainedAccountDataAttributes {

  /**
   * Sets a fixed withdrawal amount which cannot be changed by the end-user in the Trustly iframe. If this attribute is not sent, the
   * end-user will be asked to select the withdrawal amount in the Trustly iframe
   * <p>
   * Do not use in combination with {@link WithdrawRequestDataAttributes#getSuggestedMinAmount()} and
   * {@link WithdrawRequestDataAttributes#getSuggestedMaxAmount()}.
   * <p>
   * Use dot(.) as decimal separator.
   */
  @JsonProperty(value = "SuggestedAmount")
  String suggestedAmount;

  /**
   * The end-user's first name.
   */
  @JsonProperty(value = "DateOfBirth")
  String dateOfBirth;

  /**
   * The ISO 3166-1-alpha-2 code of the shipping address country.
   */
  @JsonProperty(value = "AddressCountry")
  String addressCountry;

  /**
   * The postalcode of the shipping address.
   */
  @JsonProperty(value = "AddressPostalCode")
  String addressPostalCode;

  /**
   * The city of the shipping address.
   */
  @JsonProperty(value = "AddressCity")
  String addressCity;

  /**
   * Shipping address street
   */
  @JsonProperty(value = "AddressLine1")
  String addressLine1;

  /**
   * Additional shipping address information.
   */
  @JsonProperty(value = "AddressLine2")
  String addressLine2;

  /**
   * The entire shipping address.
   * <p>
   * This attribute should only be used if you are unable to provide the shipping address information in the 5 separate properties:
   * <ul>
   *   <li>{@link WithdrawRequestDataAttributes#getAddressCountry()}</li>
   *   <li>{@link WithdrawRequestDataAttributes#getAddressCity()}</li>
   *   <li>{@link WithdrawRequestDataAttributes#getAddressPostalCode()}</li>
   *   <li>{@link WithdrawRequestDataAttributes#getAddressLine1()}</li>
   *   <li>{@link WithdrawRequestDataAttributes#getAddressLine2()}</li>
   * </ul>
   */
  @JsonProperty(value = "Address")
  String address;

  /**
   * The ExternalReference is a reference set by the merchant for any purpose and does not need to be unique for every API call. The
   * ExternalReference will be included in version 1.2 of the settlement report, ViewAutomaticSettlementDetailsCSV.
   */
  @JsonProperty(value = "ExternalReference")
  String externalReference;

  /**
   * Human-readable identifier of the consumer-facing merchant (e.g. legal name or trade name)
   *
   * <p>
   * Note: Mandatory attribute for Trustly Partners that are using Express Merchant Onboarding (EMO) and aggregate traffic under a master
   * processing account.
   * </p>
   */
  String pspMerchant;

  /**
   * URL of the consumer-facing website where the order is initiated
   *
   * <p>
   * Note: Mandatory attribute for Trustly Partners that are using Express Merchant Onboarding (EMO) and aggregate traffic under a master
   * processing account.
   * </p>
   */
  @JsonProperty(value = "PSPMerchantURL")
  String pspMerchantUrl;

  /**
   * VISA category codes describing the merchant's nature of business.
   *
   * <p>
   * Note: Mandatory attribute for Trustly Partners that are using Express Merchant Onboarding (EMO) and aggregate traffic under a master
   * processing account.
   * </p>
   */
  @JsonProperty(value = "MerchantCategoryCode")
  String merchantCategoryCode;

  /**
   * Information about the Payer (ultimate debtor).
   * <p>
   * SenderInformation is mandatory for money transfer services (including remittance houses), e-wallets, prepaid cards, as well as for
   * Trustly Partners that are using Express Merchant Onboarding and aggregate traffic under a master processing account (other cases may
   * also apply).
   */
  @JsonProperty(value = "SenderInformation")
  RecipientOrSenderInformation senderInformation;
}
