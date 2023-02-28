package com.trustly.api.domain.methods.accountpayout;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.trustly.api.domain.base.AbstractRequestParamsDataAttributes;
import com.trustly.api.domain.common.RecipientOrSenderInformation;
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
public class AccountPayoutRequestDataAttributes extends AbstractRequestParamsDataAttributes {

  /**
   * The text to show on the end-user's bank statement after Trustly's own 10 digit reference (which always will be displayed first). The
   * reference must let the end user identify the merchant based on this value. So the ShopperStatement should contain either your brand
   * name, website name, or company name.
   * <p>
   * If possible, try to keep this text as short as possible to maximise the chance that the full reference will fit into the reference
   * field on the customer's bank since some banks allow only a limited number of characters.
   */
  @JsonProperty(value = "ShopperStatement", required = true)
  @NotBlank
  String shopperStatement;

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
   * processing account. It is also mandatory for E-wallets used directly in a merchant's checkout.
   * </p>
   */
  @JsonProperty(value = "pspMerchant")
  String pspMerchant;

  /**
   * URL of the consumer-facing website where the order is initiated
   *
   * <p>
   * Note: Mandatory attribute for Trustly Partners that are using Express Merchant Onboarding (EMO) and aggregate traffic under a master
   * processing account. It is also mandatory for E-wallets used directly in a merchant's checkout.
   * </p>
   */
  @JsonProperty(value = "PSPMerchantURL")
  String pspMerchantUrl;

  /**
   * VISA category codes describing the merchant's nature of business.
   *
   * <p>
   * Note: Mandatory attribute for Trustly Partners that are using Express Merchant Onboarding (EMO) and aggregate traffic under a master
   * processing account. It is also mandatory for E-wallets used directly in a merchant's checkout.
   * </p>
   */
  @JsonProperty(value = "MerchantCategoryCode")
  String merchantCategoryCode;

  /**
   * Information about the Payer (ultimate debtor). This is required for some merchants and partners, see below.
   */
  @JsonProperty(value = "SenderInformation")
  RecipientOrSenderInformation senderInformation;
}
