package com.trustly.api.domain.methods.merchantsettlement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.trustly.api.domain.base.AbstractToTrustlyRequestData;
import com.trustly.api.domain.base.EmptyRequestDataAttributes;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.validator.constraints.URL;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Jacksonized
@JsonInclude(Include.NON_NULL)
public class MerchantSettlementRequestData extends AbstractToTrustlyRequestData<EmptyRequestDataAttributes> {

  /**
   * ID, username, hash or anything uniquely identifying the end-user to be identified. Preferably the same ID/username as used in the
   * merchant's own backoffice in order to simplify for the merchant's support department
   */
  @JsonProperty(value = "EndUserID")
  String endUserId;

  /**
   * The clearing house of the end-user's bank account. Typically the name of a country in uppercase letters.
   * <p>
   * See table <a href="https://developers.trustly.com/emea/docs/registeraccount">here</a>.
   *
   * <pre>{@code SWEDEN}</pre>
   */
  @JsonProperty(value = "ClearingHouse")
  String clearingHouse;

  /**
   * The bank number identifying the end-user's bank in the given clearing house. For bank accounts in IBAN format you should just provide
   * an empty string (""). For non-IBAN format, see table <a href="https://developers.trustly.com/emea/docs/registeraccount">here</a>
   */
  @JsonProperty(value = "BankNumber")
  String bankNumber;

  /**
   * The account number, identifying the end-user's account in the bank. Can be either IBAN or country-specific format, see table
   * <a href="https://developers.trustly.com/emea/docs/registeraccount">here</a>
   */
  @JsonProperty(value = "AccountNumber")
  String accountNumber;

  /**
   * First name of the account holder (or the name of the company/organization)
   */
  @JsonProperty(value = "Firstname")
  String firstname;

  /**
   * Last name of the account holder (empty for organizations/companies)
   */
  @JsonProperty(value = "Lastname")
  String lastname;

  /**
   * The URL to which notifications for this payment should be sent to. This URL should be hard to guess and not contain a ? ("question
   * mark").
   */
  @JsonProperty(value = "NotificationURL", required = true)
  @NotBlank
  @URL
  String notificationUrl;

  /**
   * Your unique ID for the payout. If the MessageID is a previously initiated P2P order then the payout will be attached to that P2P order
   * and the amount must be equal to or lower than the previously deposited amount.
   */
  @JsonProperty(value = "MessageID", required = true)
  @NotBlank
  String messageId;

  /**
   * The amount to send with exactly two decimals. Only digits. Use dot (.) as decimal separator. If the end-user holds a balance in the
   * merchant's system then the amount must have been deducted from that balance before calling this method.
   */
  @JsonProperty(value = "Amount", required = true)
  @NotBlank
  String amount;

  /**
   * The currency of the end-user's account in the merchant's system.
   */
  @JsonProperty(value = "Currency", required = true)
  @NotBlank
  String currency;
}

