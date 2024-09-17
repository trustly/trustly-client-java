package com.trustly.api.domain;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import lombok.Builder.Default;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Singular;

@Generated(value = "omnigen", date = "2024-09-06T06:45:43.834Z")
@SuppressWarnings("unused")
public class Models {
  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class AbstractAccountNotificationData<T extends AbstractRequestDataAttributes> extends AbstractNotificationRequestData {
    /**
     * The globally unique AccountID the account was assigned in our system. The AccountID of a returning customer. Allows for a quicker payment experience in some markets, see Trustly Express.
     * <h2>Examples</h2>
     * <ul>
     *   <li>1234567890</li>
     *   <li>7653385737</li>
     * </ul>
     */
    @JsonProperty(value = "accountid", required = true)
    @JsonInclude
    @NotNull
    private String accountID;
    /**
     * Whether the account is verified or not. 0 for not verified, 1 for verified.
     */
    @JsonProperty(value = "verified", required = true)
    @JsonInclude
    @NotNull
    private StringBoolean verified;
    @JsonProperty(value = "attributes")
    @Valid
    private T attributes;
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class AbstractAccountNotificationDataAttributes extends AbstractRequestDataAttributes {
    /**
     * The clearing house of the end-user's bank account. Typically the name of a country in uppercase letters. See examples or table at https://developers.trustly.com/emea/docs/registeraccount.
     * <h2>Examples</h2>
     * <ul>
     *   <li>AUSTRIA</li>
     *   <li>BELGIUM</li>
     *   <li>BULGARIA</li>
     *   <li>CROATIA</li>
     *   <li>CYPRUS</li>
     *   <li>CZECH_REPUBLIC</li>
     *   <li>DENMARK</li>
     *   <li>ESTONIA</li>
     *   <li>FINLAND</li>
     *   <li>FRANCE</li>
     *   <li>GERMANY</li>
     *   <li>GREECE</li>
     *   <li>HUNGARY</li>
     *   <li>IRELAND</li>
     *   <li>ITALY</li>
     *   <li>LATVIA</li>
     *   <li>LITHUANIA</li>
     *   <li>LUXEMBOURG</li>
     *   <li>MALTA</li>
     *   <li>NETHERLANDS</li>
     *   <li>NORWAY</li>
     *   <li>POLAND</li>
     *   <li>PORTUGAL</li>
     *   <li>ROMANIA</li>
     *   <li>SLOVAKIA</li>
     *   <li>SLOVENIA</li>
     *   <li>SPAIN</li>
     *   <li>SWEDEN</li>
     *   <li>UNITED_KINGDOM</li>
     * </ul>
     */
    @JsonProperty(value = "clearinghouse")
    private String clearingHouse;
    /**
     * The bank for this account
     * <h2>Examples</h2>
     * <ul>
     *   <li>SEB</li>
     *   <li>Skandiabanken</li>
     * </ul>
     */
    @JsonProperty(value = "bank")
    private String bank;
    /**
     * A text that is safe to show the enduser for identifying the account. Do not parse this text since it will be a different format for different accounts.
     * <h2>Examples</h2>
     * <ul>
     *   <li>***4057</li>
     * </ul>
     */
    @JsonProperty(value = "descriptor")
    private String descriptor;
    /**
     * The last digits of the bank account number.This can be used for matching against received KYC data from your manual routines.
     */
    @JsonProperty(value = "lastdigits")
    private String lastdigits;
    /**
     * An ID that uniquely identifies the account holder. Only present in markets where SSN is applicable. Note: The format of this field will for some countries look different than the example.
     * <h2>Examples</h2>
     * <ul>
     *   <li>SE198201019876</li>
     *   <li>19900501</li>
     * </ul>
     */
    @JsonProperty(value = "personid")
    private String personID;
    /**
     * The name of the account holder
     * <h2>Examples</h2>
     * <ul>
     *   <li>John Doe</li>
     * </ul>
     */
    @JsonProperty(value = "name")
    private String name;
    /**
     * The address of the account holder
     */
    @JsonProperty(value = "address")
    private String address;
    /**
     * The zipcode of the account holder
     * <h2>Examples</h2>
     * <ul>
     *   <li>12345</li>
     * </ul>
     */
    @JsonProperty(value = "zipcode")
    private String zipcode;
    /**
     * The city of the account holder
     * <h2>Examples</h2>
     * <ul>
     *   <li>Examplecity</li>
     * </ul>
     */
    @JsonProperty(value = "city")
    private String city;
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class AbstractCancelNotificationData<T extends AbstractRequestDataAttributes> extends AbstractNotificationRequestData {
    @JsonProperty(value = "attributes")
    @Valid
    private T attributes;
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class AbstractCreditNotificationData<T extends AbstractRequestDataAttributes> extends AbstractNotificationRequestData {
    /**
     * ID, username, hash or anything uniquely identifying the end-user requesting the deposit.
     * Preferably the same ID/username as used in the merchant's own backoffice in order to simplify for the merchant's support department.
     */
    @JsonProperty(value = "enduserid")
    private String endUserID;
    /**
     * {@code 98.02}
     */
    @JsonProperty(value = "amount")
    private double amount;
    /**
     * The ISO 4217 code of the currency. See <a href="https://eu.developers.trustly.com/doc/reference/handling-currencies">documentation</a>
     * <h2>Examples</h2>
     * <ul>
     *   <li>BGN</li>
     *   <li>CZK</li>
     *   <li>DKK</li>
     *   <li>EUR</li>
     *   <li>GBP</li>
     *   <li>HRK</li>
     *   <li>HUF</li>
     *   <li>NOK</li>
     *   <li>PLN</li>
     *   <li>RON</li>
     *   <li>SEK</li>
     * </ul>
     */
    @JsonProperty(value = "currency")
    private String currency;
    /**
     * The time of the transaction and the GMT offset (+01 means GMT + 1 hours).
     * <h2>Examples</h2>
     * <ul>
     *   <li>2014-01-30 13:28:45.652299+01</li>
     *   <li>2014-03-31 11:50:06.46106+00</li>
     * </ul>
     */
    @JsonProperty(value = "timestamp")
    private String timestamp;
    @JsonProperty(value = "attributes")
    @Valid
    private T attributes;
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class AbstractCreditNotificationDataAttributes extends AbstractRequestDataAttributes {

  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class AbstractDebitNotificationData<T extends AbstractRequestDataAttributes> extends AbstractNotificationRequestData implements IAdditionalProperties {
    /**
     * <h2>Examples</h2>
     * <ul>
     *   <li>BGN: 100.00</li>
     *   <li>CZK: 100.00</li>
     *   <li>DKK: 100.00</li>
     *   <li>EUR: 100.00</li>
     *   <li>GBP: 100.00</li>
     *   <li>HRK: 100.00</li>
     *   <li>HUF: 100</li>
     *   <li>NOK: 100.00</li>
     *   <li>PLN: 100.00</li>
     *   <li>RON: 100.00</li>
     *   <li>SEK: 100.00</li>
     * </ul>
     */
    @JsonProperty(value = "amount", required = true)
    @JsonInclude
    @NotNull
    private String amount;
    /**
     * The ISO 4217 code of the currency. See <a href="https://eu.developers.trustly.com/doc/reference/handling-currencies">documentation</a>
     * <h2>Examples</h2>
     * <ul>
     *   <li>BGN</li>
     *   <li>CZK</li>
     *   <li>DKK</li>
     *   <li>EUR</li>
     *   <li>GBP</li>
     *   <li>HRK</li>
     *   <li>HUF</li>
     *   <li>NOK</li>
     *   <li>PLN</li>
     *   <li>RON</li>
     *   <li>SEK</li>
     * </ul>
     */
    @JsonProperty(value = "currency", required = true)
    @JsonInclude
    private String currency;
    /**
     * ID, username, hash or anything uniquely identifying the end-user requesting the deposit.
     * Preferably the same ID/username as used in the merchant's own backoffice in order to simplify for the merchant's support department.
     */
    @JsonProperty(value = "enduserid", required = true)
    @JsonInclude
    @NotNull
    private String endUserID;
    /**
     * The time of the transaction and the GMT offset (+01 means GMT + 1 hours).
     * <h2>Examples</h2>
     * <ul>
     *   <li>2014-01-30 13:28:45.652299+01</li>
     *   <li>2014-03-31 11:50:06.46106+00</li>
     * </ul>
     */
    @JsonProperty(value = "timestamp", required = true)
    @JsonInclude
    @NotNull
    private String timestamp;
    @Singular
    @JsonAnySetter
    private Map<String, Object> additionalProperties;
    @JsonProperty(value = "attributes")
    @Valid
    private T attributes;

    public void addAdditionalProperty(String key, Object value) {
      this.additionalProperties.put(key, value);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
      return this.additionalProperties;
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class AbstractKYCNotificationData extends AbstractNotificationRequestData {
    /**
     * Trustly generated unique identifier based on player’s bank account profile*.
     * Can be used as an identifier when <code>personid</code> is not available.
     * <p>
     * ***The identifier may change, hence our suggestion is to have a logic that does not include <code>KYCEntityID</code>
     * <h2>Examples</h2>
     * <ul>
     *   <li>29a750aa-0bad-4a28-a42d-ffb9a690d93a</li>
     * </ul>
     */
    @JsonProperty(value = "kycentityid", required = true)
    @JsonInclude
    @NotNull
    private String kycentityid;
    @JsonProperty(value = "attributes")
    @Valid
    private KYCNotificationDataAttributes attributes;

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class KYCNotificationDataAttributes extends AbstractRequestDataAttributes {
      /**
       * An ID that uniquely identifies the account holder. Only present in markets where SSN is applicable. Note: The format of this field will for some countries look different than the example.
       * <h2>Examples</h2>
       * <ul>
       *   <li>SE198201019876</li>
       *   <li>19900501</li>
       * </ul>
       */
      @JsonProperty(value = "personid", required = true)
      @JsonInclude
      @NotNull
      private String personID;
      /**
       * First name of the person, or the name of the organization/company.
       */
      @JsonProperty(value = "firstname", required = true)
      @JsonInclude
      @NotNull
      private String firstname;
      /**
       * Last name of the person (NULL/empty for organization/company).
       */
      @JsonProperty(value = "lastname", required = true)
      @JsonInclude
      private String lastname;
      /**
       * The end-user's date of birth.
       */
      @JsonProperty(value = "dob", required = true)
      @JsonInclude
      @NotNull
      private String dob;
      /**
       * Recipient address street
       * <h2>Examples</h2>
       * <ul>
       *   <li>Main Street 1</li>
       * </ul>
       */
      @JsonProperty(value = "street", required = true)
      @JsonInclude
      @NotNull
      private String street;
      /**
       * The postalcode of the recipient address.
       */
      @JsonProperty(value = "zipcode", required = true)
      @JsonInclude
      @NotNull
      private String zipcode;
      /**
       * The city of the recipient address.
       */
      @JsonProperty(value = "city", required = true)
      @JsonInclude
      @NotNull
      private String city;
      /**
       * The ISO 3166-1-alpha-2 code of the recipient address country.
       */
      @JsonProperty(value = "country", required = true)
      @JsonInclude
      @NotNull
      private String country;
    }
  }

  @Getter
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public abstract static class AbstractNotificationRequest {
    @JsonProperty(value = "method")
    private String method;
  }

  @Getter
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public abstract static class AbstractNotificationRequestData {
    /**
     * The globally unique OrderID the charge order was assigned in our system. The order has no end-user interaction; it is merely used as a reference for the notifications delivered regarding the request.
     * <h2>Examples</h2>
     * <ul>
     *   <li>9594811343</li>
     * </ul>
     */
    @JsonProperty(value = "orderid", required = true)
    @JsonInclude
    @NotNull
    private String orderID;
    /**
     * Your unique ID of the transaction.
     * <h2>Examples</h2>
     * <ul>
     *   <li>12345678</li>
     * </ul>
     */
    @JsonProperty(value = "messageid", required = true)
    @JsonInclude
    @NotNull
    private String messageID;
    /**
     * Unique ID for this notification. Each notification must only be handled once in your system.
     */
    @JsonProperty(value = "notificationid", required = true)
    @JsonInclude
    @NotNull
    private String notificationID;
  }

  @Getter
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public abstract static class AbstractNotificationResponse {

  }

  @Getter
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public abstract static class AbstractPendingNotificationData extends AbstractNotificationRequestData {
    /**
     * {@code 98.02}
     */
    @JsonProperty(value = "amount", required = true)
    @JsonInclude
    @NotNull
    private double amount;
    /**
     * The ISO 4217 code of the currency. See <a href="https://eu.developers.trustly.com/doc/reference/handling-currencies">documentation</a>
     * <h2>Examples</h2>
     * <ul>
     *   <li>BGN</li>
     *   <li>CZK</li>
     *   <li>DKK</li>
     *   <li>EUR</li>
     *   <li>GBP</li>
     *   <li>HRK</li>
     *   <li>HUF</li>
     *   <li>NOK</li>
     *   <li>PLN</li>
     *   <li>RON</li>
     *   <li>SEK</li>
     * </ul>
     */
    @JsonProperty(value = "currency", required = true)
    @JsonInclude
    private String currency;
    /**
     * The time of the transaction and the GMT offset (+01 means GMT + 1 hours).
     * <h2>Examples</h2>
     * <ul>
     *   <li>2014-01-30 13:28:45.652299+01</li>
     *   <li>2014-03-31 11:50:06.46106+00</li>
     * </ul>
     */
    @JsonProperty(value = "timestamp", required = true)
    @JsonInclude
    @NotNull
    private String timestamp;
    @JsonProperty(value = "attributes")
    @Valid
    private AnyAttributes attributes;
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class AbstractRequestData<T extends AbstractRequestDataAttributes> {
    @JsonProperty(value = "Username", required = true)
    @JsonInclude
    @NotNull
    private String username;
    @JsonProperty(value = "Password", required = true)
    @JsonInclude
    @NotNull
    private String password;
    @JsonProperty(value = "Attributes")
    @Valid
    private T attributes;
  }

  @Getter
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public abstract static class AbstractRequestDataAttributes implements IAdditionalProperties {
    @Singular
    @JsonAnySetter
    private Map<String, Object> additionalProperties;

    public void addAdditionalProperty(String key, Object value) {
      this.additionalProperties.put(key, value);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
      return this.additionalProperties;
    }
  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class AccountLedgerRequest extends JsonRpcRequest<AccountLedgerRequest.Params> {
    public AccountLedgerRequest() {
      super("AccountLedger");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcRequestParams<Params.Data> {
      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends AbstractRequestData<AnyAttributes> {
        /**
         * Date string in the ISO 8601 format (YYYY-MM-DD)
         * <h2>Examples</h2>
         * <ul>
         *   <li>2014-04-01</li>
         * </ul>
         */
        @JsonProperty(value = "FromDate", required = true)
        @JsonInclude
        @NotNull
        private String fromDate;
        /**
         * Date string in the ISO 8601 format (YYYY-MM-DD)
         * <h2>Examples</h2>
         * <ul>
         *   <li>2014-04-01</li>
         * </ul>
         */
        @JsonProperty(value = "ToDate", required = true)
        @JsonInclude
        @NotNull
        private String toDate;
        /**
         * The ISO 4217 code of the currency. See <a href="https://eu.developers.trustly.com/doc/reference/handling-currencies">documentation</a>
         * <h2>Examples</h2>
         * <ul>
         *   <li>BGN</li>
         *   <li>CZK</li>
         *   <li>DKK</li>
         *   <li>EUR</li>
         *   <li>GBP</li>
         *   <li>HRK</li>
         *   <li>HUF</li>
         *   <li>NOK</li>
         *   <li>PLN</li>
         *   <li>RON</li>
         *   <li>SEK</li>
         * </ul>
         */
        @JsonProperty(value = "Currency", required = true)
        @JsonInclude
        private String currency;
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class AccountLedgerResponse extends JsonRpcResponse<AccountLedgerResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<List<Result.DataEntry>> {
      public Result() {
        super.method = "AccountLedger";
      }

      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class DataEntry {
        /**
         * Your userid in our system.
         */
        @JsonProperty(value = "userid")
        private String userID;
        /**
         * The datestamp for when this ledger row affected your balance in our system.
         */
        @JsonProperty(value = "datestamp")
        private String datestamp;
        /**
         * The globally unique OrderID that resulted in this ledger record.
         */
        @JsonProperty(value = "orderid")
        private long orderID;
        /**
         * The name of the bookkeeping account this ledger record belongs to.
         */
        @JsonProperty(value = "accountname")
        private String accountname;
        /**
         * Your unique MessageID that you used to create the order that resulted in this ledger record.
         */
        @JsonProperty(value = "messageid")
        private String messageID;
        /**
         * A human friendly description of this ledger record.
         * <h2>Examples</h2>
         * <ul>
         *   <li>CLIENT_FUNDS_SWEDEN_ESSE</li>
         * </ul>
         */
        @JsonProperty(value = "transactiontype")
        private String transactiontype;
        /**
         * The currency of the amount in this ledger record.
         */
        @JsonProperty(value = "currency")
        private String currency;
        /**
         * The amount your balance in our system was affected with due to this ledger record. May contain a lot of decimals.
         */
        @JsonProperty(value = "amount")
        private String amount;
        /**
         * An ID meaning different things for different payment methods, you probably don't need this data.
         * <h2>Examples</h2>
         * <ul>
         *   <li>3209647863</li>
         * </ul>
         */
        @JsonProperty(value = "gluepayid")
        private String gluepayid;
      }
    }
  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class AccountMandateNotification extends JsonRpcNotification<AccountMandateNotification.Params> {
    public AccountMandateNotification() {
      super("account");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcNotificationParams<AccountMandateNotificationData> {

    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class AccountMandateNotificationData extends AbstractAccountNotificationData<AccountMandateNotificationData.Attributes> {
    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Attributes extends AbstractAccountNotificationDataAttributes {
      /**
       * Whether the direct debit mandate is active or not, 1 for active, 0 for non-active.
       */
      @JsonProperty(value = "directdebitmandate")
      private NumberBoolean directDebitMandate;
      /**
       * The ISO 3166-1-alpha-2 code of the end-user's country. This will be used for pre-selecting the country for the end-user in the iframe.
       * Note: This will only have an effect for new end-users. If an end-user has done a previous order (with the same EndUserID), the country that was last used will be pre-selected.
       */
      @JsonProperty(value = "country")
      private String country;
      /**
       * The bank number identifying the end-user's bank in the given clearing house. For bank accounts in IBAN format you should just provide an empty string (""). For non-IBAN format, see examples. The BankNumber for Swedish bank accounts should be the local "clearing number", and the AccountNumber parameter should contain the rest of the account number. Most Swedish banks have a 4-digit clearing number, but a 5-digit clearing number is used for Swedbank accounts when the clearing number starts with "8". Nordea accounts where the account number is the same as the person's national identification number always has "3300" as the clearing number.
       * <p>
       * IBAN for Swedish bank accounts is supported upon request. When making API calls with Swedish IBAN, ensure to include the Clearinghouse attribute as "IBAN" instead of "SWEDEN". See more at https://developers.trustly.com/emea/docs/registeraccount
       * <h2>Examples</h2>
       * <ul>
       *   <li>Sweden: ^[0-9]{4,5}$</li>
       *   <li>United Kingdom: ^[0-9]{6}$</li>
       * </ul>
       */
      @JsonProperty(value = "bankcode")
      private String bankcode;
      /**
       * Name of the account
       * <h2>Examples</h2>
       * <ul>
       *   <li>Salary account</li>
       * </ul>
       */
      @JsonProperty(value = "accountname")
      private String accountname;
      @JsonProperty(value = "accountholders")
      private List<String> accountholders;
      /**
       * The branch identifier
       * <h2>Examples</h2>
       * <ul>
       *   <li>6160</li>
       *   <li>6000</li>
       *   <li>bg</li>
       *   <li>123123</li>
       *   <li>HANDSESS</li>
       * </ul>
       */
      @JsonProperty(value = "bankidentifier")
      private String bankidentifier;
      /**
       * The account number, identifying the end-user's account in the bank. Can be either IBAN or country-specific format, see examples or read more at https://developers.trustly.com/emea/docs/registeraccount
       * <h2>Examples</h2>
       * <ul>
       *   <li>6112</li>
       *   <li>391124057</li>
       *   <li>AUSTRIA: ^AT[0-9]{18}$</li>
       *   <li>BELGIUM: ^BE[0-9]{14}$</li>
       *   <li>BULGARIA: ^BG[0-9]{2}[A-Z]{4}[0-9]{4}[0-9]{2}[A-Z0-9]{8}$</li>
       *   <li>CROATIA: ^HR[0-9]{2}[0-9]{7}[0-9]{10}$</li>
       *   <li>CYPRUS: ^CY[0-9]{10}[0-9A-Z]{16}$</li>
       *   <li>CZECH_REPUBLIC: ^CZ[0-9]{22}$</li>
       *   <li>DENMARK: ^DK[0-9]{16}$</li>
       *   <li>ESTONIA: ^EE[0-9]{18}$</li>
       *   <li>FINLAND: ^FI[0-9]{16}$</li>
       *   <li>FRANCE: ^FR[0-9]{12}[0-9A-Z]{11}[0-9]{2}$</li>
       *   <li>GERMANY: ^DE[0-9]{20}$</li>
       *   <li>GREECE: ^GR[0-9]{25}$</li>
       *   <li>HUNGARY: ^HU[0-9]{26}$</li>
       *   <li>IRELAND: ^IE[0-9]{2}[A-Z]{4}[0-9]{14}$</li>
       *   <li>ITALY: ^IT[0-9]{2}[A-Z][0-9]{10}[0-9A-Z]{12}$</li>
       *   <li>LATVIA: ^LV[0-9]{2}[A-Z]{4}[0-9A-Z]{13}$</li>
       *   <li>LITHUANIA: ^LT[0-9]{18}$</li>
       *   <li>LUXEMBOURG: ^LU[0-9]{18}$</li>
       *   <li>MALTA: ^MT[0-9]{2}[A-Z]{4}[0-9]{5}[0-9A-Z]{18}$</li>
       *   <li>NETHERLANDS: ^NL[0-9]{2}[A-Z]{4}[0-9]{10}$</li>
       *   <li>NORWAY: ^NO[0-9]{13}$</li>
       *   <li>POLAND: ^PL[0-9]{26}$</li>
       *   <li>PORTUGAL: ^PT[0-9]{23}$</li>
       *   <li>ROMANIA: ^RO[0-9]{2}[A-Z]{4}[0-9A-Z]{16}$</li>
       *   <li>SLOVAKIA: ^SK[0-9]{22}$</li>
       *   <li>SLOVENIA: ^SI56[0-9]{15}$</li>
       *   <li>SPAIN: ^ES[0-9]{22}$</li>
       *   <li>SWEDEN:* [0-9]{1,15}$</li>
       *   <li>UNITED_KINGDOM: ^[0-9]{8}$</li>
       * </ul>
       */
      @JsonProperty(value = "accountnumber")
      private String accountnumber;
      @JsonProperty(value = "accountsource")
      private MandateAccountSource accountsource;

      public enum MandateAccountSource {
        /**
         * Account details from AIS
         */
        AIS("AIS"),
        /**
         * Manually inputed account number
         */
        MANUAL_ENTRY("MANUAL_ENTRY"),
        /**
         * AccountID provided when creating the order
         */
        ACCOUNT_ID("ACCOUNT_ID"),
        /**
         * If the scheme notifies about a change of account, this only aplies to BACS.
         */
        REGISTRATION_SCHEME("REGISTRATION_SCHEME");

        @JsonValue
        private final String value;

        MandateAccountSource(String value) {
          this.value = value;
        }
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class AccountMandateNotificationResponse extends JsonRpcResponse<AccountMandateNotificationResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<AckData> {
      public Result() {
        super.method = "account";
      }
    }
  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class AccountNotification extends JsonRpcNotification<AccountNotification.Params> {
    public AccountNotification() {
      super("account");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcNotificationParams<Params.Data> {
      @Getter
      @Jacksonized
      @Setter
      @SuperBuilder
      public static class Data {
        @JsonValue
        private final JsonNode _raw;
        @Valid
        private AccountDefaultNotificationData _accountDefaultNotificationData;
        @Valid
        private AccountMandateNotificationData _accountMandateNotificationData;

        @JsonCreator
        public Data(JsonNode raw) {
          this._raw = raw;
        }

        public AccountDefaultNotificationData getAccountDefaultNotificationData(ObjectMapper transformer) throws JsonProcessingException {
          if (this._accountDefaultNotificationData != null) {
            return this._accountDefaultNotificationData;
          }
          return this._accountDefaultNotificationData = transformer.treeToValue(this._raw, AccountDefaultNotificationData.class);
        }

        public AccountMandateNotificationData getAccountMandateNotificationData(ObjectMapper transformer) throws JsonProcessingException {
          if (this._accountMandateNotificationData != null) {
            return this._accountMandateNotificationData;
          }
          return this._accountMandateNotificationData = transformer.treeToValue(this._raw, AccountMandateNotificationData.class);
        }

        @Getter
        @Jacksonized
        @RequiredArgsConstructor
        @Setter
        @SuperBuilder
        public static class AccountDefaultNotificationData extends AbstractAccountNotificationData<AccountDefaultNotificationData.Attributes> {
          @Getter
          @Jacksonized
          @RequiredArgsConstructor
          @Setter
          @SuperBuilder
          public static class Attributes extends AbstractAccountNotificationDataAttributes {

          }
        }
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class AccountNotificationResponse extends JsonRpcResponse<AccountNotificationResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<AckData> {
      public Result() {
        super.method = "account";
      }
    }
  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class AccountPayoutRequest extends JsonRpcRequest<AccountPayoutRequest.Params> {
    public AccountPayoutRequest() {
      super("AccountPayout");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcRequestParams<Params.Data> {
      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends AbstractRequestData<Data.Attributes> {
        /**
         * The URL to which notifications for this payment should be sent to. This URL should be hard to guess and not contain a ? ("question mark").
         */
        @JsonProperty(value = "NotificationURL", required = true)
        @JsonInclude
        @NotNull
        private String notificationUrl;
        /**
         * The AccountID received from an Account notification to which the money shall be sent.
         */
        @JsonProperty(value = "AccountID", required = true)
        @JsonInclude
        @NotNull
        private String accountId;
        /**
         * ID, username, hash or anything uniquely identifying the end-user requesting the withdrawal. Preferably the same ID/username as used in the merchant's own backoffice in order to simplify for the merchant's support department.
         */
        @JsonProperty(value = "EndUserID", required = true)
        @JsonInclude
        @NotNull
        private String endUserId;
        /**
         * Your unique ID for the payout. If the MessageID is a previously initiated P2P order then the payout will be attached to that P2P order and the amount must be equal to or lower than the previously deposited amount.
         */
        @JsonProperty(value = "MessageID", required = true)
        @JsonInclude
        @NotNull
        private String messageId;
        /**
         * The amount to send. Only digits. Use dot (.) as decimal separator. If the end-user holds a balance in the merchant's system then the amount must have been deducted from that balance before calling this method.
         */
        @JsonProperty(value = "Amount", required = true)
        @JsonInclude
        @NotNull
        private String amount;
        /**
         * The ISO 4217 code of the currency. See <a href="https://eu.developers.trustly.com/doc/reference/handling-currencies">documentation</a>
         * <h2>Examples</h2>
         * <ul>
         *   <li>BGN</li>
         *   <li>CZK</li>
         *   <li>DKK</li>
         *   <li>EUR</li>
         *   <li>GBP</li>
         *   <li>HRK</li>
         *   <li>HUF</li>
         *   <li>NOK</li>
         *   <li>PLN</li>
         *   <li>RON</li>
         *   <li>SEK</li>
         * </ul>
         */
        @JsonProperty(value = "Currency", required = true)
        @JsonInclude
        private String currency;

        @Getter
        @Jacksonized
        @RequiredArgsConstructor
        @Setter
        @SuperBuilder
        public static class Attributes extends AbstractRequestDataAttributes {
          /**
           * The text to show on the end-user's bank statement after Trustly's own 10 digit reference (which always will be displayed first). The reference must let the end user identify the merchant based on this value. So the ShopperStatement should contain either your brand name, website name, or company name.
           * <p>
           * If possible, try to keep this text as short as possible to maximise the chance that the full reference will fit into the reference field on the customer's bank since some banks allow only a limited number of characters. If the full ShopperStatement does not fit into the reference it will be truncated from the end.
           */
          @JsonProperty(value = "ShopperStatement", required = true)
          @JsonInclude
          @NotNull
          private String shopperStatement;
          /**
           * The ExternalReference is a reference set by the merchant for any purpose and does not need to be unique for every API call. For example, it can be used for invoice references, OCR numbers and also for offering end users the option to part-pay an invoice using the same ExternalReference. The ExternalReference will be included in version 1.2 of the settlement report, <code>ViewAutomaticSettlementDetailsCSV</code>.
           * <h2>Examples</h2>
           * <ul>
           *   <li>32423534523</li>
           * </ul>
           */
          @JsonProperty(value = "ExternalReference")
          private String externalReference;
          /**
           * Human-readable identifier of the consumer-facing merchant (e.g. legal name or trade name)
           * Note: Mandatory attribute for Trustly Partners that are using Express Merchant Onboarding (EMO) and aggregate traffic under a master processing account.  It is also mandatory for E-wallets used directly in a merchant's checkout.
           * Mandatory attribute for Trustly Partners that are using Express Merchant Onboarding and aggregate traffic under a master processing account.  It is also mandatory for E-wallets used directly in a merchant's checkout, whereby the purpose of a Trustly transaction is to pay for goods/services by placing funds on the payer's e-money account ("funding stage") following an immediate transfer into the e-money account of the payee ( "payment" stage).
           */
          @JsonProperty(value = "PSPMerchant")
          private String pspMerchant;
          /**
           * URL of the consumer-facing website where the order is initiated
           * Note: Mandatory attribute for Trustly Partners that are using Express Merchant Onboarding (EMO) and aggregate traffic under a master processing account.  It is also mandatory for E-wallets used directly in a merchant's checkout.
           * Mandatory attributes for Trustly Partners that are using Express Merchant Onboarding and aggregate traffic under a master processing account.  It is also mandatory for E-wallets used directly in a merchant's checkout, whereby the purpose of a Trustly transaction is to pay for goods/services by placing funds on the payer's e-money account ("funding stage") following an immediate transfer into the e-money account of the payee ( "payment" stage).
           */
          @JsonProperty(value = "PSPMerchantURL")
          private String pspMerchantUrl;
          /**
           * VISA category codes describing the merchant's nature of business.
           * Note: Mandatory attribute for Trustly Partners that are using Express Merchant Onboarding (EMO) and aggregate traffic under a master processing account.  It is also mandatory for E-wallets used directly in a merchant's checkout.
           * Mandatory attributes for Trustly Partners that are using Express account. It is also mandatory for E-wallets used directly in a merchant's checkout, whereby the purpose of a Trustly transaction is to pay for goods/services by placing funds on the payer's e-money account ("funding stage") following an immediate transfer into the e-money account of the payee ( "payment" stage).
           */
          @JsonProperty(value = "MerchantCategoryCode")
          private String merchantCategoryCode;
          /**
           * Information about the Payer (ultimate debtor). This is required for some merchants and partners. SenderInformation is mandatory to send in Attributes{} for money transfer services (including remittance houses), e-wallets, prepaid cards, as well as for Trustly Partners that are using Express Merchant Onboarding and aggregate traffic under a master processing account (other cases may also apply).
           */
          @JsonProperty(value = "SenderInformation")
          @Valid
          private SenderInformation senderInformation;
        }
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class AccountPayoutResponse extends JsonRpcResponse<AccountPayoutResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<Result.Data> {
      public Result() {
        super.method = "AccountPayout";
      }

      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data {
        /**
         * The globally unique OrderID the account payout order was assigned in our system.
         */
        @JsonProperty(value = "orderid", required = true)
        @JsonInclude
        @NotNull
        private long orderID;
        /**
         * "1" if the payout could be accepted and "0" otherwise.
         */
        @JsonProperty(value = "result", required = true)
        @JsonInclude
        @NotNull
        private StringBoolean result;
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class AckData extends NotificationResponseDataBase<AckData.Status> implements IAdditionalProperties {
    @Singular
    @JsonAnySetter
    private Map<String, Object> additionalProperties;

    public void addAdditionalProperty(String key, Object value) {
      this.additionalProperties.put(key, value);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
      return this.additionalProperties;
    }

    public enum Status {
      OK("OK");

      @JsonValue
      private final String value;

      Status(String value) {
        this.value = value;
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class AnyAttributes extends AbstractRequestDataAttributes {

  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class ApproveWithdrawalRequest extends JsonRpcRequest<ApproveWithdrawalRequest.Params> {
    public ApproveWithdrawalRequest() {
      super("ApproveWithdrawal");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcRequestParams<Params.Data> {
      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends AbstractRequestData<AnyAttributes> {
        /**
         * The globally unique OrderID the charge order was assigned in our system. The order has no end-user interaction; it is merely used as a reference for the notifications delivered regarding the charge.
         * <h2>Examples</h2>
         * <ul>
         *   <li>9594811343</li>
         * </ul>
         */
        @JsonProperty(value = "OrderID", required = true)
        @JsonInclude
        @NotNull
        private long orderId;
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class ApproveWithdrawalResponse extends JsonRpcResponse<ApproveWithdrawalResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<Result.Data> {
      public Result() {
        super.method = "ApproveWithdrawal";
      }

      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data {
        /**
         * The OrderID specified when calling the method.
         */
        @JsonProperty(value = "orderid", required = true)
        @JsonInclude
        @NotNull
        private long orderID;
        /**
         * 1 if the withdrawal could be approved and 0 otherwise.
         */
        @JsonProperty(value = "result", required = true)
        @JsonInclude
        @NotNull
        private StringBoolean result;
      }
    }
  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class BalanceRequest extends JsonRpcRequest<BalanceRequest.Params> {
    public BalanceRequest() {
      super("Balance");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcRequestParams<Params.Data> {
      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends AbstractRequestData<AnyAttributes> {

      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class BalanceResponse extends JsonRpcResponse<BalanceResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<List<Result.DataEntry>> {
      public Result() {
        super.method = "Balance";
      }

      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class DataEntry {
        /**
         * The currency
         */
        @JsonProperty(value = "currency")
        private String currency;
        /**
         * The balance with 2 decimals
         */
        @JsonProperty(value = "balance")
        private String balance;
      }
    }
  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class CancelChargeRequest extends JsonRpcRequest<CancelChargeRequest.Params> {
    public CancelChargeRequest() {
      super("CancelCharge");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcRequestParams<Params.Data> {
      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends AbstractRequestData<AnyAttributes> {
        /**
         * The OrderID of the Charge request that should be canceled.
         */
        @JsonProperty(value = "OrderID", required = true)
        @JsonInclude
        @NotNull
        private String orderId;
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class CancelChargeResponse extends JsonRpcResponse<CancelChargeResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<Result.Data> {
      public Result() {
        super.method = "CancelCharge";
      }

      /**
       * 1 if the Charge could be canceled, and 0 otherwise.
       */
      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends WithRejection<StringBoolean, String> {
        /**
         * The globally unique OrderID the charge order was assigned in our system. The order has no end-user interaction; it is merely used as a reference for the notifications delivered regarding the request.
         * <h2>Examples</h2>
         * <ul>
         *   <li>9594811343</li>
         * </ul>
         */
        @JsonProperty(value = "orderid", required = true)
        @JsonInclude
        @NotNull
        private String orderID;
      }
    }
  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class CancelDirectCreditNotification extends JsonRpcNotification<CancelDirectCreditNotification.Params> {
    public CancelDirectCreditNotification() {
      super("cancel");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcNotificationParams<Params.Data> {
      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends AbstractCancelNotificationData<Data.Attributes> {
        @Getter
        @Jacksonized
        @RequiredArgsConstructor
        @Setter
        @SuperBuilder
        public static class Attributes extends AbstractRequestDataAttributes {
          /**
           * From CancelDirectCreditNotificationDataAttributes
           */
          @JsonProperty(value = "reason")
          private DirectCreditCancelReason reason;
          /**
           * Description of reason. If Direct Debit Mandate, then  this applies when reason is <code>FAILED: BACS ADDACS_1(INSTRUCTION CANCELLED BY PAYER)</code>. See https://eu.developers.trustly.com/doc/reference/mdd#description-of-details-eg-failure-details
           */
          @JsonProperty(value = "details")
          private String details;

          public enum DirectCreditCancelReason {
            /**
             * The specified account is not valid, eg sending funds to it is not possible.
             */
            INVALID_ACCOUNT("INVALID_ACCOUNT"),
            /**
             * If the payment was cancelled via the api
             */
            CANCELLED("CANCELLED"),
            /**
             * All other failures
             */
            FAILED("FAILED");

            @JsonValue
            private final String value;

            DirectCreditCancelReason(String value) {
              this.value = value;
            }
          }
        }
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class CancelDirectCreditNotificationResponse extends JsonRpcResponse<CancelDirectCreditNotificationResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<AckData> {
      public Result() {
        super.method = "cancel";
      }
    }
  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class CancelDirectDebitMandateRequest extends JsonRpcRequest<CancelDirectDebitMandateRequest.Params> {
    public CancelDirectDebitMandateRequest() {
      super("CancelDirectDebitMandate");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcRequestParams<Params.Data> {
      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends AbstractRequestData<AnyAttributes> {
        /**
         * The globally unique OrderID the charge order was assigned in our system. The order has no end-user interaction; it is merely used as a reference for the notifications delivered regarding the request.
         * <h2>Examples</h2>
         * <ul>
         *   <li>9594811343</li>
         * </ul>
         */
        @JsonProperty(value = "OrderID", required = true)
        @JsonInclude
        @NotNull
        private String orderId;
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class CancelDirectDebitMandateResponse extends JsonRpcResponse<CancelDirectDebitMandateResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<Result.Data> {
      public Result() {
        super.method = "CancelDirectDebitMandate";
      }

      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends WithRejection<StringBoolean, Data.CancelDirectDebitMandateReject> {
        /**
         * If the cancel was NOT accepted, a textual code describing the rejection reason, null otherwise.
         */
        public enum CancelDirectDebitMandateReject {
          /**
           * The mandate does not exist.
           */
          ERROR_MANDATE_NOT_FOUND("ERROR_MANDATE_NOT_FOUND");

          @JsonValue
          private final String value;

          CancelDirectDebitMandateReject(String value) {
            this.value = value;
          }
        }
      }
    }
  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class CancelDirectDebitNotification extends JsonRpcNotification<CancelDirectDebitNotification.Params> {
    public CancelDirectDebitNotification() {
      super("cancel");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcNotificationParams<Params.Data> {
      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends AbstractCancelNotificationData<Data.Attributes> {
        @Getter
        @Jacksonized
        @RequiredArgsConstructor
        @Setter
        @SuperBuilder
        public static class Attributes extends AbstractRequestDataAttributes {
          /**
           * From CancelDirectDebitNotificationDataAttributes
           */
          @JsonProperty(value = "reason")
          private DirectDebitCancelReason reason;
          /**
           * Description of reason. If Direct Debit Mandate, then  this applies when reason is <code>FAILED: BACS ADDACS_1(INSTRUCTION CANCELLED BY PAYER)</code>. See https://eu.developers.trustly.com/doc/reference/mdd#description-of-details-eg-failure-details
           */
          @JsonProperty(value = "details")
          private String details;

          public enum DirectDebitCancelReason {
            /**
             * The mandate is invalid for some reason.
             */
            ERROR_MANDATE_INVALID("ERROR_MANDATE_INVALID"),
            /**
             * Ie. missing funds or being canceled by the end-user.
             */
            ERROR_CHARGE_NOT_APPROVED("ERROR_CHARGE_NOT_APPROVED"),
            /**
             * If the payment was canceled from the api
             */
            CANCELLED("CANCELLED"),
            /**
             * All other failures
             */
            FAILED("FAILED");

            @JsonValue
            private final String value;

            DirectDebitCancelReason(String value) {
              this.value = value;
            }
          }
        }
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class CancelDirectDebitNotificationResponse extends JsonRpcResponse<CancelDirectDebitNotificationResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<AckData> {
      public Result() {
        super.method = "cancel";
      }
    }
  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class CancelDirectDebitRequest extends JsonRpcRequest<CancelDirectDebitRequest.Params> {
    public CancelDirectDebitRequest() {
      super("CancelDirectDebit");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcRequestParams<Params.Data> {
      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends AbstractRequestData<AnyAttributes> {
        /**
         * The globally unique OrderID the charge order was assigned in our system. The order has no end-user interaction; it is merely used as a reference for the notifications delivered regarding the request.
         * <h2>Examples</h2>
         * <ul>
         *   <li>9594811343</li>
         * </ul>
         */
        @JsonProperty(value = "OrderID", required = true)
        @JsonInclude
        @NotNull
        private String orderId;
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class CancelDirectDebitResponse extends JsonRpcResponse<CancelDirectDebitResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<Result.Data> {
      public Result() {
        super.method = "CancelDirectDebit";
      }

      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends WithRejection<StringBoolean, Data.CancelDirectDebitReject> {
        /**
         * If the cancel was NOT accepted, a textual code describing the rejection reason, null otherwise.
         */
        public enum CancelDirectDebitReject {
          /**
           * the OrderId does not exist.
           */
          ERROR_CHARGE_NOT_FOUND("ERROR_CHARGE_NOT_FOUND"),
          /**
           * the charge has already been processed in the scheme (e.g. BACS) and can not be cancelled.
           */
          ERROR_CHARGE_ALREADY_PROCESSED("ERROR_CHARGE_ALREADY_PROCESSED");

          @JsonValue
          private final String value;

          CancelDirectDebitReject(String value) {
            this.value = value;
          }
        }
      }
    }
  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class CancelDirectPaymentBatchNotification extends JsonRpcNotification<CancelDirectPaymentBatchNotification.Params> {
    public CancelDirectPaymentBatchNotification() {
      super("cancel");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcNotificationParams<Params.Data> {
      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends AbstractCancelNotificationData<AnyAttributes> {

      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class CancelDirectPaymentBatchNotificationResponse extends JsonRpcResponse<CancelDirectPaymentBatchNotificationResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<AckData> {
      public Result() {
        super.method = "cancel";
      }
    }
  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class CancelMandateNotification extends JsonRpcNotification<CancelMandateNotification.Params> {
    public CancelMandateNotification() {
      super("cancel");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcNotificationParams<Params.Data> {
      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends AbstractCancelNotificationData<Data.Attributes> {
        @Getter
        @Jacksonized
        @RequiredArgsConstructor
        @Setter
        @SuperBuilder
        public static class Attributes extends AbstractRequestDataAttributes {
          /**
           * From CancelMandateNotificationDataAttributes
           */
          @JsonProperty(value = "reason")
          private CancelReason reason;
          /**
           * Description of reason. If Direct Debit Mandate, then  this applies when reason is <code>FAILED: BACS ADDACS_1(INSTRUCTION CANCELLED BY PAYER)</code>. See https://eu.developers.trustly.com/doc/reference/mdd#description-of-details-eg-failure-details
           */
          @JsonProperty(value = "details")
          private String details;
          /**
           * The globally unique AccountID the account was assigned in our system. The AccountID of a returning customer. Allows for a quicker payment experience in some markets, see Trustly Express.
           * <h2>Examples</h2>
           * <ul>
           *   <li>1234567890</li>
           *   <li>7653385737</li>
           * </ul>
           */
          @JsonProperty(value = "accountid")
          private String accountID;
          /**
           * This parameter in a way identifies the mandate you are to setup. If it's already used, you will receive an error, ERROR_MERCHANT_REFERENCE_ALREADY_EXISTS
           * which basically informs you that there's already a mandate with that reference.
           * <p>
           * [BACS]: The unique mandate reference. 6 - 10 characters consisting of A-Z and 0-9. Can not begin with DDIC and neither consists of the same characters, eg. AAAAAAA.
           * [Bankgiro]: The unique mandate reference. This must be numeric and unique for the payer, eg nationalId or similar can be used. Format needs to follow regexp [1-9][0-9]{5-15}
           * [SEPA-DD]: The unique mandate reference. This must be unique for the end-user for you as a merchant. Format needs to follow regexp [0-9,a-z,A-Z]{10-35}
           * <h2>Examples</h2>
           * <ul>
           *   <li>123ABC0123</li>
           * </ul>
           */
          @JsonProperty(value = "merchantreference")
          private String merchantreference;

          public enum CancelReason {
            /**
             * The mandate already exists
             */
            MANDATE_ALREADY_EXISTS("MANDATE_ALREADY_EXISTS"),
            /**
             * Either when the consumer cancels in the journey or if you call cancel mandate method.
             */
            CANCELLED("CANCELLED"),
            /**
             * If the underlying scheme reports failure or cancellation of the mandate
             */
            FAILED("FAILED"),
            /**
             * If the provided accountId was incorrect
             */
            INVALID_ACCOUNT_ID("INVALID_ACCOUNT_ID");

            @JsonValue
            private final String value;

            CancelReason(String value) {
              this.value = value;
            }
          }
        }
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class CancelMandateNotificationResponse extends JsonRpcResponse<CancelMandateNotificationResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<AckData> {
      public Result() {
        super.method = "cancel";
      }
    }
  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class CancelNotification extends JsonRpcNotification<CancelNotification.Params> {
    public CancelNotification() {
      super("cancel");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcNotificationParams<Params.Data> {
      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends AbstractCancelNotificationData<Data.NotificationDataAttributes> {
        /**
         * ID, username, hash or anything uniquely identifying the end-user requesting the deposit.
         * Preferably the same ID/username as used in the merchant's own backoffice in order to simplify for the merchant's support department.
         */
        @JsonProperty(value = "enduserid")
        private String endUserID;
        /**
         * The time of the transaction and the GMT offset (+01 means GMT + 1 hours).
         * <h2>Examples</h2>
         * <ul>
         *   <li>2014-01-30 13:28:45.652299+01</li>
         *   <li>2014-03-31 11:50:06.46106+00</li>
         * </ul>
         */
        @JsonProperty(value = "timestamp")
        private String timestamp;
        /**
         * Flag indicating that this is for Direct Debit refund. Note that this flag is not sent unless it's for a refund. Only value will be 1.
         * From CancelRefundDirectDebitNotificationData
         * <p>
         * Flag indicating that this is for Direct Debit refund. Note that this flag is not sent unless it's for a refund. Only value will be 1.
         */
        @JsonProperty(value = "refund")
        private String refund;

        @Getter
        @Jacksonized
        @RequiredArgsConstructor
        @Setter
        @SuperBuilder
        public static class NotificationDataAttributes extends AbstractRequestDataAttributes {
          /**
           * Description of reason
           * <h2>Examples</h2>
           * <ul>
           *   <li>User Declined</li>
           * </ul>
           */
          @JsonProperty(value = "details")
          private String details;
          /**
           * From CancelMandateNotificationDataAttributes
           */
          @JsonProperty(value = "reason")
          private CancelReason reason;
          /**
           * The globally unique AccountID the account was assigned in our system. The AccountID of a returning customer. Allows for a quicker payment experience in some markets, see Trustly Express.
           * From CancelMandateNotificationDataAttributes
           * <p>
           * The globally unique AccountID the account was assigned in our system. The AccountID of a returning customer. Allows for a quicker payment experience in some markets, see Trustly Express.
           * <h2>Examples</h2>
           * <ul>
           *   <li>1234567890</li>
           *   <li>7653385737</li>
           * </ul>
           */
          @JsonProperty(value = "accountid")
          private String accountID;
          /**
           * This parameter in a way identifies the mandate you are to setup. If it's already used, you will receive an error, ERROR_MERCHANT_REFERENCE_ALREADY_EXISTS
           * which basically informs you that there's already a mandate with that reference.
           * <p>
           * [BACS]: The unique mandate reference. 6 - 10 characters consisting of A-Z and 0-9. Can not begin with DDIC and neither consists of the same characters, eg. AAAAAAA.
           * [Bankgiro]: The unique mandate reference. This must be numeric and unique for the payer, eg nationalId or similar can be used. Format needs to follow regexp [1-9][0-9]{5-15}
           * [SEPA-DD]: The unique mandate reference. This must be unique for the end-user for you as a merchant. Format needs to follow regexp [0-9,a-z,A-Z]{10-35}
           * From CancelMandateNotificationDataAttributes
           * <p>
           * This parameter in a way identifies the mandate you are to setup. If it's already used, you will receive an error, ERROR_MERCHANT_REFERENCE_ALREADY_EXISTS
           * which basically informs you that there's already a mandate with that reference.
           * <p>
           * [BACS]: The unique mandate reference. 6 - 10 characters consisting of A-Z and 0-9. Can not begin with DDIC and neither consists of the same characters, eg. AAAAAAA.
           * [Bankgiro]: The unique mandate reference. This must be numeric and unique for the payer, eg nationalId or similar can be used. Format needs to follow regexp [1-9][0-9]{5-15}
           * [SEPA-DD]: The unique mandate reference. This must be unique for the end-user for you as a merchant. Format needs to follow regexp [0-9,a-z,A-Z]{10-35}
           * <h2>Examples</h2>
           * <ul>
           *   <li>123ABC0123</li>
           * </ul>
           */
          @JsonProperty(value = "merchantreference")
          private String merchantreference;

          public enum CancelReason {
            /**
             * The mandate already exists
             * From CancelMandateNotificationDataAttributesCancelReason
             */
            MANDATE_ALREADY_EXISTS("MANDATE_ALREADY_EXISTS"),
            /**
             * Either when the consumer cancels in the journey or if you call cancel mandate method.
             * From CancelMandateNotificationDataAttributesCancelReason
             */
            CANCELLED("CANCELLED"),
            /**
             * If the underlying scheme reports failure or cancellation of the mandate
             * From CancelMandateNotificationDataAttributesCancelReason
             */
            FAILED("FAILED"),
            /**
             * If the provided accountId was incorrect
             * From CancelMandateNotificationDataAttributesCancelReason
             */
            INVALID_ACCOUNT_ID("INVALID_ACCOUNT_ID"),
            /**
             * The specified account is not valid, eg sending funds to it is not possible.
             * From RefundDirectDebitCancelReason
             */
            INVALID_ACCOUNT("INVALID_ACCOUNT"),
            /**
             * The mandate is invalid for some reason.
             * From DirectDebitCancelReason
             */
            ERROR_MANDATE_INVALID("ERROR_MANDATE_INVALID"),
            /**
             * Ie. missing funds or being canceled by the end-user.
             * From DirectDebitCancelReason
             */
            ERROR_CHARGE_NOT_APPROVED("ERROR_CHARGE_NOT_APPROVED"),
            /**
             * The request was declined
             * From SwishCancelReason
             */
            DECLINED("DECLINED"),
            /**
             * The request was cancelled
             * From SwishCancelReason
             */
            CANCELED("CANCELED"),
            /**
             * There was an error during the request
             * From SwishCancelReason
             */
            ERROR("ERROR");

            @JsonValue
            private final String value;

            CancelReason(String value) {
              this.value = value;
            }
          }
        }
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class CancelNotificationResponse extends JsonRpcResponse<CancelNotificationResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<AckData> {
      public Result() {
        super.method = "cancel";
      }
    }
  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class CancelRefundDirectDebitNotification extends JsonRpcNotification<CancelRefundDirectDebitNotification.Params> {
    public CancelRefundDirectDebitNotification() {
      super("cancel");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcNotificationParams<Params.Data> {
      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends AbstractCancelNotificationData<Data.Attributes> {
        /**
         * Flag indicating that this is for Direct Debit refund. Note that this flag is not sent unless it's for a refund. Only value will be 1.
         */
        @JsonProperty(value = "refund")
        public String getRefund() {
          return "1";
        }

        @Getter
        @Jacksonized
        @RequiredArgsConstructor
        @Setter
        @SuperBuilder
        public static class Attributes extends AbstractRequestDataAttributes {
          /**
           * From CancelRefundDirectDebitNotificationDataAttributes
           */
          @JsonProperty(value = "reason")
          private RefundDirectDebitCancelReason reason;
          /**
           * Description of reason. If Direct Debit Mandate, then  this applies when reason is <code>FAILED: BACS ADDACS_1(INSTRUCTION CANCELLED BY PAYER)</code>. See https://eu.developers.trustly.com/doc/reference/mdd#description-of-details-eg-failure-details
           */
          @JsonProperty(value = "details")
          private String details;

          public enum RefundDirectDebitCancelReason {
            /**
             * The specified account is not valid, eg sending funds to it is not possible.
             */
            INVALID_ACCOUNT("INVALID_ACCOUNT"),
            /**
             * All other failures
             */
            FAILED("FAILED");

            @JsonValue
            private final String value;

            RefundDirectDebitCancelReason(String value) {
              this.value = value;
            }
          }
        }
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class CancelRefundDirectDebitNotificationResponse extends JsonRpcResponse<CancelRefundDirectDebitNotificationResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<AckData> {
      public Result() {
        super.method = "cancel";
      }
    }
  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class CancelSwishNotification extends JsonRpcNotification<CancelSwishNotification.Params> {
    public CancelSwishNotification() {
      super("cancel");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcNotificationParams<Params.Data> {
      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends AbstractCancelNotificationData<Data.Attributes> {
        /**
         * The time of the transaction and the GMT offset (+01 means GMT + 1 hours).
         * <h2>Examples</h2>
         * <ul>
         *   <li>2014-01-30 13:28:45.652299+01</li>
         *   <li>2014-03-31 11:50:06.46106+00</li>
         * </ul>
         */
        @JsonProperty(value = "timestamp")
        private String timestamp;

        @Getter
        @Jacksonized
        @RequiredArgsConstructor
        @Setter
        @SuperBuilder
        public static class Attributes extends AbstractRequestDataAttributes {
          @JsonProperty(value = "reason")
          private SwishCancelReason reason;
          /**
           * Description of reason
           * <h2>Examples</h2>
           * <ul>
           *   <li>User Declined</li>
           * </ul>
           */
          @JsonProperty(value = "details")
          private String details;

          public enum SwishCancelReason {
            /**
             * The request was declined
             */
            DECLINED("DECLINED"),
            /**
             * The request was cancelled
             */
            CANCELED("CANCELED"),
            /**
             * There was an error during the request
             */
            ERROR("ERROR");

            @JsonValue
            private final String value;

            SwishCancelReason(String value) {
              this.value = value;
            }
          }
        }
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class CancelSwishNotificationResponse extends JsonRpcResponse<CancelSwishNotificationResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<AckData> {
      public Result() {
        super.method = "cancel";
      }
    }
  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class ChargeRequest extends JsonRpcRequest<ChargeRequest.Params> {
    public ChargeRequest() {
      super("Charge");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcRequestParams<Params.Data> {
      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends AbstractRequestData<Data.Attributes> {
        /**
         * The globally unique AccountID the account was assigned in our system. The AccountID of a returning customer. Allows for a quicker payment experience in some markets, see Trustly Express.
         * <h2>Examples</h2>
         * <ul>
         *   <li>1234567890</li>
         *   <li>7653385737</li>
         * </ul>
         */
        @JsonProperty(value = "AccountID", required = true)
        @JsonInclude
        @NotNull
        private String accountId;
        /**
         * The URL to which notifications for this should be sent to. This URL should be hard to guess and not contain a ? ("question mark").
         * <h2>Examples</h2>
         * <ul>
         *   <li>https://example.com/trustly/notification/a2b63j23dj23883jhfhfh</li>
         * </ul>
         */
        @JsonProperty(value = "NotificationURL", required = true)
        @JsonInclude
        @NotNull
        private String notificationUrl;
        /**
         * ID, username, hash or anything uniquely identifying the end-user requesting the deposit.
         * Preferably the same ID/username as used in the merchant's own backoffice in order to simplify for the merchant's support department.
         */
        @JsonProperty(value = "EndUserID", required = true)
        @JsonInclude
        @NotNull
        private String endUserId;
        /**
         * Your unique ID of the transaction.
         * <h2>Examples</h2>
         * <ul>
         *   <li>12345678</li>
         * </ul>
         */
        @JsonProperty(value = "MessageID", required = true)
        @JsonInclude
        @NotNull
        private String messageId;
        /**
         * <h2>Examples</h2>
         * <ul>
         *   <li>BGN: 100.00</li>
         *   <li>CZK: 100.00</li>
         *   <li>DKK: 100.00</li>
         *   <li>EUR: 100.00</li>
         *   <li>GBP: 100.00</li>
         *   <li>HRK: 100.00</li>
         *   <li>HUF: 100</li>
         *   <li>NOK: 100.00</li>
         *   <li>PLN: 100.00</li>
         *   <li>RON: 100.00</li>
         *   <li>SEK: 100.00</li>
         * </ul>
         */
        @JsonProperty(value = "Amount", required = true)
        @JsonInclude
        @NotNull
        private String amount;
        /**
         * The ISO 4217 code of the currency. See <a href="https://eu.developers.trustly.com/doc/reference/handling-currencies">documentation</a>
         * <h2>Examples</h2>
         * <ul>
         *   <li>BGN</li>
         *   <li>CZK</li>
         *   <li>DKK</li>
         *   <li>EUR</li>
         *   <li>GBP</li>
         *   <li>HRK</li>
         *   <li>HUF</li>
         *   <li>NOK</li>
         *   <li>PLN</li>
         *   <li>RON</li>
         *   <li>SEK</li>
         * </ul>
         */
        @JsonProperty(value = "Currency", required = true)
        @JsonInclude
        private String currency;

        @Getter
        @Jacksonized
        @RequiredArgsConstructor
        @Setter
        @SuperBuilder
        public static class Attributes extends AbstractRequestDataAttributes {
          /**
           * The text to show on the end-user's bank statement after Trustly's own 10 digit reference (which always will be displayed first). The reference must let the end user identify the merchant based on this value. So the ShopperStatement should contain either your brand name, website name, or company name.
           * <p>
           * If possible, try to keep this text as short as possible to maximise the chance that the full reference will fit into the reference field on the customer's bank since some banks allow only a limited number of characters. If the full ShopperStatement does not fit into the reference it will be truncated from the end.
           */
          @JsonProperty(value = "ShopperStatement", required = true)
          @JsonInclude
          @NotNull
          private String shopperStatement;
          /**
           * The email address of the end user.
           * <h2>Examples</h2>
           * <ul>
           *   <li>test@trustly.com</li>
           * </ul>
           */
          @JsonProperty(value = "Email", required = true)
          @JsonInclude
          @NotNull
          private String email;
          /**
           * The date when the funds will be charged from the end user's bank account. If this attribute is not sent, the charge will be attempted as soon as possible.
           */
          @JsonProperty(value = "PaymentDate")
          private String paymentDate;
          /**
           * The ExternalReference is a reference set by the merchant for any purpose and does not need to be unique for every API call. For example, it can be used for invoice references, OCR numbers and also for offering end users the option to part-pay an invoice using the same ExternalReference. The ExternalReference will be included in version 1.2 of the settlement report, <code>ViewAutomaticSettlementDetailsCSV</code>.
           * <h2>Examples</h2>
           * <ul>
           *   <li>32423534523</li>
           * </ul>
           */
          @JsonProperty(value = "ExternalReference")
          private String externalReference;
          /**
           * Human-readable identifier of the consumer-facing merchant (e.g. legal name or trade name)
           * Note: Mandatory attribute for Trustly Partners that are using Express Merchant Onboarding (EMO) and aggregate traffic under a master processing account.  It is also mandatory for E-wallets used directly in a merchant's checkout.
           * Mandatory attribute for Trustly Partners that are using Express Merchant Onboarding and aggregate traffic under a master processing account.  It is also mandatory for E-wallets used directly in a merchant's checkout, whereby the purpose of a Trustly transaction is to pay for goods/services by placing funds on the payer's e-money account ("funding stage") following an immediate transfer into the e-money account of the payee ( "payment" stage).
           */
          @JsonProperty(value = "PSPMerchant")
          private String pspMerchant;
          /**
           * URL of the consumer-facing website where the order is initiated
           * Note: Mandatory attribute for Trustly Partners that are using Express Merchant Onboarding (EMO) and aggregate traffic under a master processing account.  It is also mandatory for E-wallets used directly in a merchant's checkout.
           * Mandatory attributes for Trustly Partners that are using Express Merchant Onboarding and aggregate traffic under a master processing account.  It is also mandatory for E-wallets used directly in a merchant's checkout, whereby the purpose of a Trustly transaction is to pay for goods/services by placing funds on the payer's e-money account ("funding stage") following an immediate transfer into the e-money account of the payee ( "payment" stage).
           */
          @JsonProperty(value = "PSPMerchantURL")
          private String pspMerchantUrl;
          /**
           * VISA category codes describing the merchant's nature of business.
           * Note: Mandatory attribute for Trustly Partners that are using Express Merchant Onboarding (EMO) and aggregate traffic under a master processing account.  It is also mandatory for E-wallets used directly in a merchant's checkout.
           * Mandatory attributes for Trustly Partners that are using Express account. It is also mandatory for E-wallets used directly in a merchant's checkout, whereby the purpose of a Trustly transaction is to pay for goods/services by placing funds on the payer's e-money account ("funding stage") following an immediate transfer into the e-money account of the payee ( "payment" stage).
           */
          @JsonProperty(value = "MerchantCategoryCode")
          private String merchantCategoryCode;
        }
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class ChargeResponse extends JsonRpcResponse<ChargeResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<Result.Data> {
      public Result() {
        super.method = "Charge";
      }

      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends WithRejection<StringBoolean, String> {
        @JsonProperty(value = "orderid")
        private String orderID;
      }
    }
  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class CreateAccountRequest extends JsonRpcRequest<CreateAccountRequest.Params> {
    public CreateAccountRequest() {
      super("CreateAccount");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcRequestParams<Params.Data> {
      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends AbstractRequestData<Data.Attributes> {
        /**
         * ID, username, hash or anything uniquely identifying the end-user holding this account. Preferably the same ID/username as used in the merchant's own backoffice in order to simplify for the merchant's support department.
         */
        @JsonProperty(value = "EndUserID", required = true)
        @JsonInclude
        @NotNull
        private String endUserId;
        /**
         * The clearing house of the end-user's bank account. Typically the name of a country in uppercase letters. See examples or table at https://developers.trustly.com/emea/docs/registeraccount.
         * <h2>Examples</h2>
         * <ul>
         *   <li>AUSTRIA</li>
         *   <li>BELGIUM</li>
         *   <li>BULGARIA</li>
         *   <li>CROATIA</li>
         *   <li>CYPRUS</li>
         *   <li>CZECH_REPUBLIC</li>
         *   <li>DENMARK</li>
         *   <li>ESTONIA</li>
         *   <li>FINLAND</li>
         *   <li>FRANCE</li>
         *   <li>GERMANY</li>
         *   <li>GREECE</li>
         *   <li>HUNGARY</li>
         *   <li>IRELAND</li>
         *   <li>ITALY</li>
         *   <li>LATVIA</li>
         *   <li>LITHUANIA</li>
         *   <li>LUXEMBOURG</li>
         *   <li>MALTA</li>
         *   <li>NETHERLANDS</li>
         *   <li>NORWAY</li>
         *   <li>POLAND</li>
         *   <li>PORTUGAL</li>
         *   <li>ROMANIA</li>
         *   <li>SLOVAKIA</li>
         *   <li>SLOVENIA</li>
         *   <li>SPAIN</li>
         *   <li>SWEDEN</li>
         *   <li>UNITED_KINGDOM</li>
         * </ul>
         */
        @JsonProperty(value = "ClearingHouse", required = true)
        @JsonInclude
        @NotNull
        private String clearingHouse;
        /**
         * The bank number identifying the end-user's bank in the given clearing house. For bank accounts in IBAN format you should just provide an empty string (""). For non-IBAN format, see examples. The BankNumber for Swedish bank accounts should be the local "clearing number", and the AccountNumber parameter should contain the rest of the account number. Most Swedish banks have a 4-digit clearing number, but a 5-digit clearing number is used for Swedbank accounts when the clearing number starts with "8". Nordea accounts where the account number is the same as the person's national identification number always has "3300" as the clearing number.
         * <p>
         * IBAN for Swedish bank accounts is supported upon request. When making API calls with Swedish IBAN, ensure to include the Clearinghouse attribute as "IBAN" instead of "SWEDEN". See more at https://developers.trustly.com/emea/docs/registeraccount
         * <h2>Examples</h2>
         * <ul>
         *   <li>Sweden: ^[0-9]{4,5}$</li>
         *   <li>United Kingdom: ^[0-9]{6}$</li>
         * </ul>
         */
        @JsonProperty(value = "BankNumber", required = true)
        @JsonInclude
        @NotNull
        private String bankNumber;
        /**
         * The account number, identifying the end-user's account in the bank. Can be either IBAN or country-specific format, see examples or read more at https://developers.trustly.com/emea/docs/registeraccount
         * <h2>Examples</h2>
         * <ul>
         *   <li>6112</li>
         *   <li>391124057</li>
         *   <li>AUSTRIA: ^AT[0-9]{18}$</li>
         *   <li>BELGIUM: ^BE[0-9]{14}$</li>
         *   <li>BULGARIA: ^BG[0-9]{2}[A-Z]{4}[0-9]{4}[0-9]{2}[A-Z0-9]{8}$</li>
         *   <li>CROATIA: ^HR[0-9]{2}[0-9]{7}[0-9]{10}$</li>
         *   <li>CYPRUS: ^CY[0-9]{10}[0-9A-Z]{16}$</li>
         *   <li>CZECH_REPUBLIC: ^CZ[0-9]{22}$</li>
         *   <li>DENMARK: ^DK[0-9]{16}$</li>
         *   <li>ESTONIA: ^EE[0-9]{18}$</li>
         *   <li>FINLAND: ^FI[0-9]{16}$</li>
         *   <li>FRANCE: ^FR[0-9]{12}[0-9A-Z]{11}[0-9]{2}$</li>
         *   <li>GERMANY: ^DE[0-9]{20}$</li>
         *   <li>GREECE: ^GR[0-9]{25}$</li>
         *   <li>HUNGARY: ^HU[0-9]{26}$</li>
         *   <li>IRELAND: ^IE[0-9]{2}[A-Z]{4}[0-9]{14}$</li>
         *   <li>ITALY: ^IT[0-9]{2}[A-Z][0-9]{10}[0-9A-Z]{12}$</li>
         *   <li>LATVIA: ^LV[0-9]{2}[A-Z]{4}[0-9A-Z]{13}$</li>
         *   <li>LITHUANIA: ^LT[0-9]{18}$</li>
         *   <li>LUXEMBOURG: ^LU[0-9]{18}$</li>
         *   <li>MALTA: ^MT[0-9]{2}[A-Z]{4}[0-9]{5}[0-9A-Z]{18}$</li>
         *   <li>NETHERLANDS: ^NL[0-9]{2}[A-Z]{4}[0-9]{10}$</li>
         *   <li>NORWAY: ^NO[0-9]{13}$</li>
         *   <li>POLAND: ^PL[0-9]{26}$</li>
         *   <li>PORTUGAL: ^PT[0-9]{23}$</li>
         *   <li>ROMANIA: ^RO[0-9]{2}[A-Z]{4}[0-9A-Z]{16}$</li>
         *   <li>SLOVAKIA: ^SK[0-9]{22}$</li>
         *   <li>SLOVENIA: ^SI56[0-9]{15}$</li>
         *   <li>SPAIN: ^ES[0-9]{22}$</li>
         *   <li>SWEDEN:* [0-9]{1,15}$</li>
         *   <li>UNITED_KINGDOM: ^[0-9]{8}$</li>
         * </ul>
         */
        @JsonProperty(value = "AccountNumber", required = true)
        @JsonInclude
        @NotNull
        private String accountNumber;
        /**
         * First name of the person, or the name of the organization/company.
         */
        @JsonProperty(value = "Firstname", required = true)
        @JsonInclude
        @NotNull
        private String firstname;
        /**
         * Last name of the person (NULL/empty for organization/company).
         */
        @JsonProperty(value = "Lastname", required = true)
        @JsonInclude
        private String lastname;

        @Getter
        @Jacksonized
        @RequiredArgsConstructor
        @Setter
        @SuperBuilder
        public static class Attributes extends AbstractRequestDataAttributes {
          /**
           * The end-user's date of birth.
           */
          @JsonProperty(value = "DateOfBirth")
          private String dateOfBirth;
          /**
           * The mobile phone number to the end-user in international format. This is used for KYC and AML routines.
           */
          @JsonProperty(value = "MobilePhone")
          private String mobilePhone;
          /**
           * The end-user's social security number / personal number / birth number / etc.  Useful for some banks for identifying transactions and KYC/AML. If a Swedish personid ("personnummer") is provided, it will be pre-filled when the user logs in to their bank.
           * <h2>Examples</h2>
           * <ul>
           *   <li>790131-1234</li>
           * </ul>
           */
          @JsonProperty(value = "NationalIdentificationNumber")
          private String nationalIdentificationNumber;
          /**
           * The ISO 3166-1-alpha-2 code of the recipient address country.
           */
          @JsonProperty(value = "AddressCountry")
          private String addressCountry;
          /**
           * The postalcode of the recipient address.
           */
          @JsonProperty(value = "AddressPostalCode")
          private String addressPostalCode;
          /**
           * The city of the recipient address.
           */
          @JsonProperty(value = "AddressCity")
          private String addressCity;
          /**
           * Recipient address street
           * <h2>Examples</h2>
           * <ul>
           *   <li>Main Street 1</li>
           * </ul>
           */
          @JsonProperty(value = "AddressLine1")
          private String addressLine1;
          /**
           * Additional address information of the recipient.
           */
          @JsonProperty(value = "AddressLine2")
          private String addressLine2;
          /**
           * The entire shipping address.
           * This attribute should only be used if you are unable to provide the shipping address information in the 5 separate properties: <code>AddressCountry, </code>AddressCity<code>, </code>AddressPostalCode<code>, </code>AddressLine1, <code>AddressLine2</code>
           * <h2>Examples</h2>
           * <ul>
           *   <li>Birgerstreet 14, SE-11411, Stockholm, Sweden</li>
           * </ul>
           */
          @JsonProperty(value = "Address")
          private String address;
          /**
           * The email address of the end user.
           * <h2>Examples</h2>
           * <ul>
           *   <li>test@trustly.com</li>
           * </ul>
           */
          @JsonProperty(value = "Email")
          private String email;
        }
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class CreateAccountResponse extends JsonRpcResponse<CreateAccountResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<Result.Data> {
      public Result() {
        super.method = "CreateAccount";
      }

      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data {
        /**
         * The globally unique AccountID the account was assigned in our system. The AccountID of a returning customer. Allows for a quicker payment experience in some markets, see Trustly Express.
         * <h2>Examples</h2>
         * <ul>
         *   <li>7653385737</li>
         * </ul>
         */
        @JsonProperty(value = "accountId")
        private int accountId;
        /**
         * The clearing house of the end-user's bank account. Typically the name of a country in uppercase letters. See examples or table at https://developers.trustly.com/emea/docs/registeraccount.
         * <h2>Examples</h2>
         * <ul>
         *   <li>AUSTRIA</li>
         *   <li>BELGIUM</li>
         *   <li>BULGARIA</li>
         *   <li>CROATIA</li>
         *   <li>CYPRUS</li>
         *   <li>CZECH_REPUBLIC</li>
         *   <li>DENMARK</li>
         *   <li>ESTONIA</li>
         *   <li>FINLAND</li>
         *   <li>FRANCE</li>
         *   <li>GERMANY</li>
         *   <li>GREECE</li>
         *   <li>HUNGARY</li>
         *   <li>IRELAND</li>
         *   <li>ITALY</li>
         *   <li>LATVIA</li>
         *   <li>LITHUANIA</li>
         *   <li>LUXEMBOURG</li>
         *   <li>MALTA</li>
         *   <li>NETHERLANDS</li>
         *   <li>NORWAY</li>
         *   <li>POLAND</li>
         *   <li>PORTUGAL</li>
         *   <li>ROMANIA</li>
         *   <li>SLOVAKIA</li>
         *   <li>SLOVENIA</li>
         *   <li>SPAIN</li>
         *   <li>SWEDEN</li>
         *   <li>UNITED_KINGDOM</li>
         * </ul>
         */
        @JsonProperty(value = "clearingHouse")
        private String clearingHouse;
        /**
         * The bank for this account
         * <h2>Examples</h2>
         * <ul>
         *   <li>SEB</li>
         *   <li>Skandiabanken</li>
         * </ul>
         */
        @JsonProperty(value = "bank")
        private String bank;
        /**
         * A text that is safe to show the enduser for identifying the account. Do not parse this text since it will be a different format for different accounts.
         * <h2>Examples</h2>
         * <ul>
         *   <li>***4057</li>
         * </ul>
         */
        @JsonProperty(value = "descriptor")
        private String descriptor;
      }
    }
  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class CreditDirectCreditNotification extends JsonRpcNotification<CreditDirectCreditNotification.Params> {
    public CreditDirectCreditNotification() {
      super("credit");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcNotificationParams<Params.Data> {
      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends AbstractCreditNotificationData<Data.Attributes> {
        @Getter
        @Jacksonized
        @RequiredArgsConstructor
        @Setter
        @SuperBuilder
        public static class Attributes extends AbstractCreditNotificationDataAttributes {
          /**
           * Payment reference, from the bank, of the payment that occurred based on the Payment request. Only available if status is PAID.
           * <h2>Examples</h2>
           * <ul>
           *   <li>1E2FC19E5E5E4E18916609B7F8911C12</li>
           *   <li>TRLY80494-1001</li>
           * </ul>
           */
          @JsonProperty(value = "reference")
          private String reference;
          /**
           * Statement that appear on the end users bank account
           * <h2>Examples</h2>
           * <ul>
           *   <li>TRLY80494-1001</li>
           * </ul>
           */
          @JsonProperty(value = "statement")
          private String statement;
        }
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class CreditDirectCreditNotificationResponse extends JsonRpcResponse<CreditDirectCreditNotificationResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<AckData> {
      public Result() {
        super.method = "credit";
      }
    }
  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class CreditDirectDebitNotification extends JsonRpcNotification<CreditDirectDebitNotification.Params> {
    public CreditDirectDebitNotification() {
      super("credit");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcNotificationParams<Params.Data> {
      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends AbstractCreditNotificationData<Data.Attributes> {
        /**
         * The globally unique AccountID the account was assigned in our system. The AccountID of a returning customer. Allows for a quicker payment experience in some markets, see Trustly Express.
         * <h2>Examples</h2>
         * <ul>
         *   <li>1234567890</li>
         *   <li>7653385737</li>
         * </ul>
         */
        @JsonProperty(value = "accountid")
        private String accountID;

        @Getter
        @Jacksonized
        @RequiredArgsConstructor
        @Setter
        @SuperBuilder
        public static class Attributes extends AbstractCreditNotificationDataAttributes {
          /**
           * Payment reference, from the bank, of the payment that occurred based on the Payment request. Only available if status is PAID.
           * <h2>Examples</h2>
           * <ul>
           *   <li>1E2FC19E5E5E4E18916609B7F8911C12</li>
           *   <li>TRLY80494-1001</li>
           * </ul>
           */
          @JsonProperty(value = "reference")
          private String reference;
          /**
           * Statement that appear on the end users bank account
           * <h2>Examples</h2>
           * <ul>
           *   <li>TRLY80494-1001</li>
           * </ul>
           */
          @JsonProperty(value = "statement")
          private String statement;
        }
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class CreditDirectDebitNotificationResponse extends JsonRpcResponse<CreditDirectDebitNotificationResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<AckData> {
      public Result() {
        super.method = "credit";
      }
    }
  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class CreditNotification extends JsonRpcNotification<CreditNotification.Params> {
    public CreditNotification() {
      super("credit");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcNotificationParams<Params.Data> {
      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends AbstractCreditNotificationData<Data.Attributes> {
        /**
         * The globally unique AccountID the account was assigned in our system. The AccountID of a returning customer. Allows for a quicker payment experience in some markets, see Trustly Express.
         * From CreditDirectDebitNotificationData
         * <p>
         * The globally unique AccountID the account was assigned in our system. The AccountID of a returning customer. Allows for a quicker payment experience in some markets, see Trustly Express.
         * <h2>Examples</h2>
         * <ul>
         *   <li>1234567890</li>
         *   <li>7653385737</li>
         * </ul>
         */
        @JsonProperty(value = "accountid")
        private String accountID;
        /**
         * Flag indicating that this is for Direct Debit refund. Note that this flag is not sent unless it's for a refund. Only value will be 1.
         * From CreditRefundDirectDebitNotificationData
         * <p>
         * Flag indicating that this is for Direct Debit refund. Note that this flag is not sent unless it's for a refund. Only value will be 1.
         */
        @JsonProperty(value = "refund")
        private String refund;

        @Getter
        @Jacksonized
        @RequiredArgsConstructor
        @Setter
        @SuperBuilder
        public static class Attributes extends AbstractCreditNotificationDataAttributes {
          /**
           * Payment reference, from the bank, of the payment that occurred based on the Payment request. Only available if status is PAID.
           * <h2>Examples</h2>
           * <ul>
           *   <li>1E2FC19E5E5E4E18916609B7F8911C12</li>
           *   <li>TRLY80494-1001</li>
           * </ul>
           */
          @JsonProperty(value = "reference")
          private String reference;
          /**
           * Statement that appear on the end users bank account
           * <h2>Examples</h2>
           * <ul>
           *   <li>TRLY80494-1001</li>
           * </ul>
           */
          @JsonProperty(value = "statement")
          private String statement;
          /**
           * From CreditRefundDirectDebitNotificationDataAttributes
           */
          @JsonProperty(value = "reason")
          private CreditRefundDirectDebitCancelReason reason;
          /**
           * Description of reason. If Direct Debit Mandate, then  this applies when reason is <code>FAILED: BACS ADDACS_1(INSTRUCTION CANCELLED BY PAYER)</code>. See https://eu.developers.trustly.com/doc/reference/mdd#description-of-details-eg-failure-details
           * From CreditRefundDirectDebitNotificationDataAttributes
           * <p>
           * Description of reason. If Direct Debit Mandate, then  this applies when reason is <code>FAILED: BACS ADDACS_1(INSTRUCTION CANCELLED BY PAYER)</code>. See https://eu.developers.trustly.com/doc/reference/mdd#description-of-details-eg-failure-details
           */
          @JsonProperty(value = "details")
          private String details;
          /**
           * Payment reference, from the bank, of the payment that occurred based on the Payment request. Only available if status is PAID.
           * From CreditSwishNotificationDataAttributes
           * <p>
           * Payment reference, from the bank, of the payment that occurred based on the Payment request. Only available if status is PAID.
           * <h2>Examples</h2>
           * <ul>
           *   <li>467123476</li>
           * </ul>
           */
          @JsonProperty(value = "payerAlias")
          private String payerAlias;
        }
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class CreditNotificationResponse extends JsonRpcResponse<CreditNotificationResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<AckData> {
      public Result() {
        super.method = "credit";
      }
    }
  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class CreditRefundDirectDebitNotification extends JsonRpcNotification<CreditRefundDirectDebitNotification.Params> {
    public CreditRefundDirectDebitNotification() {
      super("credit");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcNotificationParams<Params.Data> {
      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends AbstractCreditNotificationData<Data.Attributes> {
        /**
         * Flag indicating that this is for Direct Debit refund. Note that this flag is not sent unless it's for a refund. Only value will be 1.
         */
        @JsonProperty(value = "refund")
        public String getRefund() {
          return "1";
        }

        @Getter
        @Jacksonized
        @RequiredArgsConstructor
        @Setter
        @SuperBuilder
        public static class Attributes extends AbstractCreditNotificationDataAttributes {
          /**
           * Payment reference, from the bank, of the payment that occurred based on the Payment request. Only available if status is PAID.
           * <h2>Examples</h2>
           * <ul>
           *   <li>1E2FC19E5E5E4E18916609B7F8911C12</li>
           *   <li>TRLY80494-1001</li>
           * </ul>
           */
          @JsonProperty(value = "reference")
          private String reference;
          /**
           * Statement that appear on the end users bank account
           * <h2>Examples</h2>
           * <ul>
           *   <li>TRLY80494-1001</li>
           * </ul>
           */
          @JsonProperty(value = "statement")
          private String statement;
          /**
           * From CreditRefundDirectDebitNotificationDataAttributes
           */
          @JsonProperty(value = "reason")
          private CreditRefundDirectDebitCancelReason reason;
          /**
           * Description of reason. If Direct Debit Mandate, then  this applies when reason is <code>FAILED: BACS ADDACS_1(INSTRUCTION CANCELLED BY PAYER)</code>. See https://eu.developers.trustly.com/doc/reference/mdd#description-of-details-eg-failure-details
           */
          @JsonProperty(value = "details")
          private String details;
        }
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class CreditRefundDirectDebitNotificationResponse extends JsonRpcResponse<CreditRefundDirectDebitNotificationResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<AckData> {
      public Result() {
        super.method = "credit";
      }
    }
  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class CreditSwishNotification extends JsonRpcNotification<CreditSwishNotification.Params> {
    public CreditSwishNotification() {
      super("credit");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcNotificationParams<Params.Data> {
      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends AbstractCreditNotificationData<Data.Attributes> {
        @Getter
        @Jacksonized
        @RequiredArgsConstructor
        @Setter
        @SuperBuilder
        public static class Attributes extends AbstractCreditNotificationDataAttributes {
          /**
           * Payment reference, from the bank, of the payment that occurred based on the Payment request. Only available if status is PAID.
           * <h2>Examples</h2>
           * <ul>
           *   <li>1E2FC19E5E5E4E18916609B7F8911C12</li>
           *   <li>TRLY80494-1001</li>
           * </ul>
           */
          @JsonProperty(value = "reference")
          private String reference;
          /**
           * Payment reference, from the bank, of the payment that occurred based on the Payment request. Only available if status is PAID.
           * <h2>Examples</h2>
           * <ul>
           *   <li>467123476</li>
           * </ul>
           */
          @JsonProperty(value = "payerAlias")
          private String payerAlias;
        }
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class CreditSwishNotificationResponse extends JsonRpcResponse<CreditSwishNotificationResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<AckData> {
      public Result() {
        super.method = "credit";
      }
    }
  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class DebitDirectCreditNotification extends JsonRpcNotification<DebitDirectCreditNotification.Params> {
    public DebitDirectCreditNotification() {
      super("debit");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcNotificationParams<Params.Data> {
      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends AbstractDebitNotificationData<Data.Attributes> {
        @Getter
        @Jacksonized
        @RequiredArgsConstructor
        @Setter
        @SuperBuilder
        public static class Attributes extends AbstractRequestDataAttributes {
          /**
           * Payment reference, from the bank, of the payment that occurred based on the Payment request. Only available if status is PAID.
           * <h2>Examples</h2>
           * <ul>
           *   <li>1E2FC19E5E5E4E18916609B7F8911C12</li>
           *   <li>TRLY80494-1001</li>
           * </ul>
           */
          @JsonProperty(value = "reference")
          private String reference;
          /**
           * Statement that appear on the end users bank account
           * <h2>Examples</h2>
           * <ul>
           *   <li>TRLY80494-1001</li>
           * </ul>
           */
          @JsonProperty(value = "statement")
          private String statement;
          /**
           * From DebitDirectCreditNotificationDataAttributes
           */
          @JsonProperty(value = "reason")
          private DirectCreditDebitNotificationReason reason;
          /**
           * Description of reason. If Direct Debit Mandate, then  this applies when reason is <code>FAILED: BACS ADDACS_1(INSTRUCTION CANCELLED BY PAYER)</code>. See https://eu.developers.trustly.com/doc/reference/mdd#description-of-details-eg-failure-details
           */
          @JsonProperty(value = "details")
          private String details;

          public enum DirectCreditDebitNotificationReason {
            /**
             * All other failures
             */
            FAILED("FAILED");

            @JsonValue
            private final String value;

            DirectCreditDebitNotificationReason(String value) {
              this.value = value;
            }
          }
        }
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class DebitDirectCreditNotificationResponse extends JsonRpcResponse<DebitDirectCreditNotificationResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<DebitNotificationResponseData> {
      public Result() {
        super.method = "debit";
      }
    }
  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class DebitDirectDebitNotification extends JsonRpcNotification<DebitDirectDebitNotification.Params> {
    public DebitDirectDebitNotification() {
      super("debit");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcNotificationParams<Params.Data> {
      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends AbstractDebitNotificationData<Data.Attributes> {
        @Getter
        @Jacksonized
        @RequiredArgsConstructor
        @Setter
        @SuperBuilder
        public static class Attributes extends AbstractRequestDataAttributes {
          /**
           * Payment reference, from the bank, of the payment that occurred based on the Payment request. Only available if status is PAID.
           * <h2>Examples</h2>
           * <ul>
           *   <li>1E2FC19E5E5E4E18916609B7F8911C12</li>
           *   <li>TRLY80494-1001</li>
           * </ul>
           */
          @JsonProperty(value = "reference")
          private String reference;
          /**
           * Statement that appear on the end users bank account
           * <h2>Examples</h2>
           * <ul>
           *   <li>TRLY80494-1001</li>
           * </ul>
           */
          @JsonProperty(value = "statement")
          private String statement;
          @JsonProperty(value = "reason")
          private Reason reason;
          /**
           * Description of reason. If Direct Debit Mandate, then  this applies when reason is <code>FAILED: BACS ADDACS_1(INSTRUCTION CANCELLED BY PAYER)</code>. See https://eu.developers.trustly.com/doc/reference/mdd#description-of-details-eg-failure-details
           */
          @JsonProperty(value = "details")
          private String details;

          public enum Reason {
            /**
             * The mandate is invalid for some reason.
             */
            ERROR_MANDATE_INVALID("ERROR_MANDATE_INVALID"),
            /**
             * Ie. missing funds or being canceled by the end-user.
             */
            ERROR_CHARGE_NOT_APPROVED("ERROR_CHARGE_NOT_APPROVED"),
            /**
             * If the payment was canceled from the api
             */
            CANCELLED("CANCELLED"),
            /**
             * All other failures
             */
            FAILED("FAILED");

            @JsonValue
            private final String value;

            Reason(String value) {
              this.value = value;
            }
          }
        }
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class DebitDirectDebitNotificationResponse extends JsonRpcResponse<DebitDirectDebitNotificationResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<DebitNotificationResponseData> {
      public Result() {
        super.method = "debit";
      }
    }
  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class DebitNotification extends JsonRpcNotification<DebitNotification.Params> {
    public DebitNotification() {
      super("debit");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcNotificationParams<Params.Data> {
      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends AbstractDebitNotificationData<Data.DirectDebitNotificationDataAttributes> {
        /**
         * Flag indicating that this is for Direct Debit refund. Note that this flag is not sent unless it's for a refund. Only value will be 1.
         * From DebitRefundDirectDebitNotificationData
         * <p>
         * Flag indicating that this is for Direct Debit refund. Note that this flag is not sent unless it's for a refund. Only value will be 1.
         */
        @JsonProperty(value = "refund")
        private String refund;

        @Getter
        @Jacksonized
        @RequiredArgsConstructor
        @Setter
        @SuperBuilder
        public static class DirectDebitNotificationDataAttributes extends AbstractRequestDataAttributes {
          /**
           * Payment reference, from the bank, of the payment that occurred based on the Payment request. Only available if status is PAID.
           * <h2>Examples</h2>
           * <ul>
           *   <li>1E2FC19E5E5E4E18916609B7F8911C12</li>
           *   <li>TRLY80494-1001</li>
           * </ul>
           */
          @JsonProperty(value = "reference")
          private String reference;
          /**
           * Statement that appear on the end users bank account
           * <h2>Examples</h2>
           * <ul>
           *   <li>TRLY80494-1001</li>
           * </ul>
           */
          @JsonProperty(value = "statement")
          private String statement;
          /**
           * Description of reason. If Direct Debit Mandate, then  this applies when reason is <code>FAILED: BACS ADDACS_1(INSTRUCTION CANCELLED BY PAYER)</code>. See https://eu.developers.trustly.com/doc/reference/mdd#description-of-details-eg-failure-details
           */
          @JsonProperty(value = "details")
          private String details;
          /**
           * From DebitDirectCreditNotificationDataAttributes
           */
          @JsonProperty(value = "reason")
          private Reason reason;

          public enum Reason {
            /**
             * All other failures
             * From DirectCreditDebitNotificationReason
             * From DebitDirectDebitNotificationReason
             */
            FAILED("FAILED"),
            /**
             * The mandate is invalid for some reason.
             * From DebitDirectDebitNotificationReason
             */
            ERROR_MANDATE_INVALID("ERROR_MANDATE_INVALID"),
            /**
             * Ie. missing funds or being canceled by the end-user.
             * From DebitDirectDebitNotificationReason
             */
            ERROR_CHARGE_NOT_APPROVED("ERROR_CHARGE_NOT_APPROVED"),
            /**
             * If the payment was canceled from the api
             * From DebitDirectDebitNotificationReason
             */
            CANCELLED("CANCELLED");

            @JsonValue
            private final String value;

            Reason(String value) {
              this.value = value;
            }
          }
        }
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class DebitNotificationResponse extends JsonRpcResponse<DebitNotificationResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<DebitNotificationResponseData> {
      public Result() {
        super.method = "debit";
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class DebitNotificationResponseData extends NotificationResponseDataBase<DebitNotificationResponseData.Status> {
    public enum Status {
      OK("OK"),
      FAILED("FAILED");

      @JsonValue
      private final String value;

      Status(String value) {
        this.value = value;
      }
    }
  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class DebitRefundDirectDebitNotification extends JsonRpcNotification<DebitRefundDirectDebitNotification.Params> {
    public DebitRefundDirectDebitNotification() {
      super("debit");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcNotificationParams<Params.Data> {
      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends AbstractDebitNotificationData<Data.Attributes> {
        /**
         * Flag indicating that this is for Direct Debit refund. Note that this flag is not sent unless it's for a refund. Only value will be 1.
         */
        @JsonProperty(value = "refund")
        public String getRefund() {
          return "1";
        }

        @Getter
        @Jacksonized
        @RequiredArgsConstructor
        @Setter
        @SuperBuilder
        public static class Attributes extends AbstractRequestDataAttributes {
          /**
           * Payment reference, from the bank, of the payment that occurred based on the Payment request. Only available if status is PAID.
           * <h2>Examples</h2>
           * <ul>
           *   <li>1E2FC19E5E5E4E18916609B7F8911C12</li>
           *   <li>TRLY80494-1001</li>
           * </ul>
           */
          @JsonProperty(value = "reference")
          private String reference;
          /**
           * Statement that appear on the end users bank account
           * <h2>Examples</h2>
           * <ul>
           *   <li>TRLY80494-1001</li>
           * </ul>
           */
          @JsonProperty(value = "statement")
          private String statement;
        }
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class DebitRefundDirectDebitNotificationResponse extends JsonRpcResponse<DebitRefundDirectDebitNotificationResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<DebitNotificationResponseData> {
      public Result() {
        super.method = "debit";
      }
    }
  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class DenyWithdrawalRequest extends JsonRpcRequest<DenyWithdrawalRequest.Params> {
    public DenyWithdrawalRequest() {
      super("DenyWithdrawal");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcRequestParams<Params.Data> {
      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends AbstractRequestData<AnyAttributes> {
        /**
         * The globally unique OrderID the charge order was assigned in our system. The order has no end-user interaction; it is merely used as a reference for the notifications delivered regarding the charge.
         * <h2>Examples</h2>
         * <ul>
         *   <li>9594811343</li>
         * </ul>
         */
        @JsonProperty(value = "OrderID", required = true)
        @JsonInclude
        @NotNull
        private long orderId;
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class DenyWithdrawalResponse extends JsonRpcResponse<DenyWithdrawalResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<Result.Data> {
      public Result() {
        super.method = "DenyWithdrawal";
      }

      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data {
        /**
         * The OrderID specified when calling the method.
         */
        @JsonProperty(value = "orderid", required = true)
        @JsonInclude
        @NotNull
        private long orderID;
        /**
         * '1' if the refund request is accepted by Trustly's system. If the  refund request is not accepted, you will get an error code back in the <code>JsonRpcResponse</code> -&gt; <code>error</code>
         */
        @JsonProperty(value = "result", required = true)
        @JsonInclude
        @NotNull
        private StringBoolean result;
      }
    }
  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class DepositRequest extends JsonRpcRequest<DepositRequest.Params> {
    public DepositRequest() {
      super("Deposit");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcRequestParams<Params.Data> {
      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends AbstractRequestData<Data.Attributes> implements IAdditionalProperties {
        /**
         * The URL to which notifications for this should be sent to. This URL should be hard to guess and not contain a ? ("question mark").
         * <h2>Examples</h2>
         * <ul>
         *   <li>https://example.com/trustly/notification/a2b63j23dj23883jhfhfh</li>
         * </ul>
         */
        @JsonProperty(value = "NotificationURL", required = true)
        @JsonInclude
        @NotNull
        private String notificationUrl;
        /**
         * ID, username, hash or anything uniquely identifying the end-user requesting the deposit.
         * Preferably the same ID/username as used in the merchant's own backoffice in order to simplify for the merchant's support department.
         */
        @JsonProperty(value = "EndUserID", required = true)
        @JsonInclude
        @NotNull
        private String endUserId;
        /**
         * Your unique ID of the transaction.
         * <h2>Examples</h2>
         * <ul>
         *   <li>12345678</li>
         * </ul>
         */
        @JsonProperty(value = "MessageID", required = true)
        @JsonInclude
        @NotNull
        private String messageId;
        @Singular
        @JsonAnySetter
        private Map<String, Object> additionalProperties;

        public void addAdditionalProperty(String key, Object value) {
          this.additionalProperties.put(key, value);
        }

        @JsonAnyGetter
        public Map<String, Object> getAdditionalProperties() {
          return this.additionalProperties;
        }

        @Getter
        @Jacksonized
        @RequiredArgsConstructor
        @Setter
        @SuperBuilder
        public static class Attributes extends AbstractRequestDataAttributes {
          /**
           * First name of the person, or the name of the organization/company.
           */
          @JsonProperty(value = "Firstname", required = true)
          @JsonInclude
          @NotNull
          private String firstname;
          /**
           * Last name of the person (NULL/empty for organization/company).
           */
          @JsonProperty(value = "Lastname", required = true)
          @JsonInclude
          private String lastname;
          /**
           * The ISO 3166-1-alpha-2 code of the end-user's country. This will be used for pre-selecting the country for the end-user in the iframe.
           * Note: This will only have an effect for new end-users. If an end-user has done a previous order (with the same EndUserID), the country that was last used will be pre-selected.
           */
          @JsonProperty(value = "Country", required = true)
          @JsonInclude
          @NotNull
          private String country;
          /**
           * The end-users localization preference in the format language[_territory]. Language is the ISO 639-1 code and territory the ISO 3166-1-alpha-2 code.
           */
          @JsonProperty(value = "Locale", required = true)
          @JsonInclude
          @NotNull
          private String locale;
          /**
           * The text to show on the end-user's bank statement after Trustly's own 10 digit reference (which always will be displayed first). The reference must let the end user identify the merchant based on this value. So the ShopperStatement should contain either your brand name, website name, or company name.
           * <p>
           * If possible, try to keep this text as short as possible to maximise the chance that the full reference will fit into the reference field on the customer's bank since some banks allow only a limited number of characters. If the full ShopperStatement does not fit into the reference it will be truncated from the end.
           */
          @JsonProperty(value = "ShopperStatement", required = true)
          @JsonInclude
          @NotNull
          private String shopperStatement;
          /**
           * The email address of the end user.
           * <h2>Examples</h2>
           * <ul>
           *   <li>test@trustly.com</li>
           * </ul>
           */
          @JsonProperty(value = "Email", required = true)
          @JsonInclude
          @NotNull
          private String email;
          /**
           * The mobile phone number to the end-user in international format. This is used for KYC and AML routines.
           */
          @JsonProperty(value = "MobilePhone")
          private String mobilePhone;
          /**
           * The IP-address of the end-user.
           */
          @JsonProperty(value = "IP")
          private String ip;
          /**
           * The URL to which the end-user should be redirected after a successful deposit.  Do not put any logic on that page since it's not guaranteed that the end-user will in fact visit it.
           */
          @JsonProperty(value = "SuccessURL", required = true)
          @JsonInclude
          @NotNull
          private String successUrl;
          /**
           * The URL to which the end-user should be redirected after a failed  deposit. Do not put any logic on that page since it's not guaranteed that the end-user will in fact visit it.
           */
          @JsonProperty(value = "FailURL", required = true)
          @JsonInclude
          @NotNull
          private String failUrl;
          /**
           * The TemplateURL should be used if you want to design your own payment page but have it hosted on Trustly's side. The URL of your template page should be provided in this attribute in every Deposit API call. Our system will then fetch the content of your template page, insert the Trustly iframe into it and host the entire page on Trustly’s side. In the response to the Deposit request, you will receive a URL to the hosted template page which you should redirect the user to (the hosted page cannot be put inside an iframe).
           */
          @JsonProperty(value = "TemplateURL")
          private String templateUrl;
          /**
           * The html target/frame-name of the SuccessURL. Only _top, _self and _parent are supported.
           */
          @JsonProperty(value = "URLTarget")
          private UrlTarget urlTarget;
          /**
           * The end-user's social security number / personal number / birth number / etc.  Useful for some banks for identifying transactions and KYC/AML. If a Swedish personid ("personnummer") is provided, it will be pre-filled when the user logs in to their bank.
           * <h2>Examples</h2>
           * <ul>
           *   <li>790131-1234</li>
           * </ul>
           */
          @JsonProperty(value = "NationalIdentificationNumber")
          private String nationalIdentificationNumber;
          /**
           * This attribute disables the possibility to change/type in national identification number when logging in to a Swedish bank. If this attribute is sent, the attribute NationalIdentificationNumber needs to be correctly included in the request.  Note: This is only available for Swedish banks.
           */
          @JsonProperty(value = "UnchangeableNationalIdentificationNumber")
          private String unchangeableNationalIdentificationNumber;
          /**
           * If you are using Trustly from within your native iOS app, this attribute should be sent so that we can redirect the users back to your app in case an external app is used for authentication (for example Mobile Bank ID in Sweden).
           */
          @JsonProperty(value = "URLScheme")
          private String urlScheme;
          /**
           * When rendering the Trustly Checkout in a native app you are required to pass your application's url as an attribute to the order initiation request. By doing so, Trustly can redirect users back to your app after using external identification apps such as Mobile BankID: Please visit documentation site for more information. It must not be included for transactions that are not originating from an app.
           * <p>
           * NOTE! This value is only used for redirecting users back to the native app within the flows. See also SuccessURL and FailURL descriptions.
           */
          @JsonProperty(value = "ReturnToAppURL")
          private String returnToAppUrl;
          /**
           * iDeal. The iDEAL integration offered by Trustly allows for both iDEAL and Trustly payments on a single integration with all transactions visible in the same AccountLedger. To initiate a new iDEAL payment, add Method = "deposit.bank.netherlands.ideal" to the Deposit attributes.
           * <h2>Examples</h2>
           * <ul>
           *   <li>deposit.bank.netherlands.ideal</li>
           * </ul>
           */
          @JsonProperty(value = "Method")
          private String method;
          /**
           * The ISO 4217 code of the currency. See <a href="https://eu.developers.trustly.com/doc/reference/handling-currencies">documentation</a>
           * <h2>Examples</h2>
           * <ul>
           *   <li>BGN</li>
           *   <li>CZK</li>
           *   <li>DKK</li>
           *   <li>EUR</li>
           *   <li>GBP</li>
           *   <li>HRK</li>
           *   <li>HUF</li>
           *   <li>NOK</li>
           *   <li>PLN</li>
           *   <li>RON</li>
           *   <li>SEK</li>
           * </ul>
           */
          @JsonProperty(value = "Currency", required = true)
          @JsonInclude
          private String currency;
          /**
           * The amount to deposit with exactly two decimals in the currency specified by Currency. Do not use this attribute in combination with<code>suggestedMinAmount</code> and <code>suggestedMaxAmount</code>. Only digits. Use dot (.) as decimal separator.
           */
          @JsonProperty(value = "Amount")
          private String amount;
          /**
           * The ISO 3166-1-alpha-2 code of the shipping address country.
           */
          @JsonProperty(value = "ShippingAddressCountry")
          private String shippingAddressCountry;
          /**
           * The postal code of the shipping address.
           */
          @JsonProperty(value = "ShippingAddressPostalCode")
          private String shippingAddressPostalCode;
          /**
           * The city of the shipping address.
           */
          @JsonProperty(value = "ShippingAddressCity")
          private String shippingAddressCity;
          /**
           * Shipping address street
           */
          @JsonProperty(value = "ShippingAddressLine1")
          private String shippingAddressLine1;
          /**
           * Additional shipping address information.
           */
          @JsonProperty(value = "ShippingAddressLine2")
          private String shippingAddressLine2;
          /**
           * The entire shipping address. This attribute should only be used if you are unable to provide the shipping address information in the 5 separate attributes: <code>ShippingAddressCountry</code>, <code>ShippingAddressCity</code>, <code>ShippingAddressPostalCode</code>, <code>ShippingAddressLine</code>, <code>ShippingAddressLine</code>
           */
          @JsonProperty(value = "ShippingAddress")
          private String shippingAddress;
          /**
           * In addition to the deposit, request a direct debit mandate from the account used for the deposit. 1 enables, 0 disables. The default is disabled. If this attribute is set, additional account notifications might be sent. You can read more about Trustly Direct Debit here,  under section 2.1
           */
          @JsonProperty(value = "RequestDirectDebitMandate")
          private StringBoolean requestDirectDebitMandate;
          /**
           * The AccountID received from an account notification which shall be charged in a Trustly Direct Debit deposit. This attribute should only be sent in combination with {"QuickDeposit" : 1}
           */
          @JsonProperty(value = "ChargeAccountId")
          private String chargeAccountId;
          /**
           * Set to 1 for Trustly Direct Debit deposits. QuickDeposit should be  set set to 1 when the end user attempts a quick deposit, even if  ChargeAccountID is not set. You can read more about QuickDeposits  here, under section 1.1 and 1.2.
           */
          @JsonProperty(value = "QuickDeposit")
          private StringBoolean quickDeposit;
          /**
           * The ExternalReference is a reference set by the merchant for any purpose and does not need to be unique for every API call. For example, it can be used for invoice references, OCR numbers and also for offering end users the option to part-pay an invoice using the same ExternalReference. The ExternalReference will be included in version 1.2 of the settlement report, <code>ViewAutomaticSettlementDetailsCSV</code>.
           * <h2>Examples</h2>
           * <ul>
           *   <li>32423534523</li>
           * </ul>
           */
          @JsonProperty(value = "ExternalReference")
          private String externalReference;
          /**
           * Human-readable identifier of the consumer-facing merchant (e.g. legal name or trade name)
           * Note: Mandatory attribute for Trustly Partners that are using Express Merchant Onboarding (EMO) and aggregate traffic under a master processing account.  It is also mandatory for E-wallets used directly in a merchant's checkout.
           * Mandatory attribute for Trustly Partners that are using Express Merchant Onboarding and aggregate traffic under a master processing account.  It is also mandatory for E-wallets used directly in a merchant's checkout, whereby the purpose of a Trustly transaction is to pay for goods/services by placing funds on the payer's e-money account ("funding stage") following an immediate transfer into the e-money account of the payee ( "payment" stage).
           */
          @JsonProperty(value = "PSPMerchant")
          private String pspMerchant;
          /**
           * URL of the consumer-facing website where the order is initiated
           * Note: Mandatory attribute for Trustly Partners that are using Express Merchant Onboarding (EMO) and aggregate traffic under a master processing account.  It is also mandatory for E-wallets used directly in a merchant's checkout.
           * Mandatory attributes for Trustly Partners that are using Express Merchant Onboarding and aggregate traffic under a master processing account.  It is also mandatory for E-wallets used directly in a merchant's checkout, whereby the purpose of a Trustly transaction is to pay for goods/services by placing funds on the payer's e-money account ("funding stage") following an immediate transfer into the e-money account of the payee ( "payment" stage).
           */
          @JsonProperty(value = "PSPMerchantURL")
          private String pspMerchantUrl;
          /**
           * VISA category codes describing the merchant's nature of business.
           * Note: Mandatory attribute for Trustly Partners that are using Express Merchant Onboarding (EMO) and aggregate traffic under a master processing account.  It is also mandatory for E-wallets used directly in a merchant's checkout.
           * Mandatory attributes for Trustly Partners that are using Express account. It is also mandatory for E-wallets used directly in a merchant's checkout, whereby the purpose of a Trustly transaction is to pay for goods/services by placing funds on the payer's e-money account ("funding stage") following an immediate transfer into the e-money account of the payee ( "payment" stage).
           */
          @JsonProperty(value = "MerchantCategoryCode")
          private String merchantCategoryCode;
          /**
           * The globally unique AccountID the account was assigned in our system. The AccountID of a returning customer. Allows for a quicker payment experience in some markets, see Trustly Express.
           * <h2>Examples</h2>
           * <ul>
           *   <li>1234567890</li>
           *   <li>7653385737</li>
           * </ul>
           */
          @JsonProperty(value = "AccountId")
          private String accountId;
          /**
           * Information about the Payee (ultimate creditor). The burden of identifying who the Payee for any given transaction is lies with the Trustly customer. Required for some merchants and partners. RecipientInformation is mandatory to send for money transfer services (including remittance houses), e-wallets, prepaid cards, as well as for Trustly Partners that are using Express Merchant Onboarding and aggregate traffic under a master processing account (other cases may also apply).
           */
          @JsonProperty(value = "RecipientInformation")
          @Valid
          private RecipientInformation recipientInformation;
          /**
           * Trustly will send a KYC notification to the merchant’s <code>NotificationURL</code> if the attribute "RequestKYC" : "1" is sent in a Deposit API call. The KYC notification should be expected after the end user has performed a successful login to their bank, and always before a deposit transfer is initiated.
           */
          @JsonProperty(value = "RequestKYC")
          private StringBoolean requestKyc;
          /**
           * The minimum amount the end-user is allowed to deposit in the currency specified by Currency.Only digits. Use dot (.) as decimal separator.
           */
          @JsonProperty(value = "SuggestedMinAmount")
          private String suggestedMinAmount;
          /**
           * The maximum amount the end-user is allowed to deposit in the currency specified by Currency. Only digits. Use dot (.) as decimal separator.
           */
          @JsonProperty(value = "SuggestedMaxAmount")
          private String suggestedMaxAmount;

          /**
           * Information about the Payee (ultimate creditor). The burden of identifying who the Payee for any given transaction is lies with the Trustly customer. Required for some merchants and partners. RecipientInformation is mandatory to send for money transfer services (including remittance houses), e-wallets, prepaid cards, as well as for Trustly Partners that are using Express Merchant Onboarding and aggregate traffic under a master processing account (other cases may also apply).
           */
          @Getter
          @Jacksonized
          @RequiredArgsConstructor
          @Setter
          @SuperBuilder
          public static class RecipientInformation extends RecipientOrSenderInformation {

          }
        }
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class DepositResponse extends JsonRpcResponse<DepositResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<Result.Data> {
      public Result() {
        super.method = "Deposit";
      }

      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data {
        /**
         * The globally unique OrderID the charge order was assigned in our system. The order has no end-user interaction; it is merely used as a reference for the notifications delivered regarding the request.
         * <h2>Examples</h2>
         * <ul>
         *   <li>9594811343</li>
         * </ul>
         */
        @JsonProperty(value = "orderid")
        private String orderID;
        /**
         * The URL that should be loaded so that the end-user can complete the deposit.
         */
        @JsonProperty(value = "url")
        private String URL;
      }
    }
  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class DirectCreditRequest extends JsonRpcRequest<DirectCreditRequest.Params> {
    public DirectCreditRequest() {
      super("DirectCredit");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcRequestParams<Params.Data> {
      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends AbstractRequestData<DirectDebitRequestDataAttributes> {
        /**
         * Your unique ID of the transaction.
         * <h2>Examples</h2>
         * <ul>
         *   <li>12345678</li>
         * </ul>
         */
        @JsonProperty(value = "MessageID", required = true)
        @JsonInclude
        @NotNull
        private String messageId;
        /**
         * ID, username, hash or anything uniquely identifying the end-user requesting the deposit.
         * Preferably the same ID/username as used in the merchant's own backoffice in order to simplify for the merchant's support department.
         */
        @JsonProperty(value = "EndUserID", required = true)
        @JsonInclude
        @NotNull
        private String endUserId;
        /**
         * The URL to which notifications for this should be sent to. This URL should be hard to guess and not contain a ? ("question mark").
         * <h2>Examples</h2>
         * <ul>
         *   <li>https://example.com/trustly/notification/a2b63j23dj23883jhfhfh</li>
         * </ul>
         */
        @JsonProperty(value = "NotificationURL", required = true)
        @JsonInclude
        @NotNull
        private String notificationUrl;
        /**
         * The globally unique AccountID the account was assigned in our system. The AccountID of a returning customer. Allows for a quicker payment experience in some markets, see Trustly Express.
         * <h2>Examples</h2>
         * <ul>
         *   <li>1234567890</li>
         *   <li>7653385737</li>
         * </ul>
         */
        @JsonProperty(value = "AccountID", required = true)
        @JsonInclude
        @NotNull
        private String accountId;
        /**
         * The branch identifier
         * <h2>Examples</h2>
         * <ul>
         *   <li>6160</li>
         *   <li>6000</li>
         *   <li>bg</li>
         *   <li>123123</li>
         *   <li>HANDSESS</li>
         * </ul>
         */
        @JsonProperty(value = "BankIdentifier")
        private String bankIdentifier;
        /**
         * The account number, identifying the end-user's account in the bank. Can be either IBAN or country-specific format, see examples or read more at https://developers.trustly.com/emea/docs/registeraccount
         * <h2>Examples</h2>
         * <ul>
         *   <li>6112</li>
         *   <li>391124057</li>
         *   <li>AUSTRIA: ^AT[0-9]{18}$</li>
         *   <li>BELGIUM: ^BE[0-9]{14}$</li>
         *   <li>BULGARIA: ^BG[0-9]{2}[A-Z]{4}[0-9]{4}[0-9]{2}[A-Z0-9]{8}$</li>
         *   <li>CROATIA: ^HR[0-9]{2}[0-9]{7}[0-9]{10}$</li>
         *   <li>CYPRUS: ^CY[0-9]{10}[0-9A-Z]{16}$</li>
         *   <li>CZECH_REPUBLIC: ^CZ[0-9]{22}$</li>
         *   <li>DENMARK: ^DK[0-9]{16}$</li>
         *   <li>ESTONIA: ^EE[0-9]{18}$</li>
         *   <li>FINLAND: ^FI[0-9]{16}$</li>
         *   <li>FRANCE: ^FR[0-9]{12}[0-9A-Z]{11}[0-9]{2}$</li>
         *   <li>GERMANY: ^DE[0-9]{20}$</li>
         *   <li>GREECE: ^GR[0-9]{25}$</li>
         *   <li>HUNGARY: ^HU[0-9]{26}$</li>
         *   <li>IRELAND: ^IE[0-9]{2}[A-Z]{4}[0-9]{14}$</li>
         *   <li>ITALY: ^IT[0-9]{2}[A-Z][0-9]{10}[0-9A-Z]{12}$</li>
         *   <li>LATVIA: ^LV[0-9]{2}[A-Z]{4}[0-9A-Z]{13}$</li>
         *   <li>LITHUANIA: ^LT[0-9]{18}$</li>
         *   <li>LUXEMBOURG: ^LU[0-9]{18}$</li>
         *   <li>MALTA: ^MT[0-9]{2}[A-Z]{4}[0-9]{5}[0-9A-Z]{18}$</li>
         *   <li>NETHERLANDS: ^NL[0-9]{2}[A-Z]{4}[0-9]{10}$</li>
         *   <li>NORWAY: ^NO[0-9]{13}$</li>
         *   <li>POLAND: ^PL[0-9]{26}$</li>
         *   <li>PORTUGAL: ^PT[0-9]{23}$</li>
         *   <li>ROMANIA: ^RO[0-9]{2}[A-Z]{4}[0-9A-Z]{16}$</li>
         *   <li>SLOVAKIA: ^SK[0-9]{22}$</li>
         *   <li>SLOVENIA: ^SI56[0-9]{15}$</li>
         *   <li>SPAIN: ^ES[0-9]{22}$</li>
         *   <li>SWEDEN:* [0-9]{1,15}$</li>
         *   <li>UNITED_KINGDOM: ^[0-9]{8}$</li>
         * </ul>
         */
        @JsonProperty(value = "AccountNumber")
        private String accountNumber;
        /**
         * The amount to deposit with exactly two decimals in the currency specified by Currency. Do not use this attribute in combination with<code>suggestedMinAmount</code> and <code>suggestedMaxAmount</code>. Only digits. Use dot (.) as decimal separator.
         */
        @JsonProperty(value = "Amount")
        private String amount;
        /**
         * The ISO 4217 code of the currency. See <a href="https://eu.developers.trustly.com/doc/reference/handling-currencies">documentation</a>
         * <h2>Examples</h2>
         * <ul>
         *   <li>BGN</li>
         *   <li>CZK</li>
         *   <li>DKK</li>
         *   <li>EUR</li>
         *   <li>GBP</li>
         *   <li>HRK</li>
         *   <li>HUF</li>
         *   <li>NOK</li>
         *   <li>PLN</li>
         *   <li>RON</li>
         *   <li>SEK</li>
         * </ul>
         */
        @JsonProperty(value = "Currency")
        private String currency;
        /**
         * The ISO 3166-1-alpha-2 code of the end-user's country. This will be used for pre-selecting the country for the end-user in the iframe.
         * Note: This will only have an effect for new end-users. If an end-user has done a previous order (with the same EndUserID), the country that was last used will be pre-selected.
         */
        @JsonProperty(value = "Country")
        private String country;
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class DirectCreditResponse extends JsonRpcResponse<DirectCreditResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<Result.Data> {
      public Result() {
        super.method = "DirectCredit";
      }

      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends WithRejection<StringBoolean, Data.DirectCreditRejected> {
        /**
         * The globally unique OrderID the charge order was assigned in our system. The order has no end-user interaction; it is merely used as a reference for the notifications delivered regarding the request.
         * <h2>Examples</h2>
         * <ul>
         *   <li>9594811343</li>
         * </ul>
         */
        @JsonProperty(value = "orderid", required = true)
        @JsonInclude
        @NotNull
        private String orderID;

        public enum DirectCreditRejected {
          /**
           * No matching active accountId
           */
          ERROR_MANDATE_NOT_FOUND("ERROR_MANDATE_NOT_FOUND"),
          /**
           * Account not properly configured
           */
          ERROR_DIRECT_DEBIT_NOT_ALLOWED("ERROR_DIRECT_DEBIT_NOT_ALLOWED"),
          /**
           * Payment date is invalid
           */
          ERROR_PAYMENT_DATE_FAILURE("ERROR_PAYMENT_DATE_FAILURE"),
          /**
           * Amount is invalid or not within the configured range
           */
          ERROR_AMOUNT_FAILURE("ERROR_AMOUNT_FAILURE"),
          /**
           * The mandate doesn't support the currency
           */
          ERROR_CURRENCY_FAILURE("ERROR_CURRENCY_FAILURE");

          @JsonValue
          private final String value;

          DirectCreditRejected(String value) {
            this.value = value;
          }
        }
      }
    }
  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class DirectDebitMandateRequest extends JsonRpcRequest<DirectDebitMandateRequest.Params> {
    public DirectDebitMandateRequest() {
      super("DirectDebitMandate");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcRequestParams<Params.Data> {
      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends AbstractRequestData<Data.Attributes> {
        /**
         * Your unique ID of the transaction.
         * <h2>Examples</h2>
         * <ul>
         *   <li>12345678</li>
         * </ul>
         */
        @JsonProperty(value = "MessageID", required = true)
        @JsonInclude
        @NotNull
        private String messageId;
        /**
         * The URL to which notifications for this should be sent to. This URL should be hard to guess and not contain a ? ("question mark").
         * <h2>Examples</h2>
         * <ul>
         *   <li>https://example.com/trustly/notification/a2b63j23dj23883jhfhfh</li>
         * </ul>
         */
        @JsonProperty(value = "NotificationURL", required = true)
        @JsonInclude
        @NotNull
        private String notificationUrl;
        /**
         * ID, username, hash or anything uniquely identifying the end-user requesting the deposit.
         * Preferably the same ID/username as used in the merchant's own backoffice in order to simplify for the merchant's support department.
         */
        @JsonProperty(value = "EndUserID", required = true)
        @JsonInclude
        @NotNull
        private String endUserId;

        @Getter
        @Jacksonized
        @RequiredArgsConstructor
        @Setter
        @SuperBuilder
        public static class Attributes extends AbstractRequestDataAttributes {
          /**
           * The globally unique AccountID the account was assigned in our system. The AccountID of a returning customer. Allows for a quicker payment experience in some markets, see Trustly Express.
           * <h2>Examples</h2>
           * <ul>
           *   <li>1234567890</li>
           *   <li>7653385737</li>
           * </ul>
           */
          @JsonProperty(value = "AccountId")
          private String accountId;
          /**
           * This parameter in a way identifies the mandate you are to setup. If it's already used, you will receive an error, ERROR_MERCHANT_REFERENCE_ALREADY_EXISTS
           * which basically informs you that there's already a mandate with that reference.
           * <p>
           * [BACS]: The unique mandate reference. 6 - 10 characters consisting of A-Z and 0-9. Can not begin with DDIC and neither consists of the same characters, eg. AAAAAAA.
           * [Bankgiro]: The unique mandate reference. This must be numeric and unique for the payer, eg nationalId or similar can be used. Format needs to follow regexp [1-9][0-9]{5-15}
           * [SEPA-DD]: The unique mandate reference. This must be unique for the end-user for you as a merchant. Format needs to follow regexp [0-9,a-z,A-Z]{10-35}
           * <h2>Examples</h2>
           * <ul>
           *   <li>123ABC0123</li>
           * </ul>
           */
          @JsonProperty(value = "MerchantReference", required = true)
          @JsonInclude
          @NotNull
          private String merchantReference;
          /**
           * The country where the mandate is to be created.
           */
          @JsonProperty(value = "Country", required = true)
          @JsonInclude
          @NotNull
          private String country;
          /**
           * The first name of the end user.
           * [BACS]: Only mandatory if manual entry should be enabled.
           * [Bankgiro]: Mandatory
           * [Sepa-DD]: Mandatory
           */
          @JsonProperty(value = "Firstname")
          private String firstname;
          /**
           * The last name of the end user.
           * [BACS]: Only mandatory if manual entry should be enabled.
           * [Bankgiro]: Mandatory
           * [Sepa-DD]: Mandatory
           */
          @JsonProperty(value = "Lastname")
          private String lastname;
          /**
           * The email address of the end user.
           * <h2>Examples</h2>
           * <ul>
           *   <li>test@trustly.com</li>
           * </ul>
           */
          @JsonProperty(value = "Email", required = true)
          @JsonInclude
          @NotNull
          private String email;
          /**
           * The mobile phone number to the end-user in international format. This is used for KYC and AML routines.
           */
          @JsonProperty(value = "MobilePhone")
          private String mobilePhone;
          /**
           * The URL to which the end-user should be redirected after a successful deposit.  Do not put any logic on that page since it's not guaranteed that the end-user will in fact visit it.
           */
          @JsonProperty(value = "SuccessURL", required = true)
          @JsonInclude
          @NotNull
          private String successUrl;
          /**
           * The URL to which the end-user should be redirected after a failed  deposit. Do not put any logic on that page since it's not guaranteed that the end-user will in fact visit it.
           */
          @JsonProperty(value = "FailURL", required = true)
          @JsonInclude
          @NotNull
          private String failUrl;
          /**
           * The end-user's date of birth.
           */
          @JsonProperty(value = "DateOfBirth")
          private String dateOfBirth;
          /**
           * The national identification number.Format is yyyyMMddxxxx. Only applicable for Bankgiro(SE)
           * <p>
           * [Bankgiro]: To preset the nationalId to use for setting up the mandate. To make the end-user journey smooth, please use this parameter.
           */
          @JsonProperty(value = "NationalIdentificationNumber")
          private String nationalIdentificationNumber;
          /**
           * Only applicable for Bankgiro(SE)
           * [Bankgiro]If the nationalId should be locked to the value of NationalIdentificationNumber, then set this to 1 or omit the parameter (you may set it to 0 as well).
           */
          @JsonProperty(value = "UnchangeableNationalIdentificationNumber")
          private String unchangeableNationalIdentificationNumber;
          /**
           * If the payment plan is known in advance, this information is displayed when approving the mandate.
           */
          @JsonProperty(value = "PaymentSchedule")
          @Valid
          private DirectDebitPaymentSchedule paymentSchedule;
          /**
           * Recipient address street
           * <h2>Examples</h2>
           * <ul>
           *   <li>Main Street 1</li>
           * </ul>
           */
          @JsonProperty(value = "AddressLine1")
          private String addressLine1;
          /**
           * Additional address information of the recipient.
           */
          @JsonProperty(value = "AddressLine2")
          private String addressLine2;
          /**
           * The city of the recipient address.
           */
          @JsonProperty(value = "AddressCity")
          private String addressCity;
          /**
           * The postalcode of the recipient address.
           */
          @JsonProperty(value = "AddressPostalCode")
          private String addressPostalCode;
          /**
           * The ISO 3166-1-alpha-2 code of the recipient address country.
           */
          @JsonProperty(value = "AddressCountry")
          private String addressCountry;
          /**
           * The end-users localization preference in the format language[_territory]. Language is the ISO 639-1 code and territory the ISO 3166-1-alpha-2 code.
           */
          @JsonProperty(value = "Locale")
          private String locale;
          @JsonProperty(value = "Theme")
          private DirectDebitTheme theme;
          /**
           * When rendering the Trustly Checkout in a native app you are required to pass your application's url as an attribute to the order initiation request. By doing so, Trustly can redirect users back to your app after using external identification apps such as Mobile BankID: Please visit documentation site for more information. It must not be included for transactions that are not originating from an app.
           * <p>
           * NOTE! This value is only used for redirecting users back to the native app within the flows. See also SuccessURL and FailURL descriptions.
           */
          @JsonProperty(value = "ReturnToAppURL")
          private String returnToAppUrl;
          /**
           * The text to show on the end-user's bank statement after Trustly's own 10 digit reference (which always will be displayed first). The reference must let the end user identify the merchant based on this value. So the ShopperStatement should contain either your brand name, website name, or company name.
           * <p>
           * If possible, try to keep this text as short as possible to maximise the chance that the full reference will fit into the reference field on the customer's bank since some banks allow only a limited number of characters. If the full ShopperStatement does not fit into the reference it will be truncated from the end.
           */
          @JsonProperty(value = "ShopperStatement")
          private String shopperStatement;

          /**
           * If the payment plan is known in advance, this information is displayed when approving the mandate.
           */
          @Getter
          @Jacksonized
          @RequiredArgsConstructor
          @Setter
          @SuperBuilder
          public static class DirectDebitPaymentSchedule implements IAdditionalProperties {
            @Singular
            @JsonAnySetter
            private Map<String, Object> additionalProperties;

            public void addAdditionalProperty(String key, Object value) {
              this.additionalProperties.put(key, value);
            }

            @JsonAnyGetter
            public Map<String, Object> getAdditionalProperties() {
              return this.additionalProperties;
            }
          }

          public enum DirectDebitTheme {
            DARK("DARK"),
            LIGHT("LIGHT"),
            DEFAULT("DEFAULT");

            @JsonValue
            private final String value;

            DirectDebitTheme(String value) {
              this.value = value;
            }
          }
        }
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class DirectDebitMandateResponse extends JsonRpcResponse<DirectDebitMandateResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<Result.Data> {
      public Result() {
        super.method = "DirectDebitMandate";
      }

      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data {
        /**
         * The globally unique OrderID the charge order was assigned in our system. The order has no end-user interaction; it is merely used as a reference for the notifications delivered regarding the request.
         * <h2>Examples</h2>
         * <ul>
         *   <li>9594811343</li>
         * </ul>
         */
        @JsonProperty(value = "orderid", required = true)
        @JsonInclude
        @NotNull
        private String orderID;
        /**
         * The URL that should be loaded so that the end-user can continue with the interactive process. Please see our general guidelines around iFraming, nativeApps etc for best usability. In general, never iFrame the url for mobile devices.
         */
        @JsonProperty(value = "url", required = true)
        @JsonInclude
        @NotNull
        private String URL;
      }
    }
  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class DirectDebitRequest extends JsonRpcRequest<DirectDebitRequest.Params> {
    public DirectDebitRequest() {
      super("DirectDebit");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcRequestParams<Params.Data> {
      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends AbstractRequestData<DirectDebitRequestDataAttributes> implements IAdditionalProperties {
        /**
         * Your unique ID of the transaction.
         * <h2>Examples</h2>
         * <ul>
         *   <li>12345678</li>
         * </ul>
         */
        @JsonProperty(value = "MessageID", required = true)
        @JsonInclude
        @NotNull
        private String messageId;
        /**
         * The URL to which notifications for this should be sent to. This URL should be hard to guess and not contain a ? ("question mark").
         * <h2>Examples</h2>
         * <ul>
         *   <li>https://example.com/trustly/notification/a2b63j23dj23883jhfhfh</li>
         * </ul>
         */
        @JsonProperty(value = "NotificationURL", required = true)
        @JsonInclude
        @NotNull
        private String notificationUrl;
        /**
         * The globally unique AccountID the account was assigned in our system. The AccountID of a returning customer. Allows for a quicker payment experience in some markets, see Trustly Express.
         * <h2>Examples</h2>
         * <ul>
         *   <li>1234567890</li>
         *   <li>7653385737</li>
         * </ul>
         */
        @JsonProperty(value = "AccountID", required = true)
        @JsonInclude
        @NotNull
        private String accountId;
        /**
         * This parameter in a way identifies the mandate you are to setup. If it's already used, you will receive an error, ERROR_MERCHANT_REFERENCE_ALREADY_EXISTS
         * which basically informs you that there's already a mandate with that reference.
         * <p>
         * [BACS]: The unique mandate reference. 6 - 10 characters consisting of A-Z and 0-9. Can not begin with DDIC and neither consists of the same characters, eg. AAAAAAA.
         * [Bankgiro]: The unique mandate reference. This must be numeric and unique for the payer, eg nationalId or similar can be used. Format needs to follow regexp [1-9][0-9]{5-15}
         * [SEPA-DD]: The unique mandate reference. This must be unique for the end-user for you as a merchant. Format needs to follow regexp [0-9,a-z,A-Z]{10-35}
         * <h2>Examples</h2>
         * <ul>
         *   <li>123ABC0123</li>
         * </ul>
         */
        @JsonProperty(value = "MerchantReference")
        private String merchantReference;
        /**
         * The amount to deposit with exactly two decimals in the currency specified by Currency. Do not use this attribute in combination with<code>suggestedMinAmount</code> and <code>suggestedMaxAmount</code>. Only digits. Use dot (.) as decimal separator.
         */
        @JsonProperty(value = "Amount", required = true)
        @JsonInclude
        @NotNull
        private String amount;
        /**
         * The ISO 4217 code of the currency. See <a href="https://eu.developers.trustly.com/doc/reference/handling-currencies">documentation</a>
         * <h2>Examples</h2>
         * <ul>
         *   <li>BGN</li>
         *   <li>CZK</li>
         *   <li>DKK</li>
         *   <li>EUR</li>
         *   <li>GBP</li>
         *   <li>HRK</li>
         *   <li>HUF</li>
         *   <li>NOK</li>
         *   <li>PLN</li>
         *   <li>RON</li>
         *   <li>SEK</li>
         * </ul>
         */
        @JsonProperty(value = "Currency", required = true)
        @JsonInclude
        private String currency;
        @Singular
        @JsonAnySetter
        private Map<String, Object> additionalProperties;

        public void addAdditionalProperty(String key, Object value) {
          this.additionalProperties.put(key, value);
        }

        @JsonAnyGetter
        public Map<String, Object> getAdditionalProperties() {
          return this.additionalProperties;
        }
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class DirectDebitRequestDataAttributes extends AbstractRequestDataAttributes {
    @JsonProperty(value = "CollectionType")
    private DirectDebitCollectionType collectionType;
    /**
     * Date string in the ISO 8601 format (YYYY-MM-DD)
     * <h2>Examples</h2>
     * <ul>
     *   <li>2014-04-01</li>
     * </ul>
     */
    @JsonProperty(value = "PaymentDate")
    private String paymentDate;
    /**
     * The text to show on the end-user's bank statement after Trustly's own 10 digit reference (which always will be displayed first). The reference must let the end user identify the merchant based on this value. So the ShopperStatement should contain either your brand name, website name, or company name.
     * <p>
     * If possible, try to keep this text as short as possible to maximise the chance that the full reference will fit into the reference field on the customer's bank since some banks allow only a limited number of characters. If the full ShopperStatement does not fit into the reference it will be truncated from the end.
     */
    @JsonProperty(value = "ShopperStatement")
    private String shopperStatement;

    public enum DirectDebitCollectionType {
      INITIAL("INITIAL"),
      RECURRING("RECURRING"),
      RE_SUBMITTED("RE_SUBMITTED"),
      FINAL("FINAL");

      @JsonValue
      private final String value;

      DirectDebitCollectionType(String value) {
        this.value = value;
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class DirectDebitResponse extends JsonRpcResponse<DirectDebitResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<Result.Data> {
      public Result() {
        super.method = "DirectDebit";
      }

      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends WithRejection<StringBoolean, Data.DirectDebitRejected> implements IAdditionalProperties {
        /**
         * The globally unique OrderID the charge order was assigned in our system. The order has no end-user interaction; it is merely used as a reference for the notifications delivered regarding the request.
         * <h2>Examples</h2>
         * <ul>
         *   <li>9594811343</li>
         * </ul>
         */
        @JsonProperty(value = "orderid", required = true)
        @JsonInclude
        @NotNull
        private String orderID;
        @Singular
        @JsonAnySetter
        private Map<String, Object> additionalProperties;

        public void addAdditionalProperty(String key, Object value) {
          this.additionalProperties.put(key, value);
        }

        @JsonAnyGetter
        public Map<String, Object> getAdditionalProperties() {
          return this.additionalProperties;
        }

        public enum DirectDebitRejected {
          /**
           * No matching active accountId
           */
          ERROR_MANDATE_NOT_FOUND("ERROR_MANDATE_NOT_FOUND"),
          /**
           * Account not properly configured
           */
          ERROR_DIRECT_DEBIT_NOT_ALLOWED("ERROR_DIRECT_DEBIT_NOT_ALLOWED"),
          /**
           * Payment date is invalid
           */
          ERROR_PAYMENT_DATE_FAILURE("ERROR_PAYMENT_DATE_FAILURE"),
          /**
           * Amount is invalid or not within the configured range
           */
          ERROR_AMOUNT_FAILURE("ERROR_AMOUNT_FAILURE"),
          /**
           * The mandate doesn't support the currency
           */
          ERROR_CURRENCY_FAILURE("ERROR_CURRENCY_FAILURE"),
          /**
           * Invalid collection type
           */
          ERROR_COLLECTION_TYPE_FAILURE("ERROR_COLLECTION_TYPE_FAILURE");

          @JsonValue
          private final String value;

          DirectDebitRejected(String value) {
            this.value = value;
          }
        }
      }
    }
  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class DirectPaymentBatchRequest extends JsonRpcRequest<DirectPaymentBatchRequest.Params> {
    public DirectPaymentBatchRequest() {
      super("DirectPaymentBatch");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcRequestParams<Params.Data> {
      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends AbstractRequestData<Data.Attributes> {
        /**
         * Your unique ID of the transaction.
         * <h2>Examples</h2>
         * <ul>
         *   <li>12345678</li>
         * </ul>
         */
        @JsonProperty(value = "MessageID", required = true)
        @JsonInclude
        @NotNull
        private String messageId;
        /**
         * The URL to which notifications for this should be sent to. This URL should be hard to guess and not contain a ? ("question mark").
         * <h2>Examples</h2>
         * <ul>
         *   <li>https://example.com/trustly/notification/a2b63j23dj23883jhfhfh</li>
         * </ul>
         */
        @JsonProperty(value = "NotificationURL", required = true)
        @JsonInclude
        @NotNull
        private String notificationUrl;
        /**
         * The ISO 4217 code of the currency. See <a href="https://eu.developers.trustly.com/doc/reference/handling-currencies">documentation</a>
         * <h2>Examples</h2>
         * <ul>
         *   <li>BGN</li>
         *   <li>CZK</li>
         *   <li>DKK</li>
         *   <li>EUR</li>
         *   <li>GBP</li>
         *   <li>HRK</li>
         *   <li>HUF</li>
         *   <li>NOK</li>
         *   <li>PLN</li>
         *   <li>RON</li>
         *   <li>SEK</li>
         * </ul>
         */
        @JsonProperty(value = "Currency")
        private String currency;
        /**
         * The ISO 3166-1-alpha-2 code of the end-user's country. This will be used for pre-selecting the country for the end-user in the iframe.
         * Note: This will only have an effect for new end-users. If an end-user has done a previous order (with the same EndUserID), the country that was last used will be pre-selected.
         */
        @JsonProperty(value = "Country")
        private String country;

        @Getter
        @Jacksonized
        @RequiredArgsConstructor
        @Setter
        @SuperBuilder
        public static class Attributes extends AbstractRequestDataAttributes {
          /**
           * Date string in the ISO 8601 format (YYYY-MM-DD)
           * <h2>Examples</h2>
           * <ul>
           *   <li>2014-04-01</li>
           * </ul>
           */
          @JsonProperty(value = "PaymentDate")
          private String paymentDate;
          /**
           * The uploaded unique file name on the SFTP server containing the instructions.
           * <h2>Examples</h2>
           * <ul>
           *   <li>unique-filename.csv</li>
           * </ul>
           */
          @JsonProperty(value = "BatchFile")
          private String batchFile;
          /**
           * The MD5 checksum for the file
           * <h2>Examples</h2>
           * <ul>
           *   <li>0cdf0945096283b9ba94e42150ba84d8</li>
           * </ul>
           */
          @JsonProperty(value = "Checksum")
          private String checksum;
        }
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class DirectPaymentBatchResponse extends JsonRpcResponse<DirectPaymentBatchResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<Result.Data> {
      public Result() {
        super.method = "DirectPaymentBatch";
      }

      /**
       * 1 if the charge was accepted for processing, 0 otherwise. Note that this is an acceptance of the order, no money has been charged from the account until you receive notifications thereof.
       */
      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends WithRejection<StringBoolean, Data.DirectPaymentBatchRejected> {
        /**
         * The globally unique OrderID the charge order was assigned in our system. The order has no end-user interaction; it is merely used as a reference for the notifications delivered regarding the request.
         * <h2>Examples</h2>
         * <ul>
         *   <li>9594811343</li>
         * </ul>
         */
        @JsonProperty(value = "orderid", required = true)
        @JsonInclude
        @NotNull
        private String orderID;

        public enum DirectPaymentBatchRejected {
          /**
           * The account has not properly been configured.
           */
          ERROR_DIRECT_DEBIT_NOT_ALLOWED("ERROR_DIRECT_DEBIT_NOT_ALLOWED"),
          /**
           * Invalid payment date.
           */
          ERROR_PAYMENT_DATE_FAILURE("ERROR_PAYMENT_DATE_FAILURE"),
          /**
           * The file on the sftp server can't be interpreted.
           */
          ERROR_UNABLE_TO_READ_BATCH_FILE("ERROR_UNABLE_TO_READ_BATCH_FILE"),
          /**
           * The file could not be found on the sftp server.
           */
          ERROR_MISSING_BATCH_FILE("ERROR_MISSING_BATCH_FILE"),
          /**
           * The checksum for the file doesn't match.
           */
          ERROR_INVALID_CHECKSUM("ERROR_INVALID_CHECKSUM");

          @JsonValue
          private final String value;

          DirectPaymentBatchRejected(String value) {
            this.value = value;
          }
        }
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class ErrorUnknown extends JsonRpcErrorResponse {

  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class GetWithdrawalsRequest extends JsonRpcRequest<GetWithdrawalsRequest.Params> {
    public GetWithdrawalsRequest() {
      super("GetWithdrawals");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcRequestParams<Params.Data> {
      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends AbstractRequestData<AnyAttributes> {
        /**
         * The globally unique OrderID the charge order was assigned in our system. The order has no end-user interaction; it is merely used as a reference for the notifications delivered regarding the charge.
         * <h2>Examples</h2>
         * <ul>
         *   <li>9594811343</li>
         * </ul>
         */
        @JsonProperty(value = "OrderID", required = true)
        @JsonInclude
        @NotNull
        private long orderId;
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class GetWithdrawalsResponse extends JsonRpcResponse<GetWithdrawalsResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<List<Result.DataEntry>> {
      public Result() {
        super.method = "GetWithdrawals";
      }

      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class DataEntry {
        /**
         * Reference code for the withdrawal generated by Trustly.
         */
        @JsonProperty(value = "reference", required = true)
        @JsonInclude
        @NotNull
        private String reference;
        /**
         * Date and time when the withdrawal was last updated.
         */
        @JsonProperty(value = "modificationdate", required = true)
        @JsonInclude
        @NotNull
        private String modificationdate;
        /**
         * OrderID of the withdrawal
         */
        @JsonProperty(value = "orderid", required = true)
        @JsonInclude
        @NotNull
        private String orderID;
        /**
         * Date and time when the withdrawal request was received.
         */
        @JsonProperty(value = "datestamp", required = true)
        @JsonInclude
        @NotNull
        private String datestamp;
        /**
         * The current state of the withdrawal.
         * It's important that no logic is built on the merchant side based on any specific transferState. New states can be added, and existing states can be changed or removed without notice.
         * <h2>Examples</h2>
         * <ul>
         *   <li>EXECUTING</li>
         *   <li>EXECUTED</li>
         *   <li>PENDING</li>
         *   <li>QUEUED</li>
         *   <li>PREPARING</li>
         *   <li>PREPARED</li>
         *   <li>BOUNCED</li>
         *   <li>ERROR</li>
         *   <li>FAILED</li>
         *   <li>RETURNED</li>
         *   <li>CONFIRMED</li>
         * </ul>
         */
        @JsonProperty(value = "transferstate", required = true)
        @JsonInclude
        @NotNull
        private String transferstate;
        /**
         * The amount of the withdrawal.
         */
        @JsonProperty(value = "amount", required = true)
        @JsonInclude
        @NotNull
        private String amount;
        /**
         * The globally unique AccountID the account was assigned in our system. The AccountID of a returning customer. Allows for a quicker payment experience in some markets, see Trustly Express.
         * <h2>Examples</h2>
         * <ul>
         *   <li>1234567890</li>
         *   <li>7653385737</li>
         * </ul>
         */
        @JsonProperty(value = "accountid", required = true)
        @JsonInclude
        @NotNull
        private String accountID;
        /**
         * The currency of the withdrawal.
         */
        @JsonProperty(value = "currency", required = true)
        @JsonInclude
        @NotNull
        private String currency;
        /**
         * The estimated date and time for when the funds will be available on the receiving bank account. If this information is not available it will be null.
         */
        @JsonProperty(value = "eta")
        private String eta;
      }
    }
  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class ImportDirectDebitMandateRequest extends JsonRpcRequest<ImportDirectDebitMandateRequest.Params> {
    public ImportDirectDebitMandateRequest() {
      super("ImportDirectDebitMandate");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcRequestParams<Params.Data> {
      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends AbstractRequestData<Data.Attributes> {
        /**
         * The URL to which notifications for this should be sent to. This URL should be hard to guess and not contain a ? ("question mark").
         * <h2>Examples</h2>
         * <ul>
         *   <li>https://example.com/trustly/notification/a2b63j23dj23883jhfhfh</li>
         * </ul>
         */
        @JsonProperty(value = "NotificationURL", required = true)
        @JsonInclude
        @NotNull
        private String notificationUrl;
        /**
         * ID, username, hash or anything uniquely identifying the end-user requesting the deposit.
         * Preferably the same ID/username as used in the merchant's own backoffice in order to simplify for the merchant's support department.
         */
        @JsonProperty(value = "EndUserID", required = true)
        @JsonInclude
        @NotNull
        private String endUserId;
        /**
         * Your unique ID of the transaction.
         * <h2>Examples</h2>
         * <ul>
         *   <li>12345678</li>
         * </ul>
         */
        @JsonProperty(value = "MessageID", required = true)
        @JsonInclude
        @NotNull
        private String messageId;

        @Getter
        @Jacksonized
        @RequiredArgsConstructor
        @Setter
        @SuperBuilder
        public static class Attributes extends AbstractRequestDataAttributes {
          /**
           * The globally unique AccountID the account was assigned in our system. The AccountID of a returning customer. Allows for a quicker payment experience in some markets, see Trustly Express.
           * <h2>Examples</h2>
           * <ul>
           *   <li>1234567890</li>
           *   <li>7653385737</li>
           * </ul>
           */
          @JsonProperty(value = "AccountID")
          private String accountId;
          /**
           * This parameter in a way identifies the mandate you are to setup. If it's already used, you will receive an error, ERROR_MERCHANT_REFERENCE_ALREADY_EXISTS
           * which basically informs you that there's already a mandate with that reference.
           * <p>
           * [BACS]: The unique mandate reference. 6 - 10 characters consisting of A-Z and 0-9. Can not begin with DDIC and neither consists of the same characters, eg. AAAAAAA.
           * [Bankgiro]: The unique mandate reference. This must be numeric and unique for the payer, eg nationalId or similar can be used. Format needs to follow regexp [1-9][0-9]{5-15}
           * [SEPA-DD]: The unique mandate reference. This must be unique for the end-user for you as a merchant. Format needs to follow regexp [0-9,a-z,A-Z]{10-35}
           * <h2>Examples</h2>
           * <ul>
           *   <li>123ABC0123</li>
           * </ul>
           */
          @JsonProperty(value = "MerchantReference", required = true)
          @JsonInclude
          @NotNull
          private String merchantReference;
          @JsonProperty(value = "ImportType", required = true)
          @JsonInclude
          @NotNull
          private DirectDebitImportType importType;
          /**
           * The country where the mandate is to be created.
           */
          @JsonProperty(value = "Country", required = true)
          @JsonInclude
          @NotNull
          private String country;
          /**
           * The first name of the end user.
           * [BACS]: Only mandatory if manual entry should be enabled.
           * [Bankgiro]: Mandatory
           * [Sepa-DD]: Mandatory
           */
          @JsonProperty(value = "Firstname")
          private String firstname;
          /**
           * The last name of the end user.
           * [BACS]: Only mandatory if manual entry should be enabled.
           * [Bankgiro]: Mandatory
           * [Sepa-DD]: Mandatory
           */
          @JsonProperty(value = "Lastname")
          private String lastname;
          /**
           * The email address of the end user.
           * <h2>Examples</h2>
           * <ul>
           *   <li>test@trustly.com</li>
           * </ul>
           */
          @JsonProperty(value = "Email", required = true)
          @JsonInclude
          @NotNull
          private String email;
          /**
           * The mobile phone number to the end-user in international format. This is used for KYC and AML routines.
           */
          @JsonProperty(value = "MobilePhone")
          private String mobilePhone;
          /**
           * The text to show on the end-user's bank statement after Trustly's own 10 digit reference (which always will be displayed first). The reference must let the end user identify the merchant based on this value. So the ShopperStatement should contain either your brand name, website name, or company name.
           * <p>
           * If possible, try to keep this text as short as possible to maximise the chance that the full reference will fit into the reference field on the customer's bank since some banks allow only a limited number of characters. If the full ShopperStatement does not fit into the reference it will be truncated from the end.
           */
          @JsonProperty(value = "ShopperStatement")
          private String shopperStatement;
          /**
           * The national identification number.Format is yyyyMMddxxxx. Only applicable for Bankgiro(SE)
           * <p>
           * [Bankgiro]: To preset the nationalId to use for setting up the mandate. To make the end-user journey smooth, please use this parameter.
           */
          @JsonProperty(value = "NationalIdentificationNumber")
          private String nationalIdentificationNumber;
          /**
           * [BACS]:If flat and building is available, then use that for this field. If there's no flat/building at all, then use the street and number. Note that without AddressLine1, manual entry will be disabled.
           * [Bankgiro]: Not mandatory
           * [SepaDD]: Mandatory
           */
          @JsonProperty(value = "AddressLine1")
          private String addressLine1;
          /**
           * [BACS]:If there are flat and building information, then use addressLine2 for the street and number. Note that without AddressLine1, manual entry will be disabled.
           * [Bankgiro]: Not mandatory
           * [SepaDD]: Optional and depends on the country if needed.
           */
          @JsonProperty(value = "AddressLine2")
          private String addressLine2;
          /**
           * The city of the recipient address.
           */
          @JsonProperty(value = "AddressCity")
          private String addressCity;
          /**
           * The postalcode of the recipient address.
           */
          @JsonProperty(value = "AddressPostalCode")
          private String addressPostalCode;
          /**
           * The ISO 3166-1-alpha-2 code of the recipient address country.
           */
          @JsonProperty(value = "AddressCountry")
          private String addressCountry;

          public enum DirectDebitImportType {
            /**
             * Does NOT register the mandate with the scheme (<code>REGISTER</code> does)
             */
            CREATE("CREATE"),
            /**
             * Registers the mandate with the scheme
             */
            REGISTER("REGISTER");

            @JsonValue
            private final String value;

            DirectDebitImportType(String value) {
              this.value = value;
            }
          }
        }
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class ImportDirectDebitMandateResponse extends JsonRpcResponse<ImportDirectDebitMandateResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<Result.Data> {
      public Result() {
        super.method = "ImportDirectDebitMandate";
      }

      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends WithRejection<StringBoolean, Data.ImportDirectDebitReject> {
        /**
         * Reasons for not being able to import the mandate
         */
        public enum ImportDirectDebitReject {
          /**
           * AccountID not found
           */
          ERROR_ACCOUNT_NOT_FOUND("ERROR_ACCOUNT_NOT_FOUND"),
          /**
           * mandate is not allowed for this account
           */
          ERROR_IMPORT_MANDATE_NOT_ALLOWED("ERROR_IMPORT_MANDATE_NOT_ALLOWED"),
          /**
           * account can't be imported as it's blocked.
           */
          BANK_ACCOUNT_NUMBER_BLOCKED("BANK_ACCOUNT_NUMBER_BLOCKED"),
          /**
           * account is invalid
           */
          ERROR_INVALID_ACCOUNT_NUMBER("ERROR_INVALID_ACCOUNT_NUMBER"),
          /**
           * other reasons
           */
          ERROR_UNKNOWN("ERROR_UNKNOWN");

          @JsonValue
          private final String value;

          ImportDirectDebitReject(String value) {
            this.value = value;
          }
        }
      }
    }
  }

  /**
   * Generic class to describe the JsonRpc error inside an error response
   */
  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class JsonRpcError {
    @Default
    @JsonProperty(value = "code")
    private int code = -1;
    @Default
    @JsonProperty(value = "message")
    private String message = "Unknown Error";
    @JsonProperty(value = "error")
    @Valid
    private Error error;

    @JsonProperty(value = "name")
    public String getName() {
      return "JSONRPCError";
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Error extends ResponseResult<Error.Data> implements IAdditionalProperties {
      @Singular
      @JsonAnySetter
      private Map<String, Object> additionalProperties;

      public void addAdditionalProperty(String key, Object value) {
        this.additionalProperties.put(key, value);
      }

      @JsonAnyGetter
      public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
      }

      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data implements IAdditionalProperties {
        @Singular
        @JsonAnySetter
        private Map<String, Object> additionalProperties;

        public void addAdditionalProperty(String key, Object value) {
          this.additionalProperties.put(key, value);
        }

        @JsonAnyGetter
        public Map<String, Object> getAdditionalProperties() {
          return this.additionalProperties;
        }
      }
    }
  }

  /**
   * Generic class to describe the JsonRpc error response package
   */
  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class JsonRpcErrorResponse {
    @JsonProperty(value = "error", required = true)
    @JsonInclude
    @NotNull
    @Valid
    private ErrorUnknownError error;

    @JsonProperty(value = "version")
    public String getVersion() {
      return "1.1";
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class ErrorUnknownError extends JsonRpcError {

    }
  }

  /**
   * Generic class to describe the JsonRpc callback package
   */
  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class JsonRpcNotification<TParams extends JsonRpcNotificationParams<?>> {
    @JsonProperty(value = "method", required = true)
    @JsonInclude
    @NotNull
    private final String method;
    @JsonProperty(value = "params")
    @Valid
    private TParams params;

    public JsonRpcNotification(@JsonProperty(value = "method", required = true) String method) {
      this.method = method;
    }

    @JsonProperty(value = "version")
    public String getVersion() {
      return "1.1";
    }
  }

  /**
   * Generic class to describe the JsonRpc callback params
   */
  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class JsonRpcNotificationParams<T> {
    /**
     * The signature that the Trustly server generated, which you can verify against with our public key
     */
    @JsonProperty(value = "signature", required = true)
    @JsonInclude
    @NotNull
    private String signature;
    /**
     * The unique identifier for this request
     */
    @JsonProperty(value = "uuid", required = true)
    @JsonInclude
    @NotNull
    private String UUID;
    @JsonProperty(value = "data", required = true)
    @JsonInclude
    @NotNull
    @Valid
    private T data;
  }

  /**
   * Generic class to describe the JsonRpc request package
   */
  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class JsonRpcRequest<TParams extends JsonRpcRequestParams<? extends AbstractRequestData<? extends AbstractRequestDataAttributes>>> {
    @JsonProperty(value = "method", required = true)
    @JsonInclude
    @NotNull
    private final String method;
    @JsonProperty(value = "params")
    @Valid
    private TParams params;

    public JsonRpcRequest(@JsonProperty(value = "method", required = true) String method) {
      this.method = method;
    }

    @JsonProperty(value = "version")
    public String getVersion() {
      return "1.1";
    }
  }

  /**
   * Generic class to describe the JsonRpc request params
   */
  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class JsonRpcRequestParams<T extends AbstractRequestData<? extends AbstractRequestDataAttributes>> {
    /**
     * The signature which validates your request to the Trustly server
     */
    @JsonProperty(value = "Signature", required = true)
    @JsonInclude
    @NotNull
    private String signature;
    /**
     * The unique identifier for this request
     */
    @JsonProperty(value = "UUID", required = true)
    @JsonInclude
    @NotNull
    private String uuid;
    @JsonProperty(value = "Data", required = true)
    @JsonInclude
    @NotNull
    @Valid
    private T data;
  }

  /**
   * Generic class to describe the JsonRpc response package
   */
  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class JsonRpcResponse<T extends ResponseResult<?>> {
    @JsonProperty(value = "result", required = true)
    @JsonInclude
    @NotNull
    @Valid
    private T result;

    @JsonProperty(value = "version")
    public String getVersion() {
      return "1.1";
    }
  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class KYCNotification extends JsonRpcNotification<KYCNotification.Params> {
    public KYCNotification() {
      super("kyc");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcNotificationParams<Params.Data> {
      @Getter
      @Jacksonized
      @Setter
      @SuperBuilder
      public static class Data {
        @JsonValue
        private final JsonNode _raw;
        @Valid
        private KYCDefaultNotificationData _kycDefaultNotificationData;
        @Valid
        private KYCAbortNotificationData _kycAbortNotificationData;

        @JsonCreator
        public Data(JsonNode raw) {
          this._raw = raw;
        }

        public KYCDefaultNotificationData getKycDefaultNotificationData(ObjectMapper transformer) throws JsonProcessingException {
          if (this._kycDefaultNotificationData != null) {
            return this._kycDefaultNotificationData;
          }
          return this._kycDefaultNotificationData = transformer.treeToValue(this._raw, KYCDefaultNotificationData.class);
        }

        public KYCAbortNotificationData getKycAbortNotificationData(ObjectMapper transformer) throws JsonProcessingException {
          if (this._kycAbortNotificationData != null) {
            return this._kycAbortNotificationData;
          }
          return this._kycAbortNotificationData = transformer.treeToValue(this._raw, KYCAbortNotificationData.class);
        }

        @Getter
        @Jacksonized
        @RequiredArgsConstructor
        @Setter
        @SuperBuilder
        public static class KYCAbortNotificationData extends AbstractKYCNotificationData {
          @JsonProperty(value = "abort", required = true)
          @JsonInclude
          @NotNull
          private StringBoolean abort;
          /**
           * <h2>Examples</h2>
           * <ul>
           *   <li>unverified</li>
           *   <li>underage</li>
           * </ul>
           */
          @JsonProperty(value = "abortmessage", required = true)
          @JsonInclude
          @NotNull
          private String abortmessage;
        }

        @Getter
        @Jacksonized
        @RequiredArgsConstructor
        @Setter
        @SuperBuilder
        public static class KYCDefaultNotificationData extends AbstractKYCNotificationData {

        }
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class KYCNotificationResponse extends JsonRpcResponse<KYCNotificationResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<Result.Data> {
      public Result() {
        super.method = "kyc";
      }

      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends NotificationResponseDataBase<Data.Status> {
        /**
         * <h2>Examples</h2>
         * <ul>
         *   <li>BGN: 100.00</li>
         *   <li>CZK: 100.00</li>
         *   <li>DKK: 100.00</li>
         *   <li>EUR: 100.00</li>
         *   <li>GBP: 100.00</li>
         *   <li>HRK: 100.00</li>
         *   <li>HUF: 100</li>
         *   <li>NOK: 100.00</li>
         *   <li>PLN: 100.00</li>
         *   <li>RON: 100.00</li>
         *   <li>SEK: 100.00</li>
         * </ul>
         */
        @JsonProperty(value = "limit")
        private String limit;

        public enum Status {
          OK("OK"),
          FINISH("FINISH"),
          CONTINUE("CONTINUE");

          @JsonValue
          private final String value;

          Status(String value) {
            this.value = value;
          }
        }
      }
    }
  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class MerchantSettlementRequest extends JsonRpcRequest<MerchantSettlementRequest.Params> {
    public MerchantSettlementRequest() {
      super("MerchantSettlement");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcRequestParams<Params.Data> {
      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends AbstractRequestData<AnyAttributes> {
        /**
         * Your unique ID for the payout. If the MessageID is a previously initiated P2P order then the payout will be attached to that P2P order and the amount must be equal to or lower than the previously deposited amount.
         */
        @JsonProperty(value = "MessageID", required = true)
        @JsonInclude
        @NotNull
        private String messageId;
        /**
         * The amount to send. See format in Handling currencies. Only digits. Use dot (.) as decimal separator. If the end-user holds a balance in the merchant's system then the amount must have been deducted from that balance before calling this method.
         */
        @JsonProperty(value = "Amount", required = true)
        @JsonInclude
        @NotNull
        private String amount;
        /**
         * The currency of the amount to send.
         */
        @JsonProperty(value = "Currency", required = true)
        @JsonInclude
        @NotNull
        private String currency;
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class MerchantSettlementResponse extends JsonRpcResponse<MerchantSettlementResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<Result.Data> {
      public Result() {
        super.method = "MerchantSettlement";
      }

      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data implements IAdditionalProperties {
        /**
         * The unique reference generated for the settlement.
         */
        @JsonProperty(value = "reference", required = true)
        @JsonInclude
        @NotNull
        private String reference;
        @Singular
        @JsonAnySetter
        private Map<String, Object> additionalProperties;

        public void addAdditionalProperty(String key, Object value) {
          this.additionalProperties.put(key, value);
        }

        @JsonAnyGetter
        public Map<String, Object> getAdditionalProperties() {
          return this.additionalProperties;
        }
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class NotificationResponseDataBase<T> {
    @JsonProperty(value = "message")
    private String message;
    @JsonProperty(value = "status", required = true)
    @JsonInclude
    @NotNull
    @Valid
    private T status;
  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class PayoutConfirmationNotification extends JsonRpcNotification<PayoutConfirmationNotification.Params> {
    public PayoutConfirmationNotification() {
      super("payoutconfirmation");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcNotificationParams<Params.Data> {
      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends AbstractNotificationRequestData {
        /**
         * {@code 98.02}
         */
        @JsonProperty(value = "amount", required = true)
        @JsonInclude
        @NotNull
        private double amount;
        /**
         * The ISO 4217 code of the currency. See <a href="https://eu.developers.trustly.com/doc/reference/handling-currencies">documentation</a>
         * <h2>Examples</h2>
         * <ul>
         *   <li>BGN</li>
         *   <li>CZK</li>
         *   <li>DKK</li>
         *   <li>EUR</li>
         *   <li>GBP</li>
         *   <li>HRK</li>
         *   <li>HUF</li>
         *   <li>NOK</li>
         *   <li>PLN</li>
         *   <li>RON</li>
         *   <li>SEK</li>
         * </ul>
         */
        @JsonProperty(value = "currency", required = true)
        @JsonInclude
        private String currency;
        /**
         * ID, username, hash or anything uniquely identifying the end-user requesting the deposit.
         * Preferably the same ID/username as used in the merchant's own backoffice in order to simplify for the merchant's support department.
         */
        @JsonProperty(value = "enduserid", required = true)
        @JsonInclude
        @NotNull
        private String endUserID;
        /**
         * The time of the transaction and the GMT offset (+01 means GMT + 1 hours).
         * <h2>Examples</h2>
         * <ul>
         *   <li>2014-01-30 13:28:45.652299+01</li>
         *   <li>2014-03-31 11:50:06.46106+00</li>
         * </ul>
         */
        @JsonProperty(value = "timestamp", required = true)
        @JsonInclude
        @NotNull
        private String timestamp;
        @JsonProperty(value = "attributes")
        @Valid
        private AnyAttributes attributes;
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class PayoutConfirmationNotificationResponse extends JsonRpcResponse<PayoutConfirmationNotificationResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<AckData> {
      public Result() {
        super.method = "payoutconfirmation";
      }
    }
  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class PayoutFailedNotification extends JsonRpcNotification<PayoutFailedNotification.Params> {
    public PayoutFailedNotification() {
      super("payoutfailed");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcNotificationParams<Params.Data> {
      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends AbstractNotificationRequestData {
        /**
         * {@code 98.02}
         */
        @JsonProperty(value = "amount", required = true)
        @JsonInclude
        @NotNull
        private double amount;
        /**
         * The ISO 4217 code of the currency. See <a href="https://eu.developers.trustly.com/doc/reference/handling-currencies">documentation</a>
         * <h2>Examples</h2>
         * <ul>
         *   <li>BGN</li>
         *   <li>CZK</li>
         *   <li>DKK</li>
         *   <li>EUR</li>
         *   <li>GBP</li>
         *   <li>HRK</li>
         *   <li>HUF</li>
         *   <li>NOK</li>
         *   <li>PLN</li>
         *   <li>RON</li>
         *   <li>SEK</li>
         * </ul>
         */
        @JsonProperty(value = "currency", required = true)
        @JsonInclude
        private String currency;
        /**
         * ID, username, hash or anything uniquely identifying the end-user requesting the deposit.
         * Preferably the same ID/username as used in the merchant's own backoffice in order to simplify for the merchant's support department.
         */
        @JsonProperty(value = "enduserid", required = true)
        @JsonInclude
        @NotNull
        private String endUserID;
        /**
         * The time of the transaction and the GMT offset (+01 means GMT + 1 hours).
         * <h2>Examples</h2>
         * <ul>
         *   <li>2014-01-30 13:28:45.652299+01</li>
         *   <li>2014-03-31 11:50:06.46106+00</li>
         * </ul>
         */
        @JsonProperty(value = "timestamp", required = true)
        @JsonInclude
        @NotNull
        private String timestamp;
        /**
         * Short code of the error
         * <h2>Examples</h2>
         * <ul>
         *   <li>incorrect_account_number</li>
         * </ul>
         */
        @JsonProperty(value = "errorcode")
        private String errorcode;
        /**
         * Description of the error
         * <h2>Examples</h2>
         * <ul>
         *   <li>Format of the account number specified is not correct</li>
         * </ul>
         */
        @JsonProperty(value = "errormessage")
        private String errormessage;
        @JsonProperty(value = "attributes")
        @Valid
        private AnyAttributes attributes;
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class PayoutFailedNotificationResponse extends JsonRpcResponse<PayoutFailedNotificationResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<AckData> {
      public Result() {
        super.method = "payoutfailed";
      }
    }
  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class PendingDirectCreditNotification extends JsonRpcNotification<PendingDirectCreditNotification.Params> {
    public PendingDirectCreditNotification() {
      super("pending");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcNotificationParams<Params.Data> {
      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends AbstractPendingNotificationData {
        /**
         * ID, username, hash or anything uniquely identifying the end-user requesting the deposit.
         * Preferably the same ID/username as used in the merchant's own backoffice in order to simplify for the merchant's support department.
         */
        @JsonProperty(value = "enduserid")
        private String endUserID;
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class PendingDirectCreditNotificationResponse extends JsonRpcResponse<PendingDirectCreditNotificationResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<AckData> {
      public Result() {
        super.method = "pending";
      }
    }
  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class PendingDirectDebitNotification extends JsonRpcNotification<PendingDirectDebitNotification.Params> {
    public PendingDirectDebitNotification() {
      super("pending");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcNotificationParams<Params.Data> {
      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends AbstractPendingNotificationData {
        /**
         * ID, username, hash or anything uniquely identifying the end-user requesting the deposit.
         * Preferably the same ID/username as used in the merchant's own backoffice in order to simplify for the merchant's support department.
         */
        @JsonProperty(value = "enduserid")
        private String endUserID;
        /**
         * The globally unique AccountID the account was assigned in our system. The AccountID of a returning customer. Allows for a quicker payment experience in some markets, see Trustly Express.
         * <h2>Examples</h2>
         * <ul>
         *   <li>1234567890</li>
         *   <li>7653385737</li>
         * </ul>
         */
        @JsonProperty(value = "accountid")
        private String accountID;
        /**
         * The expected payment date for the debit. Note that this gives you an indication if the paymentDate you submitted has been adjusted eg due to a bank holiday.
         * From PendingDirectDebitNotificationData
         * <p>
         * The expected payment date for the debit. Note that this gives you an indication if the paymentDate you submitted has been adjusted eg due to a bank holiday.
         */
        @JsonProperty(value = "paymentdate")
        private String paymentdate;
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class PendingDirectDebitNotificationResponse extends JsonRpcResponse<PendingDirectDebitNotificationResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<AckData> {
      public Result() {
        super.method = "pending";
      }
    }
  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class PendingDirectPaymentBatchNotification extends JsonRpcNotification<PendingDirectPaymentBatchNotification.Params> {
    public PendingDirectPaymentBatchNotification() {
      super("pending");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcNotificationParams<Params.Data> {
      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends AbstractPendingNotificationData {

      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class PendingDirectPaymentBatchNotificationResponse extends JsonRpcResponse<PendingDirectPaymentBatchNotificationResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<AckData> {
      public Result() {
        super.method = "pending";
      }
    }
  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class PendingNotification extends JsonRpcNotification<PendingNotification.Params> {
    public PendingNotification() {
      super("pending");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcNotificationParams<Params.Data> {
      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends AbstractPendingNotificationData {
        /**
         * ID, username, hash or anything uniquely identifying the end-user requesting the deposit.
         * Preferably the same ID/username as used in the merchant's own backoffice in order to simplify for the merchant's support department.
         */
        @JsonProperty(value = "enduserid")
        private String endUserID;
        /**
         * The globally unique AccountID the account was assigned in our system. The AccountID of a returning customer. Allows for a quicker payment experience in some markets, see Trustly Express.
         * From PendingDirectDebitNotificationData
         * <p>
         * The globally unique AccountID the account was assigned in our system. The AccountID of a returning customer. Allows for a quicker payment experience in some markets, see Trustly Express.
         * <h2>Examples</h2>
         * <ul>
         *   <li>1234567890</li>
         *   <li>7653385737</li>
         * </ul>
         */
        @JsonProperty(value = "accountid")
        private String accountID;
        /**
         * The expected payment date for the debit. Note that this gives you an indication if the paymentDate you submitted has been adjusted eg due to a bank holiday.
         * From PendingDirectDebitNotificationData
         * <p>
         * The expected payment date for the debit. Note that this gives you an indication if the paymentDate you submitted has been adjusted eg due to a bank holiday.
         */
        @JsonProperty(value = "paymentdate")
        private String paymentdate;
        /**
         * Flag indicating that this is for Direct Debit refund. Note that this flag is not sent unless it's for a refund. Only value will be 1.
         * From PendingRefundDirectDebitNotificationData
         * <p>
         * Flag indicating that this is for Direct Debit refund. Note that this flag is not sent unless it's for a refund. Only value will be 1.
         */
        @JsonProperty(value = "refund")
        private String refund;
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class PendingNotificationResponse extends JsonRpcResponse<PendingNotificationResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<AckData> {
      public Result() {
        super.method = "pending";
      }
    }
  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class PendingRefundDirectDebitNotification extends JsonRpcNotification<PendingRefundDirectDebitNotification.Params> {
    public PendingRefundDirectDebitNotification() {
      super("pending");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcNotificationParams<Params.Data> {
      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends AbstractPendingNotificationData {
        /**
         * ID, username, hash or anything uniquely identifying the end-user requesting the deposit.
         * Preferably the same ID/username as used in the merchant's own backoffice in order to simplify for the merchant's support department.
         */
        @JsonProperty(value = "enduserid")
        private String endUserID;

        /**
         * Flag indicating that this is for Direct Debit refund. Note that this flag is not sent unless it's for a refund. Only value will be 1.
         */
        @JsonProperty(value = "refund")
        public String getRefund() {
          return "1";
        }
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class PendingRefundDirectDebitNotificationResponse extends JsonRpcResponse<PendingRefundDirectDebitNotificationResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<AckData> {
      public Result() {
        super.method = "pending";
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class RecipientOrSenderInformation {
    /**
     * Partytype can be <code>PERSON</code> or <code>ORGANISATION</code> (if the recipient or ultimate debtor is an organisation/company).
     */
    @JsonProperty(value = "Partytype", required = true)
    @JsonInclude
    @NotNull
    private PartyTypeKind partytype;
    /**
     * First name of the person, or the name of the organization/company.
     */
    @JsonProperty(value = "Firstname", required = true)
    @JsonInclude
    @NotNull
    private String firstname;
    /**
     * Last name of the person (NULL/empty for organization/company).
     */
    @JsonProperty(value = "Lastname", required = true)
    @JsonInclude
    private String lastname;
    /**
     * The ISO 3166-1-alpha-2 code of the country that the recipient resides in.
     */
    @JsonProperty(value = "CountryCode", required = true)
    @JsonInclude
    @NotNull
    private String countryCode;
    /**
     * Payment account number or an alternative consistent unique identifier (e.g.customer number). Note: this is not a transaction ID or similar. This identifier must stay consistent across all transactions  relating to this recipient (payee).
     */
    @JsonProperty(value = "CustomerID")
    private String customerId;
    /**
     * Full address of the recipient, excluding the country.
     */
    @JsonProperty(value = "Address")
    private String address;
    /**
     * Date of birth (YYYY-MM-DD, ISO 8601) of the beneficiary, or organisational number for the organisation.
     * <p>
     * Date string in the ISO 8601 format (YYYY-MM-DD)
     * <h2>Examples</h2>
     * <ul>
     *   <li>2014-04-01</li>
     * </ul>
     */
    @JsonProperty(value = "DateOfBirth")
    private String dateOfBirth;

    public enum PartyTypeKind {
      PERSON("PERSON"),
      ORGANISATION("ORGANISATION");

      @JsonValue
      private final String value;

      PartyTypeKind(String value) {
        this.value = value;
      }
    }
  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class RefundDirectDebitRequest extends JsonRpcRequest<RefundDirectDebitRequest.Params> {
    public RefundDirectDebitRequest() {
      super("RefundDirectDebit");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcRequestParams<Params.Data> {
      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends AbstractRequestData<AnyAttributes> {
        /**
         * The globally unique OrderID the charge order was assigned in our system. The order has no end-user interaction; it is merely used as a reference for the notifications delivered regarding the charge.
         * <h2>Examples</h2>
         * <ul>
         *   <li>9594811343</li>
         * </ul>
         */
        @JsonProperty(value = "OrderID", required = true)
        @JsonInclude
        @NotNull
        private long orderId;
        /**
         * Your unique ID of the transaction.
         * <h2>Examples</h2>
         * <ul>
         *   <li>12345678</li>
         * </ul>
         */
        @JsonProperty(value = "MessageID", required = true)
        @JsonInclude
        @NotNull
        private String messageId;
        /**
         * The amount to deposit with exactly two decimals in the currency specified by Currency. Do not use this attribute in combination with<code>suggestedMinAmount</code> and <code>suggestedMaxAmount</code>. Only digits. Use dot (.) as decimal separator.
         */
        @JsonProperty(value = "Amount", required = true)
        @JsonInclude
        @NotNull
        private String amount;
        /**
         * The ISO 4217 code of the currency. See <a href="https://eu.developers.trustly.com/doc/reference/handling-currencies">documentation</a>
         * <h2>Examples</h2>
         * <ul>
         *   <li>BGN</li>
         *   <li>CZK</li>
         *   <li>DKK</li>
         *   <li>EUR</li>
         *   <li>GBP</li>
         *   <li>HRK</li>
         *   <li>HUF</li>
         *   <li>NOK</li>
         *   <li>PLN</li>
         *   <li>RON</li>
         *   <li>SEK</li>
         * </ul>
         */
        @JsonProperty(value = "Currency", required = true)
        @JsonInclude
        private String currency;
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class RefundDirectDebitResponse extends JsonRpcResponse<RefundDirectDebitResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<Result.Data> {
      public Result() {
        super.method = "RefundDirectDebit";
      }

      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends WithRejection<StringBoolean, Data.RefundDirectDebitRejected> {
        public enum RefundDirectDebitRejected {
          /**
           * The debit order wasn't found.
           */
          ERROR_CHARGE_NOT_FOUND("ERROR_CHARGE_NOT_FOUND"),
          /**
           * The debit order is not refundable
           */
          ERROR_CHARGE_NOT_REFUNDABLE("ERROR_CHARGE_NOT_REFUNDABLE"),
          /**
           * The amount is not valid, eg could be bigger than the debit.
           */
          ERROR_AMOUNT_FAILURE("ERROR_AMOUNT_FAILURE"),
          /**
           * The currency does not match the original debit order.
           */
          ERROR_CURRENCY_FAILURE("ERROR_CURRENCY_FAILURE");

          @JsonValue
          private final String value;

          RefundDirectDebitRejected(String value) {
            this.value = value;
          }
        }
      }
    }
  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class RefundRequest extends JsonRpcRequest<RefundRequest.Params> {
    public RefundRequest() {
      super("Refund");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcRequestParams<Params.Data> {
      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends AbstractRequestData<Data.Attributes> implements IAdditionalProperties {
        /**
         * The OrderID of the initial deposit.
         */
        @JsonProperty(value = "OrderID", required = true)
        @JsonInclude
        @NotNull
        private String orderId;
        /**
         * The amount to refund the customer with exactly two decimals. Only digits. Use dot (.) as decimal separator.
         */
        @JsonProperty(value = "Amount", required = true)
        @JsonInclude
        @NotNull
        private String amount;
        /**
         * The currency of the amount to refund the customer.
         */
        @JsonProperty(value = "Currency", required = true)
        @JsonInclude
        @NotNull
        private String currency;
        @Singular
        @JsonAnySetter
        private Map<String, Object> additionalProperties;

        public void addAdditionalProperty(String key, Object value) {
          this.additionalProperties.put(key, value);
        }

        @JsonAnyGetter
        public Map<String, Object> getAdditionalProperties() {
          return this.additionalProperties;
        }

        @Getter
        @Jacksonized
        @RequiredArgsConstructor
        @Setter
        @SuperBuilder
        public static class Attributes extends AbstractRequestDataAttributes {
          /**
           * The ExternalReference is a reference set by the merchant for any purpose and does not need to be unique for every API call. For example, it can be used for invoice references, OCR numbers and also for offering end users the option to part-pay an invoice using the same ExternalReference. The ExternalReference will be included in version 1.2 of the settlement report, <code>ViewAutomaticSettlementDetailsCSV</code>.
           * <h2>Examples</h2>
           * <ul>
           *   <li>32423534523</li>
           * </ul>
           */
          @JsonProperty(value = "ExternalReference")
          private String externalReference;
        }
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class RefundResponse extends JsonRpcResponse<RefundResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<Result.Data> {
      public Result() {
        super.method = "Refund";
      }

      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data {
        /**
         * The OrderID specified when calling the method.
         */
        @JsonProperty(value = "orderid", required = true)
        @JsonInclude
        @NotNull
        private String orderID;
        /**
         * "1" if the refund request is accepted by Trustly's system. If the refund request is not accepted, you will get an error code back in  <code>JsonRpcResponse</code> -&gt; <code>error</code>.
         */
        @JsonProperty(value = "result", required = true)
        @JsonInclude
        @NotNull
        private StringBoolean result;
      }
    }
  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class RegisterAccountPayoutRequest extends JsonRpcRequest<RegisterAccountPayoutRequest.Params> {
    public RegisterAccountPayoutRequest() {
      super("RegisterAccountPayout");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcRequestParams<Params.Data> {
      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends AbstractRequestData<Data.Attributes> implements IAdditionalProperties {
        /**
         * ID, username, hash or anything uniquely identifying the end-user requesting the deposit.
         * Preferably the same ID/username as used in the merchant's own backoffice in order to simplify for the merchant's support department.
         */
        @JsonProperty(value = "EndUserID", required = true)
        @JsonInclude
        @NotNull
        private String endUserId;
        /**
         * The clearing house of the end-user's bank account. Typically the name of a country in uppercase letters. See examples or table at https://developers.trustly.com/emea/docs/registeraccount.
         * <h2>Examples</h2>
         * <ul>
         *   <li>AUSTRIA</li>
         *   <li>BELGIUM</li>
         *   <li>BULGARIA</li>
         *   <li>CROATIA</li>
         *   <li>CYPRUS</li>
         *   <li>CZECH_REPUBLIC</li>
         *   <li>DENMARK</li>
         *   <li>ESTONIA</li>
         *   <li>FINLAND</li>
         *   <li>FRANCE</li>
         *   <li>GERMANY</li>
         *   <li>GREECE</li>
         *   <li>HUNGARY</li>
         *   <li>IRELAND</li>
         *   <li>ITALY</li>
         *   <li>LATVIA</li>
         *   <li>LITHUANIA</li>
         *   <li>LUXEMBOURG</li>
         *   <li>MALTA</li>
         *   <li>NETHERLANDS</li>
         *   <li>NORWAY</li>
         *   <li>POLAND</li>
         *   <li>PORTUGAL</li>
         *   <li>ROMANIA</li>
         *   <li>SLOVAKIA</li>
         *   <li>SLOVENIA</li>
         *   <li>SPAIN</li>
         *   <li>SWEDEN</li>
         *   <li>UNITED_KINGDOM</li>
         * </ul>
         */
        @JsonProperty(value = "ClearingHouse", required = true)
        @JsonInclude
        @NotNull
        private String clearingHouse;
        /**
         * The bank number identifying the end-user's bank in the given clearing house. For bank accounts in IBAN format you should just provide an empty string (""). For non-IBAN format, see examples. The BankNumber for Swedish bank accounts should be the local "clearing number", and the AccountNumber parameter should contain the rest of the account number. Most Swedish banks have a 4-digit clearing number, but a 5-digit clearing number is used for Swedbank accounts when the clearing number starts with "8". Nordea accounts where the account number is the same as the person's national identification number always has "3300" as the clearing number.
         * <p>
         * IBAN for Swedish bank accounts is supported upon request. When making API calls with Swedish IBAN, ensure to include the Clearinghouse attribute as "IBAN" instead of "SWEDEN". See more at https://developers.trustly.com/emea/docs/registeraccount
         * <h2>Examples</h2>
         * <ul>
         *   <li>Sweden: ^[0-9]{4,5}$</li>
         *   <li>United Kingdom: ^[0-9]{6}$</li>
         * </ul>
         */
        @JsonProperty(value = "BankNumber", required = true)
        @JsonInclude
        @NotNull
        private String bankNumber;
        /**
         * The account number, identifying the end-user's account in the bank. Can be either IBAN or country-specific format, see examples or read more at https://developers.trustly.com/emea/docs/registeraccount
         * <h2>Examples</h2>
         * <ul>
         *   <li>6112</li>
         *   <li>391124057</li>
         *   <li>AUSTRIA: ^AT[0-9]{18}$</li>
         *   <li>BELGIUM: ^BE[0-9]{14}$</li>
         *   <li>BULGARIA: ^BG[0-9]{2}[A-Z]{4}[0-9]{4}[0-9]{2}[A-Z0-9]{8}$</li>
         *   <li>CROATIA: ^HR[0-9]{2}[0-9]{7}[0-9]{10}$</li>
         *   <li>CYPRUS: ^CY[0-9]{10}[0-9A-Z]{16}$</li>
         *   <li>CZECH_REPUBLIC: ^CZ[0-9]{22}$</li>
         *   <li>DENMARK: ^DK[0-9]{16}$</li>
         *   <li>ESTONIA: ^EE[0-9]{18}$</li>
         *   <li>FINLAND: ^FI[0-9]{16}$</li>
         *   <li>FRANCE: ^FR[0-9]{12}[0-9A-Z]{11}[0-9]{2}$</li>
         *   <li>GERMANY: ^DE[0-9]{20}$</li>
         *   <li>GREECE: ^GR[0-9]{25}$</li>
         *   <li>HUNGARY: ^HU[0-9]{26}$</li>
         *   <li>IRELAND: ^IE[0-9]{2}[A-Z]{4}[0-9]{14}$</li>
         *   <li>ITALY: ^IT[0-9]{2}[A-Z][0-9]{10}[0-9A-Z]{12}$</li>
         *   <li>LATVIA: ^LV[0-9]{2}[A-Z]{4}[0-9A-Z]{13}$</li>
         *   <li>LITHUANIA: ^LT[0-9]{18}$</li>
         *   <li>LUXEMBOURG: ^LU[0-9]{18}$</li>
         *   <li>MALTA: ^MT[0-9]{2}[A-Z]{4}[0-9]{5}[0-9A-Z]{18}$</li>
         *   <li>NETHERLANDS: ^NL[0-9]{2}[A-Z]{4}[0-9]{10}$</li>
         *   <li>NORWAY: ^NO[0-9]{13}$</li>
         *   <li>POLAND: ^PL[0-9]{26}$</li>
         *   <li>PORTUGAL: ^PT[0-9]{23}$</li>
         *   <li>ROMANIA: ^RO[0-9]{2}[A-Z]{4}[0-9A-Z]{16}$</li>
         *   <li>SLOVAKIA: ^SK[0-9]{22}$</li>
         *   <li>SLOVENIA: ^SI56[0-9]{15}$</li>
         *   <li>SPAIN: ^ES[0-9]{22}$</li>
         *   <li>SWEDEN:* [0-9]{1,15}$</li>
         *   <li>UNITED_KINGDOM: ^[0-9]{8}$</li>
         * </ul>
         */
        @JsonProperty(value = "AccountNumber", required = true)
        @JsonInclude
        @NotNull
        private String accountNumber;
        /**
         * First name of the person, or the name of the organization/company.
         */
        @JsonProperty(value = "Firstname", required = true)
        @JsonInclude
        @NotNull
        private String firstname;
        /**
         * Last name of the person (NULL/empty for organization/company).
         */
        @JsonProperty(value = "Lastname", required = true)
        @JsonInclude
        private String lastname;
        /**
         * The URL to which notifications for this should be sent to. This URL should be hard to guess and not contain a ? ("question mark").
         * <h2>Examples</h2>
         * <ul>
         *   <li>https://example.com/trustly/notification/a2b63j23dj23883jhfhfh</li>
         * </ul>
         */
        @JsonProperty(value = "NotificationURL", required = true)
        @JsonInclude
        @NotNull
        private String notificationUrl;
        /**
         * Your unique ID for the payout. If the MessageID is a previously initiated P2P order then the payout will be attached to that P2P order and the amount must be equal to or lower than the previously deposited amount.
         */
        @JsonProperty(value = "MessageID", required = true)
        @JsonInclude
        @NotNull
        private String messageId;
        /**
         * The amount to send with exactly two decimals. Only digits. Use dot (.) as decimal separator. If the end-user holds a balance in the merchant's system then the amount must have been deducted from that balance before calling this method.
         */
        @JsonProperty(value = "Amount", required = true)
        @JsonInclude
        @NotNull
        private String amount;
        /**
         * The ISO 4217 code of the currency. See <a href="https://eu.developers.trustly.com/doc/reference/handling-currencies">documentation</a>
         * <h2>Examples</h2>
         * <ul>
         *   <li>BGN</li>
         *   <li>CZK</li>
         *   <li>DKK</li>
         *   <li>EUR</li>
         *   <li>GBP</li>
         *   <li>HRK</li>
         *   <li>HUF</li>
         *   <li>NOK</li>
         *   <li>PLN</li>
         *   <li>RON</li>
         *   <li>SEK</li>
         * </ul>
         */
        @JsonProperty(value = "Currency", required = true)
        @JsonInclude
        private String currency;
        @Singular
        @JsonAnySetter
        private Map<String, Object> additionalProperties;

        public void addAdditionalProperty(String key, Object value) {
          this.additionalProperties.put(key, value);
        }

        @JsonAnyGetter
        public Map<String, Object> getAdditionalProperties() {
          return this.additionalProperties;
        }

        @Getter
        @Jacksonized
        @RequiredArgsConstructor
        @Setter
        @SuperBuilder
        public static class Attributes extends AbstractRequestDataAttributes {
          /**
           * The text to show on the end-user's bank statement after Trustly's own 10 digit reference (which always will be displayed first). The reference must let the end user identify the merchant based on this value. So the ShopperStatement should contain either your brand name, website name, or company name.
           * <p>
           * If possible, try to keep this text as short as possible to maximise the chance that the full reference will fit into the reference field on the customer's bank since some banks allow only a limited number of characters. If the full ShopperStatement does not fit into the reference it will be truncated from the end.
           */
          @JsonProperty(value = "ShopperStatement", required = true)
          @JsonInclude
          @NotNull
          private String shopperStatement;
          /**
           * The ExternalReference is a reference set by the merchant for any purpose and does not need to be unique for every API call. For example, it can be used for invoice references, OCR numbers and also for offering end users the option to part-pay an invoice using the same ExternalReference. The ExternalReference will be included in version 1.2 of the settlement report, <code>ViewAutomaticSettlementDetailsCSV</code>.
           * <h2>Examples</h2>
           * <ul>
           *   <li>32423534523</li>
           * </ul>
           */
          @JsonProperty(value = "ExternalReference")
          private String externalReference;
          /**
           * Human-readable identifier of the consumer-facing merchant (e.g. legal name or trade name)
           * Note: Mandatory attribute for Trustly Partners that are using Express Merchant Onboarding (EMO) and aggregate traffic under a master processing account.  It is also mandatory for E-wallets used directly in a merchant's checkout.
           * Mandatory attribute for Trustly Partners that are using Express Merchant Onboarding and aggregate traffic under a master processing account.  It is also mandatory for E-wallets used directly in a merchant's checkout, whereby the purpose of a Trustly transaction is to pay for goods/services by placing funds on the payer's e-money account ("funding stage") following an immediate transfer into the e-money account of the payee ( "payment" stage).
           */
          @JsonProperty(value = "PSPMerchant")
          private String pspMerchant;
          /**
           * URL of the consumer-facing website where the order is initiated
           * Note: Mandatory attribute for Trustly Partners that are using Express Merchant Onboarding (EMO) and aggregate traffic under a master processing account.  It is also mandatory for E-wallets used directly in a merchant's checkout.
           * Mandatory attributes for Trustly Partners that are using Express Merchant Onboarding and aggregate traffic under a master processing account.  It is also mandatory for E-wallets used directly in a merchant's checkout, whereby the purpose of a Trustly transaction is to pay for goods/services by placing funds on the payer's e-money account ("funding stage") following an immediate transfer into the e-money account of the payee ( "payment" stage).
           */
          @JsonProperty(value = "PSPMerchantURL")
          private String pspMerchantUrl;
          /**
           * VISA category codes describing the merchant's nature of business.
           * Note: Mandatory attribute for Trustly Partners that are using Express Merchant Onboarding (EMO) and aggregate traffic under a master processing account.  It is also mandatory for E-wallets used directly in a merchant's checkout.
           * Mandatory attributes for Trustly Partners that are using Express account. It is also mandatory for E-wallets used directly in a merchant's checkout, whereby the purpose of a Trustly transaction is to pay for goods/services by placing funds on the payer's e-money account ("funding stage") following an immediate transfer into the e-money account of the payee ( "payment" stage).
           */
          @JsonProperty(value = "MerchantCategoryCode")
          private String merchantCategoryCode;
          /**
           * Information about the Payer (ultimate debtor). This is required for some merchants and partners. SenderInformation is mandatory to send in Attributes{} for money transfer services (including remittance houses), e-wallets, prepaid cards, as well as for Trustly Partners that are using Express Merchant Onboarding and aggregate traffic under a master processing account (other cases may also apply).
           */
          @JsonProperty(value = "SenderInformation")
          @Valid
          private SenderInformation senderInformation;
          /**
           * The end-user's date of birth.
           */
          @JsonProperty(value = "DateOfBirth")
          private String dateOfBirth;
          /**
           * The mobile phone number to the end-user in international format. This is used for KYC and AML routines.
           */
          @JsonProperty(value = "MobilePhone")
          private String mobilePhone;
          /**
           * The end-user's social security number / personal number / birth number / etc.  Useful for some banks for identifying transactions and KYC/AML. If a Swedish personid ("personnummer") is provided, it will be pre-filled when the user logs in to their bank.
           * <h2>Examples</h2>
           * <ul>
           *   <li>790131-1234</li>
           * </ul>
           */
          @JsonProperty(value = "NationalIdentificationNumber")
          private String nationalIdentificationNumber;
          /**
           * The ISO 3166-1-alpha-2 code of the recipient address country.
           */
          @JsonProperty(value = "AddressCountry")
          private String addressCountry;
          /**
           * The postalcode of the recipient address.
           */
          @JsonProperty(value = "AddressPostalCode")
          private String addressPostalCode;
          /**
           * The city of the recipient address.
           */
          @JsonProperty(value = "AddressCity")
          private String addressCity;
          /**
           * Recipient address street
           * <h2>Examples</h2>
           * <ul>
           *   <li>Main Street 1</li>
           * </ul>
           */
          @JsonProperty(value = "AddressLine1")
          private String addressLine1;
          /**
           * Additional address information of the recipient.
           */
          @JsonProperty(value = "AddressLine2")
          private String addressLine2;
          /**
           * The entire shipping address.
           * This attribute should only be used if you are unable to provide the shipping address information in the 5 separate properties: <code>AddressCountry, </code>AddressCity<code>, </code>AddressPostalCode<code>, </code>AddressLine1, <code>AddressLine2</code>
           * <h2>Examples</h2>
           * <ul>
           *   <li>Birgerstreet 14, SE-11411, Stockholm, Sweden</li>
           * </ul>
           */
          @JsonProperty(value = "Address")
          private String address;
          /**
           * The email address of the end user.
           * <h2>Examples</h2>
           * <ul>
           *   <li>test@trustly.com</li>
           * </ul>
           */
          @JsonProperty(value = "Email")
          private String email;
        }
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class RegisterAccountPayoutResponse extends JsonRpcResponse<RegisterAccountPayoutResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<Result.Data> {
      public Result() {
        super.method = "RegisterAccountPayout";
      }

      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data {
        /**
         * The globally unique OrderID the charge order was assigned in our system. The order has no end-user interaction; it is merely used as a reference for the notifications delivered regarding the charge.
         * <h2>Examples</h2>
         * <ul>
         *   <li>9594811343</li>
         * </ul>
         */
        @JsonProperty(value = "orderid", required = true)
        @JsonInclude
        @NotNull
        private long orderID;
        /**
         * 1 if the payout could be accepted and 0 otherwise.
         */
        @JsonProperty(value = "result", required = true)
        @JsonInclude
        @NotNull
        private NumberBoolean result;
      }
    }
  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class RegisterAccountRequest extends JsonRpcRequest<RegisterAccountRequest.Params> {
    public RegisterAccountRequest() {
      super("RegisterAccount");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcRequestParams<Params.Data> {
      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends AbstractRequestData<Data.Attributes> {
        /**
         * ID, username, hash or anything uniquely identifying the end-user requesting the deposit.
         * Preferably the same ID/username as used in the merchant's own backoffice in order to simplify for the merchant's support department.
         */
        @JsonProperty(value = "EndUserID")
        private String endUserId;
        /**
         * The clearing house of the end-user's bank account. Typically the name of a country in uppercase letters. See examples or table at https://developers.trustly.com/emea/docs/registeraccount.
         * <h2>Examples</h2>
         * <ul>
         *   <li>AUSTRIA</li>
         *   <li>BELGIUM</li>
         *   <li>BULGARIA</li>
         *   <li>CROATIA</li>
         *   <li>CYPRUS</li>
         *   <li>CZECH_REPUBLIC</li>
         *   <li>DENMARK</li>
         *   <li>ESTONIA</li>
         *   <li>FINLAND</li>
         *   <li>FRANCE</li>
         *   <li>GERMANY</li>
         *   <li>GREECE</li>
         *   <li>HUNGARY</li>
         *   <li>IRELAND</li>
         *   <li>ITALY</li>
         *   <li>LATVIA</li>
         *   <li>LITHUANIA</li>
         *   <li>LUXEMBOURG</li>
         *   <li>MALTA</li>
         *   <li>NETHERLANDS</li>
         *   <li>NORWAY</li>
         *   <li>POLAND</li>
         *   <li>PORTUGAL</li>
         *   <li>ROMANIA</li>
         *   <li>SLOVAKIA</li>
         *   <li>SLOVENIA</li>
         *   <li>SPAIN</li>
         *   <li>SWEDEN</li>
         *   <li>UNITED_KINGDOM</li>
         * </ul>
         */
        @JsonProperty(value = "ClearingHouse", required = true)
        @JsonInclude
        @NotNull
        private String clearingHouse;
        /**
         * The bank number identifying the end-user's bank in the given clearing house. For bank accounts in IBAN format you should just provide an empty string (""). For non-IBAN format, see examples. The BankNumber for Swedish bank accounts should be the local "clearing number", and the AccountNumber parameter should contain the rest of the account number. Most Swedish banks have a 4-digit clearing number, but a 5-digit clearing number is used for Swedbank accounts when the clearing number starts with "8". Nordea accounts where the account number is the same as the person's national identification number always has "3300" as the clearing number.
         * <p>
         * IBAN for Swedish bank accounts is supported upon request. When making API calls with Swedish IBAN, ensure to include the Clearinghouse attribute as "IBAN" instead of "SWEDEN". See more at https://developers.trustly.com/emea/docs/registeraccount
         * <h2>Examples</h2>
         * <ul>
         *   <li>Sweden: ^[0-9]{4,5}$</li>
         *   <li>United Kingdom: ^[0-9]{6}$</li>
         * </ul>
         */
        @JsonProperty(value = "BankNumber", required = true)
        @JsonInclude
        @NotNull
        private String bankNumber;
        /**
         * The account number, identifying the end-user's account in the bank. Can be either IBAN or country-specific format, see examples or read more at https://developers.trustly.com/emea/docs/registeraccount
         * <h2>Examples</h2>
         * <ul>
         *   <li>6112</li>
         *   <li>391124057</li>
         *   <li>AUSTRIA: ^AT[0-9]{18}$</li>
         *   <li>BELGIUM: ^BE[0-9]{14}$</li>
         *   <li>BULGARIA: ^BG[0-9]{2}[A-Z]{4}[0-9]{4}[0-9]{2}[A-Z0-9]{8}$</li>
         *   <li>CROATIA: ^HR[0-9]{2}[0-9]{7}[0-9]{10}$</li>
         *   <li>CYPRUS: ^CY[0-9]{10}[0-9A-Z]{16}$</li>
         *   <li>CZECH_REPUBLIC: ^CZ[0-9]{22}$</li>
         *   <li>DENMARK: ^DK[0-9]{16}$</li>
         *   <li>ESTONIA: ^EE[0-9]{18}$</li>
         *   <li>FINLAND: ^FI[0-9]{16}$</li>
         *   <li>FRANCE: ^FR[0-9]{12}[0-9A-Z]{11}[0-9]{2}$</li>
         *   <li>GERMANY: ^DE[0-9]{20}$</li>
         *   <li>GREECE: ^GR[0-9]{25}$</li>
         *   <li>HUNGARY: ^HU[0-9]{26}$</li>
         *   <li>IRELAND: ^IE[0-9]{2}[A-Z]{4}[0-9]{14}$</li>
         *   <li>ITALY: ^IT[0-9]{2}[A-Z][0-9]{10}[0-9A-Z]{12}$</li>
         *   <li>LATVIA: ^LV[0-9]{2}[A-Z]{4}[0-9A-Z]{13}$</li>
         *   <li>LITHUANIA: ^LT[0-9]{18}$</li>
         *   <li>LUXEMBOURG: ^LU[0-9]{18}$</li>
         *   <li>MALTA: ^MT[0-9]{2}[A-Z]{4}[0-9]{5}[0-9A-Z]{18}$</li>
         *   <li>NETHERLANDS: ^NL[0-9]{2}[A-Z]{4}[0-9]{10}$</li>
         *   <li>NORWAY: ^NO[0-9]{13}$</li>
         *   <li>POLAND: ^PL[0-9]{26}$</li>
         *   <li>PORTUGAL: ^PT[0-9]{23}$</li>
         *   <li>ROMANIA: ^RO[0-9]{2}[A-Z]{4}[0-9A-Z]{16}$</li>
         *   <li>SLOVAKIA: ^SK[0-9]{22}$</li>
         *   <li>SLOVENIA: ^SI56[0-9]{15}$</li>
         *   <li>SPAIN: ^ES[0-9]{22}$</li>
         *   <li>SWEDEN:* [0-9]{1,15}$</li>
         *   <li>UNITED_KINGDOM: ^[0-9]{8}$</li>
         * </ul>
         */
        @JsonProperty(value = "AccountNumber", required = true)
        @JsonInclude
        @NotNull
        private String accountNumber;
        /**
         * First name of the person, or the name of the organization/company.
         */
        @JsonProperty(value = "Firstname", required = true)
        @JsonInclude
        @NotNull
        private String firstname;
        /**
         * Last name of the person (NULL/empty for organization/company).
         */
        @JsonProperty(value = "Lastname", required = true)
        @JsonInclude
        private String lastname;

        @Getter
        @Jacksonized
        @RequiredArgsConstructor
        @Setter
        @SuperBuilder
        public static class Attributes extends AbstractRequestDataAttributes {
          /**
           * The end-user's date of birth.
           */
          @JsonProperty(value = "DateOfBirth")
          private String dateOfBirth;
          /**
           * The mobile phone number to the end-user in international format. This is used for KYC and AML routines.
           */
          @JsonProperty(value = "MobilePhone")
          private String mobilePhone;
          /**
           * The end-user's social security number / personal number / birth number / etc.  Useful for some banks for identifying transactions and KYC/AML. If a Swedish personid ("personnummer") is provided, it will be pre-filled when the user logs in to their bank.
           * <h2>Examples</h2>
           * <ul>
           *   <li>790131-1234</li>
           * </ul>
           */
          @JsonProperty(value = "NationalIdentificationNumber")
          private String nationalIdentificationNumber;
          /**
           * The ISO 3166-1-alpha-2 code of the recipient address country.
           */
          @JsonProperty(value = "AddressCountry")
          private String addressCountry;
          /**
           * The postalcode of the recipient address.
           */
          @JsonProperty(value = "AddressPostalCode")
          private String addressPostalCode;
          /**
           * The city of the recipient address.
           */
          @JsonProperty(value = "AddressCity")
          private String addressCity;
          /**
           * The ISO 3166-1-alpha-2 code of the recipient address country.
           */
          @JsonProperty(value = "AddressLine1")
          private String addressLine1;
          /**
           * The ISO 3166-1-alpha-2 code of the recipient address country.
           */
          @JsonProperty(value = "AddressLine2")
          private String addressLine2;
          /**
           * The entire shipping address.
           * This attribute should only be used if you are unable to provide the shipping address information in the 5 separate properties: <code>AddressCountry, </code>AddressCity<code>, </code>AddressPostalCode<code>, </code>AddressLine1, <code>AddressLine2</code>
           * <h2>Examples</h2>
           * <ul>
           *   <li>Birgerstreet 14, SE-11411, Stockholm, Sweden</li>
           * </ul>
           */
          @JsonProperty(value = "Address")
          private String address;
          /**
           * The email address of the end user.
           * <h2>Examples</h2>
           * <ul>
           *   <li>test@trustly.com</li>
           * </ul>
           */
          @JsonProperty(value = "Email")
          private String email;
        }
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class RegisterAccountResponse extends JsonRpcResponse<RegisterAccountResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<Result.Data> {
      public Result() {
        super.method = "RegisterAccount";
      }

      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data implements IAdditionalProperties {
        /**
         * The globally unique AccountID the account was assigned in our system. The AccountID of a returning customer. Allows for a quicker payment experience in some markets, see Trustly Express.
         * <h2>Examples</h2>
         * <ul>
         *   <li>1234567890</li>
         *   <li>7653385737</li>
         * </ul>
         */
        @JsonProperty(value = "accountid")
        private String accountID;
        /**
         * The clearing house of the end-user's bank account. Typically the name of a country in uppercase letters. See examples or table at https://developers.trustly.com/emea/docs/registeraccount.
         * <h2>Examples</h2>
         * <ul>
         *   <li>AUSTRIA</li>
         *   <li>BELGIUM</li>
         *   <li>BULGARIA</li>
         *   <li>CROATIA</li>
         *   <li>CYPRUS</li>
         *   <li>CZECH_REPUBLIC</li>
         *   <li>DENMARK</li>
         *   <li>ESTONIA</li>
         *   <li>FINLAND</li>
         *   <li>FRANCE</li>
         *   <li>GERMANY</li>
         *   <li>GREECE</li>
         *   <li>HUNGARY</li>
         *   <li>IRELAND</li>
         *   <li>ITALY</li>
         *   <li>LATVIA</li>
         *   <li>LITHUANIA</li>
         *   <li>LUXEMBOURG</li>
         *   <li>MALTA</li>
         *   <li>NETHERLANDS</li>
         *   <li>NORWAY</li>
         *   <li>POLAND</li>
         *   <li>PORTUGAL</li>
         *   <li>ROMANIA</li>
         *   <li>SLOVAKIA</li>
         *   <li>SLOVENIA</li>
         *   <li>SPAIN</li>
         *   <li>SWEDEN</li>
         *   <li>UNITED_KINGDOM</li>
         * </ul>
         */
        @JsonProperty(value = "clearinghouse")
        private String clearingHouse;
        /**
         * The bank for this account
         * <h2>Examples</h2>
         * <ul>
         *   <li>SEB</li>
         *   <li>Skandiabanken</li>
         * </ul>
         */
        @JsonProperty(value = "bank")
        private String bank;
        /**
         * A text that is safe to show the enduser for identifying the account. Do not parse this text since it will be a different format for different accounts.
         * <h2>Examples</h2>
         * <ul>
         *   <li>***4057</li>
         * </ul>
         */
        @JsonProperty(value = "descriptor")
        private String descriptor;
        @Singular
        @JsonAnySetter
        private Map<String, Object> additionalProperties;

        public void addAdditionalProperty(String key, Object value) {
          this.additionalProperties.put(key, value);
        }

        @JsonAnyGetter
        public Map<String, Object> getAdditionalProperties() {
          return this.additionalProperties;
        }
      }
    }
  }

  @SuperBuilder
  @Jacksonized
  @Getter
  @RequiredArgsConstructor
  @Setter
  public static class ResponseResult<TData> {
    @JsonProperty(value = "signature", required = true)
    @JsonInclude
    @NotNull
    private String signature;
    @JsonProperty(value = "uuid", required = true)
    @JsonInclude
    @NotNull
    private String UUID;
    @JsonProperty(value = "data", required = true)
    @JsonInclude
    @NotNull
    @Valid
    private TData data;
    @JsonProperty(value = "method", required = true)
    @JsonInclude
    @NotNull
    private String method;
  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class SelectAccountRequest extends JsonRpcRequest<SelectAccountRequest.Params> {
    public SelectAccountRequest() {
      super("SelectAccount");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcRequestParams<Params.Data> {
      /**
       * https://eu.developers.trustly.com/doc/reference/selectaccount
       */
      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends AbstractRequestData<Data.Attributes> {
        /**
         * The URL to which notifications for this should be sent to. This URL should be hard to guess and not contain a ? ("question mark").
         * <h2>Examples</h2>
         * <ul>
         *   <li>https://example.com/trustly/notification/a2b63j23dj23883jhfhfh</li>
         * </ul>
         */
        @JsonProperty(value = "NotificationURL", required = true)
        @JsonInclude
        @NotNull
        private String notificationUrl;
        /**
         * ID, username, hash or anything uniquely identifying the end-user requesting the deposit.
         * Preferably the same ID/username as used in the merchant's own backoffice in order to simplify for the merchant's support department.
         */
        @JsonProperty(value = "EndUserID", required = true)
        @JsonInclude
        @NotNull
        private String endUserId;
        /**
         * Your unique ID of the transaction.
         * <h2>Examples</h2>
         * <ul>
         *   <li>12345678</li>
         * </ul>
         */
        @JsonProperty(value = "MessageID", required = true)
        @JsonInclude
        @NotNull
        private String messageId;

        @Getter
        @Jacksonized
        @RequiredArgsConstructor
        @Setter
        @SuperBuilder
        public static class Attributes extends AbstractRequestDataAttributes {
          /**
           * First name of the person, or the name of the organization/company.
           */
          @JsonProperty(value = "Firstname", required = true)
          @JsonInclude
          @NotNull
          private String firstname;
          /**
           * Last name of the person (NULL/empty for organization/company).
           */
          @JsonProperty(value = "Lastname", required = true)
          @JsonInclude
          private String lastname;
          /**
           * The ISO 3166-1-alpha-2 code of the end-user's country. This will be used for pre-selecting the country for the end-user in the iframe.
           * Note: This will only have an effect for new end-users. If an end-user has done a previous order (with the same EndUserID), the country that was last used will be pre-selected.
           */
          @JsonProperty(value = "Country", required = true)
          @JsonInclude
          @NotNull
          private String country;
          /**
           * The end-users localization preference in the format language[_territory]. Language is the ISO 639-1 code and territory the ISO 3166-1-alpha-2 code.
           */
          @JsonProperty(value = "Locale", required = true)
          @JsonInclude
          @NotNull
          private String locale;
          /**
           * The text to show on the end-user's bank statement after Trustly's own 10 digit reference (which always will be displayed first). The reference must let the end user identify the merchant based on this value. So the ShopperStatement should contain either your brand name, website name, or company name.
           * <p>
           * If possible, try to keep this text as short as possible to maximise the chance that the full reference will fit into the reference field on the customer's bank since some banks allow only a limited number of characters. If the full ShopperStatement does not fit into the reference it will be truncated from the end.
           */
          @JsonProperty(value = "ShopperStatement")
          private String shopperStatement;
          /**
           * The email address of the end user.
           * <h2>Examples</h2>
           * <ul>
           *   <li>test@trustly.com</li>
           * </ul>
           */
          @JsonProperty(value = "Email")
          private String email;
          /**
           * The mobile phone number to the end-user in international format. This is used for KYC and AML routines.
           */
          @JsonProperty(value = "MobilePhone")
          private String mobilePhone;
          /**
           * The IP-address of the end-user.
           */
          @JsonProperty(value = "IP")
          private String ip;
          /**
           * The URL to which the end-user should be redirected after a successful deposit.  Do not put any logic on that page since it's not guaranteed that the end-user will in fact visit it.
           */
          @JsonProperty(value = "SuccessURL", required = true)
          @JsonInclude
          @NotNull
          private String successUrl;
          /**
           * The URL to which the end-user should be redirected after a failed  deposit. Do not put any logic on that page since it's not guaranteed that the end-user will in fact visit it.
           */
          @JsonProperty(value = "FailURL", required = true)
          @JsonInclude
          @NotNull
          private String failUrl;
          /**
           * The TemplateURL should be used if you want to design your own payment page but have it hosted on Trustly's side. The URL of your template page should be provided in this attribute in every Deposit API call. Our system will then fetch the content of your template page, insert the Trustly iframe into it and host the entire page on Trustly’s side. In the response to the Deposit request, you will receive a URL to the hosted template page which you should redirect the user to (the hosted page cannot be put inside an iframe).
           */
          @JsonProperty(value = "TemplateURL")
          private String templateUrl;
          /**
           * The html target/frame-name of the SuccessURL. Only _top, _self and _parent are supported.
           */
          @JsonProperty(value = "URLTarget")
          private UrlTarget urlTarget;
          /**
           * The end-user's social security number / personal number / birth number / etc.  Useful for some banks for identifying transactions and KYC/AML. If a Swedish personid ("personnummer") is provided, it will be pre-filled when the user logs in to their bank.
           * <h2>Examples</h2>
           * <ul>
           *   <li>790131-1234</li>
           * </ul>
           */
          @JsonProperty(value = "NationalIdentificationNumber")
          private String nationalIdentificationNumber;
          /**
           * This attribute disables the possibility to change/type in national identification number when logging in to a Swedish bank. If this attribute is sent, the attribute NationalIdentificationNumber needs to be correctly included in the request.  Note: This is only available for Swedish banks.
           */
          @JsonProperty(value = "UnchangeableNationalIdentificationNumber")
          private String unchangeableNationalIdentificationNumber;
          /**
           * If you are using Trustly from within your native iOS app, this attribute should be sent so that we can redirect the users back to your app in case an external app is used for authentication (for example Mobile Bank ID in Sweden).
           */
          @JsonProperty(value = "URLScheme")
          private String urlScheme;
          /**
           * When rendering the Trustly Checkout in a native app you are required to pass your application's url as an attribute to the order initiation request. By doing so, Trustly can redirect users back to your app after using external identification apps such as Mobile BankID: Please visit documentation site for more information. It must not be included for transactions that are not originating from an app.
           * <p>
           * NOTE! This value is only used for redirecting users back to the native app within the flows. See also SuccessURL and FailURL descriptions.
           */
          @JsonProperty(value = "ReturnToAppURL")
          private String returnToAppUrl;
          /**
           * Only for Trustly Direct Debit. Request a direct debit mandate from the selected account. 1 or 0. See section "Direct Debit Mandates" for details. If this is set to 1, then <code>email</code> is required.
           */
          @JsonProperty(value = "RequestDirectDebitMandate")
          private NumberBoolean requestDirectDebitMandate;
          /**
           * The end-user's date of birth.
           */
          @JsonProperty(value = "DateOfBirth")
          private String dateOfBirth;
          /**
           * Human-readable identifier of the consumer-facing merchant (e.g. legal name or trade name)
           * Note: Mandatory attribute for Trustly Partners that are using Express Merchant Onboarding (EMO) and aggregate traffic under a master processing account.  It is also mandatory for E-wallets used directly in a merchant's checkout.
           * Mandatory attribute for Trustly Partners that are using Express Merchant Onboarding and aggregate traffic under a master processing account.  It is also mandatory for E-wallets used directly in a merchant's checkout, whereby the purpose of a Trustly transaction is to pay for goods/services by placing funds on the payer's e-money account ("funding stage") following an immediate transfer into the e-money account of the payee ( "payment" stage).
           */
          @JsonProperty(value = "PSPMerchant")
          private String pspMerchant;
          /**
           * URL of the consumer-facing website where the order is initiated
           * Note: Mandatory attribute for Trustly Partners that are using Express Merchant Onboarding (EMO) and aggregate traffic under a master processing account.  It is also mandatory for E-wallets used directly in a merchant's checkout.
           * Mandatory attributes for Trustly Partners that are using Express Merchant Onboarding and aggregate traffic under a master processing account.  It is also mandatory for E-wallets used directly in a merchant's checkout, whereby the purpose of a Trustly transaction is to pay for goods/services by placing funds on the payer's e-money account ("funding stage") following an immediate transfer into the e-money account of the payee ( "payment" stage).
           */
          @JsonProperty(value = "PSPMerchantURL")
          private String pspMerchantUrl;
          /**
           * VISA category codes describing the merchant's nature of business.
           * Note: Mandatory attribute for Trustly Partners that are using Express Merchant Onboarding (EMO) and aggregate traffic under a master processing account.  It is also mandatory for E-wallets used directly in a merchant's checkout.
           * Mandatory attributes for Trustly Partners that are using Express account. It is also mandatory for E-wallets used directly in a merchant's checkout, whereby the purpose of a Trustly transaction is to pay for goods/services by placing funds on the payer's e-money account ("funding stage") following an immediate transfer into the e-money account of the payee ( "payment" stage).
           */
          @JsonProperty(value = "MerchantCategoryCode")
          private String merchantCategoryCode;
        }
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class SelectAccountResponse extends JsonRpcResponse<SelectAccountResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<Result.Data> {
      public Result() {
        super.method = "SelectAccount";
      }

      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data {
        /**
         * The globally unique OrderID the charge order was assigned in our system. The order has no end-user interaction; it is merely used as a reference for the notifications delivered regarding the charge.
         * <h2>Examples</h2>
         * <ul>
         *   <li>9594811343</li>
         * </ul>
         */
        @JsonProperty(value = "orderid")
        private long orderID;
        /**
         * The URL that should be loaded so that the end-user can continue with the interactive process. Please see our general guidelines around iFraming, nativeApps etc for best usability. In general, never iFrame the url for mobile devices.
         */
        @JsonProperty(value = "url")
        private String URL;
      }
    }
  }

  /**
   * Information about the Payer (ultimate debtor). This is required for some merchants and partners. SenderInformation is mandatory to send in Attributes{} for money transfer services (including remittance houses), e-wallets, prepaid cards, as well as for Trustly Partners that are using Express Merchant Onboarding and aggregate traffic under a master processing account (other cases may also apply).
   */
  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class SenderInformation extends RecipientOrSenderInformation {

  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class SettlementReportRequest extends JsonRpcRequest<SettlementReportRequest.Params> {
    public SettlementReportRequest() {
      super("SettlementReport");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcRequestParams<Params.Data> {
      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends AbstractRequestData<Data.Attributes> {
        /**
         * If the value is specified (i.e. not "null"), the system will only search for a settlement executed in that particular currency. If unspecified, settlements executed in any currency are included in the report.
         * <p>
         * The ISO 4217 code of the currency. See <a href="https://eu.developers.trustly.com/doc/reference/handling-currencies">documentation</a>
         * <h2>Examples</h2>
         * <ul>
         *   <li>BGN</li>
         *   <li>CZK</li>
         *   <li>DKK</li>
         *   <li>EUR</li>
         *   <li>GBP</li>
         *   <li>HRK</li>
         *   <li>HUF</li>
         *   <li>NOK</li>
         *   <li>PLN</li>
         *   <li>RON</li>
         *   <li>SEK</li>
         * </ul>
         */
        @JsonProperty(value = "Currency", required = true)
        @JsonInclude
        private String currency;
        /**
         * The date when the settlement was processed.
         */
        @JsonProperty(value = "SettlementDate", required = true)
        @JsonInclude
        @NotNull
        private String settlementDate;

        @Getter
        @Jacksonized
        @RequiredArgsConstructor
        @Setter
        @SuperBuilder
        public static class Attributes extends AbstractRequestDataAttributes {
          /**
           * Required. The APIVersion. Must be "1.2". We also have older versions of the report, but those should not be implemented by new merchants.
           */
          @JsonProperty(value = "APIVersion", required = true)
          @JsonInclude
          @NotNull
          private ApiVersion apiVersion;

          /**
           * Required. The APIVersion. Must be "1.2". We also have older versions of the report, but those should not be implemented by new merchants.
           */
          public enum ApiVersion {
            _1_2("1.2");

            @JsonValue
            private final String value;

            ApiVersion(String value) {
              this.value = value;
            }
          }
        }
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class SettlementReportResponse extends JsonRpcResponse<SettlementReportResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<Result.Data> {
      public Result() {
        super.method = "SettlementReport";
      }

      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data {
        @JsonProperty(value = "view_automatic_settlement_details", required = true)
        @JsonInclude
        @NotNull
        private String viewAutomaticSettlementDetails;
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class SettlementReportResponseDataEntry {
    /**
     * The account the money was transferred from (if the amount is positive), or the account the money was transferred to (if the amount is negative).
     */
    @JsonProperty(value = "accountName")
    private String accountName;
    /**
     * The monetary amount of the transaction, rounded down to two decimal places.
     */
    @JsonProperty(value = "amount")
    private double amount;
    /**
     * The three-letter currency code of the transaction.
     */
    @JsonProperty(value = "currency")
    private String currency;
    /**
     * The timestamp of the transaction, including the UTC offset. As the timestamps are always in UTC, the offset is always +00
     */
    @JsonProperty(value = "datestamp")
    private String datestamp;
    /**
     * MessageID of the order associated with the transaction, if available.
     */
    @JsonProperty(value = "messageId")
    private String messageId;
    /**
     * OrderID of the order associated with the transaction, if available.
     */
    @JsonProperty(value = "orderid")
    private String orderID;
    /**
     * The type of the order associated with the transaction, if available.Text See list of possible orderypes in the table below.
     */
    @JsonProperty(value = "orderType")
    private OrderType orderType;
    /**
     * Can be used in case there is an unknown order type that was received
     */
    @JsonProperty(value = "orderTypeString")
    private String orderTypeString;
    /**
     * The sum of all amounts of the respective currency within the report.
     */
    @JsonProperty(value = "total")
    private double total;
    /**
     * The username of the child merchant account.
     */
    @JsonProperty(value = "username")
    private String username;
    /**
     * The amount that the end user paid, if the currency is different from the requested deposit currency. For transactions where the payment currency is the same as the requested currency, this field will be empty.
     */
    @JsonProperty(value = "fxPaymentAmount")
    private double fxPaymentAmount;
    /**
     * The currency that the user paid with, if the currency is different from the requested deposit currency. For transactions where the payment currency is the same as the requested currency, this field will be empty.
     */
    @JsonProperty(value = "fxPaymentCurrency")
    private String fxPaymentCurrency;
    /**
     * The 10 digit reference that will show up on the merchant's bank statement for this automatic settlement batch. The same value will be sent on every row in the report.
     */
    @JsonProperty(value = "settlementBankWithdrawalId")
    private String settlementBankWithdrawalId;
    /**
     * Contains the ExternalReference value for Deposit, Charge, and Refund transactions if provided. Otherwise empty.
     */
    @JsonProperty(value = "externalReference")
    private String externalReference;

    /**
     * The type of the order associated with the transaction, if available.Text See list of possible orderypes in the table below.
     */
    public enum OrderType {
      /**
       * Order type for Deposit
       */
      DEPOSIT("Deposit"),
      REFUND("Refund"),
      CHARGE("Charge"),
      WITHDRAW("Withdraw"),
      ACCOUNT_PAYOUT("AccountPayout"),
      DEPOSIT_FEE("Deposit Fee"),
      REFUND_FEE("Refund Fee"),
      CHARGE_FEE("Charge Fee"),
      WITHDRAW_FEE("Withdraw Fee"),
      SETTLEMENT_FEE("Settlement Fee"),
      ACCOUNT_PAYOUT_FEE("AccountPayout Fee"),
      FAILED_REFUND("Failed Refund"),
      FAILED_REFUND_FEE("Failed Refund Fee"),
      FX("FX"),
      FLOAT_ADJUSTMENT("Float Adjustment"),
      AUTOMATIC_FLOAT_ADJUSTMENT("Automatic Float Adjustment");

      @JsonValue
      private final String value;

      OrderType(String value) {
        this.value = value;
      }
    }
  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class SwishRequest extends JsonRpcRequest<SwishRequest.Params> {
    public SwishRequest() {
      super("Swish");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcRequestParams<Params.Data> {
      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends AbstractRequestData<Data.Attributes> {
        /**
         * The URL to which notifications for this should be sent to. This URL should be hard to guess and not contain a ? ("question mark").
         * <h2>Examples</h2>
         * <ul>
         *   <li>https://example.com/trustly/notification/a2b63j23dj23883jhfhfh</li>
         * </ul>
         */
        @JsonProperty(value = "NotificationURL")
        private String notificationUrl;
        /**
         * ID, username, hash or anything uniquely identifying the end-user requesting the deposit.
         * Preferably the same ID/username as used in the merchant's own backoffice in order to simplify for the merchant's support department.
         */
        @JsonProperty(value = "EndUserID")
        private String endUserId;
        /**
         * Your unique ID of the transaction.
         * <h2>Examples</h2>
         * <ul>
         *   <li>12345678</li>
         * </ul>
         */
        @JsonProperty(value = "MessageID")
        private String messageId;

        /**
         * https://eu.developers.trustly.com/doc/reference/swish
         */
        @Getter
        @Jacksonized
        @RequiredArgsConstructor
        @Setter
        @SuperBuilder
        public static class Attributes extends AbstractRequestDataAttributes {
          /**
           * The Swish number of the payee.
           * <h2>Examples</h2>
           * <ul>
           *   <li>1231181189</li>
           * </ul>
           */
          @JsonProperty(value = "MerchantSwishNumber", required = true)
          @JsonInclude
          @NotNull
          private String merchantSwishNumber;
          @JsonProperty(value = "UseMobile")
          private StringBoolean useMobile;
          /**
           * The URL to which the end-user should be redirected after a successful deposit.  Do not put any logic on that page since it's not guaranteed that the end-user will in fact visit it.
           */
          @JsonProperty(value = "SuccessURL")
          private String successUrl;
          /**
           * The URL to which the end-user should be redirected after a failed  deposit. Do not put any logic on that page since it's not guaranteed that the end-user will in fact visit it.
           */
          @JsonProperty(value = "FailURL")
          private String failUrl;
          /**
           * The amount to deposit with exactly two decimals in the currency specified by Currency. Do not use this attribute in combination with<code>suggestedMinAmount</code> and <code>suggestedMaxAmount</code>. Only digits. Use dot (.) as decimal separator.
           */
          @JsonProperty(value = "Amount", required = true)
          @JsonInclude
          @NotNull
          private String amount;
          /**
           * The ISO 4217 code of the currency. See <a href="https://eu.developers.trustly.com/doc/reference/handling-currencies">documentation</a>
           * <h2>Examples</h2>
           * <ul>
           *   <li>BGN</li>
           *   <li>CZK</li>
           *   <li>DKK</li>
           *   <li>EUR</li>
           *   <li>GBP</li>
           *   <li>HRK</li>
           *   <li>HUF</li>
           *   <li>NOK</li>
           *   <li>PLN</li>
           *   <li>RON</li>
           *   <li>SEK</li>
           * </ul>
           */
          @JsonProperty(value = "Currency", required = true)
          @JsonInclude
          private String currency;
          /**
           * Merchant supplies a message about the payment/order. Max 50 characters. Common allowed characters are the letters a-ö, A-Ö, the numbers 0-9, and special characters !?(),.-:;
           * <h2>Examples</h2>
           * <ul>
           *   <li>Payment</li>
           * </ul>
           */
          @JsonProperty(value = "Message")
          private String message;
          /**
           * The registered cellphone number of the person that makes the payment. It can only contain numbers and has to be at least 8 and at most 15 numbers. It also needs to match the following format in order to be found in Swish: country code + cell phone number (without leading zero).
           */
          @JsonProperty(value = "MobilePhone")
          private String mobilePhone;
          /**
           * The end-user's social security number / personal number / birth number / etc.  Useful for some banks for identifying transactions and KYC/AML. If a Swedish personid ("personnummer") is provided, it will be pre-filled when the user logs in to their bank.
           * <h2>Examples</h2>
           * <ul>
           *   <li>790131-1234</li>
           * </ul>
           */
          @JsonProperty(value = "NationalIdentificationNumber")
          private String nationalIdentificationNumber;
          /**
           * Minimum age (in years) that the individual connected to the payerAlias has to be in order for the payment to be accepted. Value has to be in the range of 1 to 99.
           */
          @JsonProperty(value = "AgeLimit")
          private String ageLimit;
        }
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class SwishResponse extends JsonRpcResponse<SwishResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<Result.Data> {
      public Result() {
        super.method = "Swish";
      }

      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data {
        /**
         * The globally unique OrderID the charge order was assigned in our system. The order has no end-user interaction; it is merely used as a reference for the notifications delivered regarding the request.
         * <h2>Examples</h2>
         * <ul>
         *   <li>9594811343</li>
         * </ul>
         */
        @JsonProperty(value = "orderid", required = true)
        @JsonInclude
        @NotNull
        private String orderID;
        /**
         * The URL that should be loaded so that the end-user can continue with the interactive process. Please see our general guidelines around iFraming, nativeApps etc for best usability. In general, never iFrame the url for mobile devices.
         */
        @JsonProperty(value = "url")
        private String URL;
        @JsonProperty(value = "qrcode")
        private String qrcode;
      }
    }
  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class WithdrawRequest extends JsonRpcRequest<WithdrawRequest.Params> {
    public WithdrawRequest() {
      super("Withdraw");
    }

    @Getter
    @Jacksonized
    @RequiredArgsConstructor
    @Setter
    @SuperBuilder
    public static class Params extends JsonRpcRequestParams<Params.Data> {
      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data extends AbstractRequestData<Data.Attributes> {
        /**
         * The URL to which notifications for this should be sent to. This URL should be hard to guess and not contain a ? ("question mark").
         * <h2>Examples</h2>
         * <ul>
         *   <li>https://example.com/trustly/notification/a2b63j23dj23883jhfhfh</li>
         * </ul>
         */
        @JsonProperty(value = "NotificationURL", required = true)
        @JsonInclude
        @NotNull
        private String notificationUrl;
        /**
         * ID, username, hash or anything uniquely identifying the end-user requesting the deposit.
         * Preferably the same ID/username as used in the merchant's own backoffice in order to simplify for the merchant's support department.
         */
        @JsonProperty(value = "EndUserID", required = true)
        @JsonInclude
        @NotNull
        private String endUserId;
        /**
         * Your unique ID of the transaction.
         * <h2>Examples</h2>
         * <ul>
         *   <li>12345678</li>
         * </ul>
         */
        @JsonProperty(value = "MessageID", required = true)
        @JsonInclude
        @NotNull
        private String messageId;
        /**
         * The ISO 4217 code of the currency. See <a href="https://eu.developers.trustly.com/doc/reference/handling-currencies">documentation</a>
         * <h2>Examples</h2>
         * <ul>
         *   <li>BGN</li>
         *   <li>CZK</li>
         *   <li>DKK</li>
         *   <li>EUR</li>
         *   <li>GBP</li>
         *   <li>HRK</li>
         *   <li>HUF</li>
         *   <li>NOK</li>
         *   <li>PLN</li>
         *   <li>RON</li>
         *   <li>SEK</li>
         * </ul>
         */
        @JsonProperty(value = "Currency", required = true)
        @JsonInclude
        private String currency;

        @Getter
        @Jacksonized
        @RequiredArgsConstructor
        @Setter
        @SuperBuilder
        public static class Attributes extends AbstractRequestDataAttributes {
          /**
           * First name of the person, or the name of the organization/company.
           */
          @JsonProperty(value = "Firstname", required = true)
          @JsonInclude
          @NotNull
          private String firstname;
          /**
           * Last name of the person (NULL/empty for organization/company).
           */
          @JsonProperty(value = "Lastname", required = true)
          @JsonInclude
          private String lastname;
          /**
           * The ISO 3166-1-alpha-2 code of the end-user's country. This will be used for pre-selecting the country for the end-user in the iframe.
           * Note: This will only have an effect for new end-users. If an end-user has done a previous order (with the same EndUserID), the country that was last used will be pre-selected.
           */
          @JsonProperty(value = "Country", required = true)
          @JsonInclude
          @NotNull
          private String country;
          /**
           * The end-users localization preference in the format language[_territory]. Language is the ISO 639-1 code and territory the ISO 3166-1-alpha-2 code.
           */
          @JsonProperty(value = "Locale", required = true)
          @JsonInclude
          @NotNull
          private String locale;
          /**
           * The text to show on the end-user's bank statement after Trustly's own 10 digit reference (which always will be displayed first). The reference must let the end user identify the merchant based on this value. So the ShopperStatement should contain either your brand name, website name, or company name.
           * <p>
           * If possible, try to keep this text as short as possible to maximise the chance that the full reference will fit into the reference field on the customer's bank since some banks allow only a limited number of characters. If the full ShopperStatement does not fit into the reference it will be truncated from the end.
           */
          @JsonProperty(value = "ShopperStatement", required = true)
          @JsonInclude
          @NotNull
          private String shopperStatement;
          /**
           * The email address of the end user.
           * <h2>Examples</h2>
           * <ul>
           *   <li>test@trustly.com</li>
           * </ul>
           */
          @JsonProperty(value = "Email")
          private String email;
          /**
           * The mobile phone number to the end-user in international format. This is used for KYC and AML routines.
           */
          @JsonProperty(value = "MobilePhone")
          private String mobilePhone;
          /**
           * The IP-address of the end-user.
           */
          @JsonProperty(value = "IP")
          private String ip;
          /**
           * The URL to which the end-user should be redirected after a successful deposit.  Do not put any logic on that page since it's not guaranteed that the end-user will in fact visit it.
           */
          @JsonProperty(value = "SuccessURL", required = true)
          @JsonInclude
          @NotNull
          private String successUrl;
          /**
           * The URL to which the end-user should be redirected after a failed  deposit. Do not put any logic on that page since it's not guaranteed that the end-user will in fact visit it.
           */
          @JsonProperty(value = "FailURL", required = true)
          @JsonInclude
          @NotNull
          private String failUrl;
          /**
           * The TemplateURL should be used if you want to design your own payment page but have it hosted on Trustly's side. The URL of your template page should be provided in this attribute in every Deposit API call. Our system will then fetch the content of your template page, insert the Trustly iframe into it and host the entire page on Trustly’s side. In the response to the Deposit request, you will receive a URL to the hosted template page which you should redirect the user to (the hosted page cannot be put inside an iframe).
           */
          @JsonProperty(value = "TemplateURL")
          private String templateUrl;
          /**
           * The html target/frame-name of the SuccessURL. Only _top, _self and _parent are supported.
           */
          @JsonProperty(value = "URLTarget")
          private UrlTarget urlTarget;
          /**
           * The end-user's social security number / personal number / birth number / etc.  Useful for some banks for identifying transactions and KYC/AML. If a Swedish personid ("personnummer") is provided, it will be pre-filled when the user logs in to their bank.
           * <h2>Examples</h2>
           * <ul>
           *   <li>790131-1234</li>
           * </ul>
           */
          @JsonProperty(value = "NationalIdentificationNumber")
          private String nationalIdentificationNumber;
          /**
           * This attribute disables the possibility to change/type in national identification number when logging in to a Swedish bank. If this attribute is sent, the attribute NationalIdentificationNumber needs to be correctly included in the request.  Note: This is only available for Swedish banks.
           */
          @JsonProperty(value = "UnchangeableNationalIdentificationNumber")
          private String unchangeableNationalIdentificationNumber;
          /**
           * If you are using Trustly from within your native iOS app, this attribute should be sent so that we can redirect the users back to your app in case an external app is used for authentication (for example Mobile Bank ID in Sweden).
           */
          @JsonProperty(value = "URLScheme")
          private String urlScheme;
          /**
           * When rendering the Trustly Checkout in a native app you are required to pass your application's url as an attribute to the order initiation request. By doing so, Trustly can redirect users back to your app after using external identification apps such as Mobile BankID: Please visit documentation site for more information. It must not be included for transactions that are not originating from an app.
           * <p>
           * NOTE! This value is only used for redirecting users back to the native app within the flows. See also SuccessURL and FailURL descriptions.
           */
          @JsonProperty(value = "ReturnToAppURL")
          private String returnToAppUrl;
          /**
           * The minimum amount the end-user is allowed to deposit in the currency specified by Currency.Only digits. Use dot (.) as decimal separator.
           */
          @JsonProperty(value = "SuggestedMinAmount")
          private String suggestedMinAmount;
          /**
           * The maximum amount the end-user is allowed to deposit in the currency specified by Currency. Only digits. Use dot (.) as decimal separator.
           */
          @JsonProperty(value = "SuggestedMaxAmount")
          private String suggestedMaxAmount;
          /**
           * Sets a fixed withdrawal amount which cannot be changed by the end-user in the Trustly iframe. If this attribute is not sent, the end-user will be asked to select the withdrawal amount in the Trustly iframe. Do not use in combination with <code>suggestedMinAmount</code> and <code>suggestedMaxAmount</code>. Use dot(.) as decimal separator.
           */
          @JsonProperty(value = "SuggestedAmount")
          private String suggestedAmount;
          /**
           * The end-user's date of birth.
           */
          @JsonProperty(value = "DateOfBirth")
          private String dateOfBirth;
          /**
           * The ISO 3166-1-alpha-2 code of the recipient address country.
           */
          @JsonProperty(value = "AddressCountry")
          private String addressCountry;
          /**
           * The postalcode of the recipient address.
           */
          @JsonProperty(value = "AddressPostalCode")
          private String addressPostalCode;
          /**
           * The city of the recipient address.
           */
          @JsonProperty(value = "AddressCity")
          private String addressCity;
          /**
           * Recipient address street
           * <h2>Examples</h2>
           * <ul>
           *   <li>Main Street 1</li>
           * </ul>
           */
          @JsonProperty(value = "AddressLine1")
          private String addressLine1;
          /**
           * Additional address information of the recipient.
           */
          @JsonProperty(value = "AddressLine2")
          private String addressLine2;
          /**
           * The entire shipping address.
           * This attribute should only be used if you are unable to provide the shipping address information in the 5 separate properties: <code>AddressCountry, </code>AddressCity<code>, </code>AddressPostalCode<code>, </code>AddressLine1, <code>AddressLine2</code>
           * <h2>Examples</h2>
           * <ul>
           *   <li>Birgerstreet 14, SE-11411, Stockholm, Sweden</li>
           * </ul>
           */
          @JsonProperty(value = "Address")
          private String address;
          /**
           * The ExternalReference is a reference set by the merchant for any purpose and does not need to be unique for every API call. For example, it can be used for invoice references, OCR numbers and also for offering end users the option to part-pay an invoice using the same ExternalReference. The ExternalReference will be included in version 1.2 of the settlement report, <code>ViewAutomaticSettlementDetailsCSV</code>.
           * <h2>Examples</h2>
           * <ul>
           *   <li>32423534523</li>
           * </ul>
           */
          @JsonProperty(value = "ExternalReference")
          private String externalReference;
          /**
           * Human-readable identifier of the consumer-facing merchant (e.g. legal name or trade name)
           * Note: Mandatory attribute for Trustly Partners that are using Express Merchant Onboarding (EMO) and aggregate traffic under a master processing account.  It is also mandatory for E-wallets used directly in a merchant's checkout.
           * Mandatory attribute for Trustly Partners that are using Express Merchant Onboarding and aggregate traffic under a master processing account.  It is also mandatory for E-wallets used directly in a merchant's checkout, whereby the purpose of a Trustly transaction is to pay for goods/services by placing funds on the payer's e-money account ("funding stage") following an immediate transfer into the e-money account of the payee ( "payment" stage).
           */
          @JsonProperty(value = "PSPMerchant")
          private String pspMerchant;
          /**
           * URL of the consumer-facing website where the order is initiated
           * Note: Mandatory attribute for Trustly Partners that are using Express Merchant Onboarding (EMO) and aggregate traffic under a master processing account.  It is also mandatory for E-wallets used directly in a merchant's checkout.
           * Mandatory attributes for Trustly Partners that are using Express Merchant Onboarding and aggregate traffic under a master processing account.  It is also mandatory for E-wallets used directly in a merchant's checkout, whereby the purpose of a Trustly transaction is to pay for goods/services by placing funds on the payer's e-money account ("funding stage") following an immediate transfer into the e-money account of the payee ( "payment" stage).
           */
          @JsonProperty(value = "PSPMerchantURL")
          private String pspMerchantUrl;
          /**
           * VISA category codes describing the merchant's nature of business.
           * Note: Mandatory attribute for Trustly Partners that are using Express Merchant Onboarding (EMO) and aggregate traffic under a master processing account.  It is also mandatory for E-wallets used directly in a merchant's checkout.
           * Mandatory attributes for Trustly Partners that are using Express account. It is also mandatory for E-wallets used directly in a merchant's checkout, whereby the purpose of a Trustly transaction is to pay for goods/services by placing funds on the payer's e-money account ("funding stage") following an immediate transfer into the e-money account of the payee ( "payment" stage).
           */
          @JsonProperty(value = "MerchantCategoryCode")
          private String merchantCategoryCode;
          /**
           * Information about the Payer (ultimate debtor). This is required for some merchants and partners. SenderInformation is mandatory to send in Attributes{} for money transfer services (including remittance houses), e-wallets, prepaid cards, as well as for Trustly Partners that are using Express Merchant Onboarding and aggregate traffic under a master processing account (other cases may also apply).
           */
          @JsonProperty(value = "SenderInformation")
          @Valid
          private SenderInformation senderInformation;
        }
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class WithdrawResponse extends JsonRpcResponse<WithdrawResponse.Result> {
    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Result extends ResponseResult<Result.Data> {
      public Result() {
        super.method = "Withdraw";
      }

      @Getter
      @Jacksonized
      @RequiredArgsConstructor
      @Setter
      @SuperBuilder
      public static class Data {
        /**
         * The globally unique OrderID the charge order was assigned in our system. The order has no end-user interaction; it is merely used as a reference for the notifications delivered regarding the request.
         * <h2>Examples</h2>
         * <ul>
         *   <li>9594811343</li>
         * </ul>
         */
        @JsonProperty(value = "orderid", required = true)
        @JsonInclude
        @NotNull
        private String orderID;
        /**
         * The URL that should be loaded so that the end-user can continue with the interactive process. Please see our general guidelines around iFraming, nativeApps etc for best usability. In general, never iFrame the url for mobile devices.
         */
        @JsonProperty(value = "url", required = true)
        @JsonInclude
        @NotNull
        private String URL;
      }
    }
  }

  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class WithRejection<TResult, TRejected> {
    @JsonProperty(value = "result")
    @Valid
    private TResult result;
    @JsonProperty(value = "rejected")
    @Valid
    private TRejected rejected;
  }

  public enum CreditRefundDirectDebitCancelReason {
    /**
     * The request was declined
     */
    DECLINED("DECLINED"),
    /**
     * The request was cancelled
     */
    CANCELED("CANCELED"),
    /**
     * There was an error during the request
     */
    ERROR("ERROR"),
    /**
     * Direct Debit Mandate: Either when the consumer cancels in the journey or if you call cancel mandate method, or the payment was canceled from the api.
     */
    CANCELLED("CANCELLED"),
    /**
     * Direct Debit Mandate: If the underlying scheme reports failure or cancellation of the mandate
     */
    FAILED("FAILED"),
    /**
     * Direct Debit Mandate: If the mandate already exist
     */
    MANDATE_ALREADY_EXISTS("MANDATE_ALREADY_EXISTS"),
    /**
     * Direct Debit Mandate: If the provided accountId was incorrect
     */
    INVALID_ACCOUNT_ID("INVALID_ACCOUNT_ID"),
    /**
     * The mandate is invalid for some reason.
     */
    ERROR_MANDATE_INVALID("ERROR_MANDATE_INVALID"),
    /**
     * Ie. missing funds or being canceled by the end-user.
     */
    ERROR_CHARGE_NOT_APPROVED("ERROR_CHARGE_NOT_APPROVED"),
    /**
     * The specified account is not valid, eg sending funds to it is not possible.
     */
    INVALID_ACCOUNT("INVALID_ACCOUNT");

    @JsonValue
    private final String value;

    CreditRefundDirectDebitCancelReason(String value) {
      this.value = value;
    }
  }

  public interface IAdditionalProperties {
    Map<String, Object> getAdditionalProperties();
    void addAdditionalProperty(String key, Object value);
  }

  public enum NumberBoolean {
    /**
     * 0 for false
     */
    FALSE(0),
    /**
     * 1 for true
     */
    TRUE(1);

    @JsonValue
    private final int value;

    NumberBoolean(int value) {
      this.value = value;
    }
  }

  public enum StringBoolean {
    FALSE("0"),
    TRUE("1");

    @JsonValue
    private final String value;

    StringBoolean(String value) {
      this.value = value;
    }
  }

  /**
   * The html target/frame-name. Only _top, _self and _parent are supported.
   */
  public enum UrlTarget {
    TOP("_top"),
    SELF("_self"),
    PARENT("_parent");

    @JsonValue
    private final String value;

    UrlTarget(String value) {
      this.value = value;
    }
  }
}
