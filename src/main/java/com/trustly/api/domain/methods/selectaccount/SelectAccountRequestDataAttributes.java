package com.trustly.api.domain.methods.selectaccount;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.trustly.api.domain.common.AbstractAccountDataAttributes;
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
public class SelectAccountRequestDataAttributes extends AbstractAccountDataAttributes {

  /**
   * Only for Trustly Direct Debit. Request a direct debit mandate from the selected account. 1 or 0. See section "Direct Debit Mandates"
   * below for details.
   * <p>
   * If this is set to 1, then {@link SelectAccountRequestDataAttributes#getEmail()} is required.
   */
  @JsonProperty(value = "RequestDirectDebitMandate")
  int requestDirectDebitMandate;

  /**
   * The end-user's date of birth.
   *
   * <pre>{@code 1979-01-31}</pre>
   */
  @JsonProperty(value = "DateOfBirth")
  String dateOfBirth;

  /**
   * Human-readable identifier of the consumer-facing merchant(e.g.legal name or trade name)
   *
   * <p>
   * Note:  Mandatory attribute for Trustly Partners that are using Express Merchant Onboarding(EMO) and aggregate traffic under a master
   * processing account.
   * </p>
   *
   * <pre>{@code Merchant Ltd.}</pre>
   */
  @JsonProperty(value = "pspMerchant")
  String pspMerchant;

  /**
   * URL of the consumer-facing website where the order is initiated
   *
   * <p>
   * Note:  Mandatory attribute for Trustly Partners that are using Express Merchant Onboarding(EMO) and aggregate traffic under a master
   * processing account.
   * </p>
   *
   * <pre>{@code www.merchant.com}</pre>
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
