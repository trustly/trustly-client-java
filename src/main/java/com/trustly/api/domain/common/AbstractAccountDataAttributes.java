package com.trustly.api.domain.common;

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
import org.hibernate.validator.constraints.URL;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class AbstractAccountDataAttributes extends AbstractRequestParamsDataAttributes {

  /**
   * The end-user's first name.
   */
  @JsonProperty(value = "Firstname", required = true)
  @NotBlank
  String firstname;

  /**
   * The end-user's last name.
   */
  @JsonProperty(value = "Lastname", required = true)
  @NotBlank
  String lastname;

  /**
   * The ISO 3166-1-alpha-2 code of the end-user's country. This will be used for pre-selecting the country for the end-user in the iframe.
   * Note: This will only have an effect for new end-users.If an end-user has done a previous order(with the same EndUserID), the country
   * that was last used will be pre-selected.
   */
  @JsonProperty(value = "Country", required = true)
  @NotBlank
  @Pattern(regexp = "[A-Z]{2}")
  String country;

  /**
   * The end-users localization preference in the format[language[_territory]]. Language is the ISO 639-1 code and territory the ISO
   * 3166-1-alpha-2 code.
   */
  @JsonProperty(value = "Locale", required = true)
  @Pattern(regexp = "[a-z]{2}_[A-Z]{2}")
  @NotBlank
  String locale;

  /**
   * The text to show on the end-user's bank statement after Trustly's own 10 digit reference(which always will be displayed first). The
   * reference must let the end user identify the merchant based on this value.So the ShopperStatement should contain either your brand
   * name, website name, or company name.
   */
  @JsonProperty(value = "ShopperStatement")
  @NotBlank(groups = {DepositValidationGroup.class})
  String shopperStatement;

  /**
   * The email address of the end user.
   */
  @JsonProperty(value = "Email")
  @NotBlank(groups = {DepositValidationGroup.class})
  @Email
  String email;

  /**
   * The mobile phone number of the end-user in international format.
   */
  @JsonProperty(value = "MobilePhone")
  @NotBlank(groups = {DepositValidationGroup.class})
  String mobilePhone;

  /**
   * The IP-address of the end-user.
   */
  @JsonProperty("IP")
  String ip;

  /**
   * The URL to which the end-user should be redirected after a successful deposit. Do not put any logic on that page since it's not
   * guaranteed that the end-user will in fact visit it.
   *
   * <pre>{@code https://example.com/thank_you.html}</pre>
   */
  @JsonProperty("SuccessURL")
  @NotBlank
  @URL
  String successUrl;

  /**
   * The URL to which the end-user should be redirected after a failed deposit. Do not put any logic on that page since it's not guaranteed
   * that the end-user will in fact visit it.
   *
   * <pre>{@code https://trustly.com/error.html}</pre>
   */
  @JsonProperty(value = "FailURL")
  @NotBlank
  @URL
  String failURL;

  /**
   * The TemplateURL should be used if you want to design your own payment page but have it hosted on Trustly's side. The URL of your
   * template page should be provided in this attribute in every Deposit API call. Our system will then fetch the content of your template
   * page, insert the Trustly iframe into it and host the entire page on Trustly’s side. In the response to the Deposit request, you will
   * receive a URL to the hosted template page which you should redirect the user to (the hosted page cannot be iframed).
   */
  @JsonProperty(value = "TemplateURL")
  String templateURL;

  /**
   * The html target/framename of the SuccessURL. Only _top, _self and _parent are suported.
   */
  @JsonProperty(value = "URLTarget")
  String urlTarget;

  /**
   * The end-user's social security number / personal number / birth number / etc. Useful for some banks for identifying transactions and
   * KYC/AML. If a Swedish personid ("personnummer") is provided, it will be pre-filled when the user logs in to their bank.
   */
  @JsonProperty(value = "NationalIdentificationNumber")
  String nationalIdentificationNumber;

  /**
   * This attribute disables the possibility to change/type in national identification number when logging in to a Swedish bank.If this
   * attribute is sent, the attribute NationalIdentificationNumber needs to be correctly included in the request. Note: This is only
   * available for Swedish banks.
   */
  @JsonProperty(value = "UnchangeableNationalIdentificationNumber")
  String unchangeableNationalIdentificationNumber;

  /**
   * If you are using Trustly from within your native iOS app, this attribute should be sent so that we can redirect the users back to your
   * app in case an external app is used for authentication (for example Mobile Bank ID in Sweden).
   */
  @JsonProperty("URLScheme")
  String urlScheme;
}
