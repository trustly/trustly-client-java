package com.trustly.api.domain.methods.charge;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.trustly.api.domain.base.AbstractRequestParamsDataAttributes;
import jakarta.validation.constraints.Email;
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
public class ChargeRequestDataAttributes extends AbstractRequestParamsDataAttributes {

  /**
   * The text to show on the end-user's bank statement as well as in end-user e-mail communication. On the bank statement, only the first
   * seven characters (along with Trustly's reference) will be shown.
   * <p>
   * Allowed characters:
   * <ul>
   *   <li>{@code A-Z} (both upper and lower case),</li>
   *   <li>{@code 0-9},</li>
   *   <li>{@code "."}, {@code "-"}, {@code "_"}, {@code " "} (dot, dash, underscore, space).</li>
   * </ul>
   *
   * <p>
   * With {@code "Sport Shop"} set as ShopperStatement:
   * <dl>
   *   <dt>In Bank Statement:</dt>
   *   <dd><pre>{@code "T Sport S xyz"}</pre></dd>
   *
   *   <dt>In Email:</dt>
   *   <dd><pre>{@code "Sport Shop"}</pre></dd>
   * </dl>
   */
  @JsonProperty(value = "ShopperStatement", required = true)
  @NotBlank
  String shopperStatement;

  /**
   * The email address of the end user.
   */
  @JsonProperty(value = "Email", required = true)
  @NotBlank
  @Email
  String email;

  /**
   * The date when the funds will be charged from the end user's bank account. If this attribute is not sent, the charge will be attempted
   * as soon as possible.
   */
  @JsonProperty(value = "PaymentDate")
  String paymentDate;

  /**
   * The ExternalReference is a reference set by the merchant for any purpose and does not need to be unique for every API call. For
   * example, it can be used for invoice references, OCR numbers and also for offering end users the option to part-pay an invoice using the
   * same ExternalReference. The ExternalReference will be included in version 1.2 of the settlement report,
   * ViewAutomaticSettlementDetailsCSV.
   *
   * <pre>{@code 32423534523}</pre>
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
  @JsonProperty(value = "pspMerchant")
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
}
