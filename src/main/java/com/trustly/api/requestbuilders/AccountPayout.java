package com.trustly.api.requestbuilders;

import com.google.gson.annotations.SerializedName;
import com.trustly.api.commons.Method;
import com.trustly.api.data.request.Request;
import com.trustly.api.data.request.RequestParameters;
import com.trustly.api.data.request.requestdata.AccountPayoutData;
import com.trustly.api.security.SignatureHandler;
import java.util.Map;
import java.util.TreeMap;


/**
 * Creates a AccountPayout request ready to be sent to Trustly API.
 * The constructor contains the required fields of a AccountPayout request.
 *
 * Builder lets you add additional information if any is available for the given request.
 *
 * The API specifics of the request can be found on https://developers.trustly.com/emea/docs/accountpayout
 *
 * Example use for a default AccountPayout request:
 * Request accountPayout = new AccountPayout.Build(...).getRequest();
 */
public class AccountPayout {

  private final Request request = new Request();

  private AccountPayout(final AccountPayout.Build builder) {
    final RequestParameters params = new RequestParameters();
    params.setUUID(SignatureHandler.generateNewUUID());
    params.setData(builder.data);

    request.setMethod(Method.ACCOUNT_PAYOUT);
    request.setParams(params);
  }

  public Request getRequest() {
    return request;
  }

  public static class Build {

    private final AccountPayoutData data = new AccountPayoutData();
    private final Map<String, Object> attributes = new TreeMap<>();

    @SerializedName("SenderInformation")
    private final Map<String, Object> senderInformation = new TreeMap<>();

    public Build(
        final String notificationURL,
        final String accountId,
        final String endUserId,
        final String messageId,
        final String amount,
        final String currency
    ) {
      data.setNotificationURL(notificationURL);
      data.setAccountId(accountId);
      data.setEndUserId(endUserId);
      data.setMessageId(messageId);
      data.setAmount(amount);
      data.setCurrency(currency);
      data.setSenderInformation(senderInformation);
      data.setAttributes(attributes);
    }

    /* Attributes */
    public AccountPayout.Build shopperStatement(final String shopperStatement) {
      attributes.put("ShopperStatement", shopperStatement);
      return this;
    }

    public AccountPayout.Build externalReference(final String externalReference) {
      attributes.put("ExternalReference", externalReference);
      return this;
    }

    public AccountPayout.Build pspMerchant(final String pspMerchant) {
      attributes.put("PSPMerchant", pspMerchant);
      return this;
    }

    public AccountPayout.Build pspMerchantURL(final String pspMerchantURL) {
      attributes.put("PSPMerchantURL", pspMerchantURL);
      return this;
    }

    public AccountPayout.Build merchantCategoryCode(final String merchantCategoryCode) {
      attributes.put("MerchantCategoryCode", merchantCategoryCode);
      return this;
    }

    /* Sender information */
    public AccountPayout.Build partytype(final String partytype) {
      senderInformation.put("Partytype", partytype);
      return this;
    }

    public AccountPayout.Build address(final String address) {
      senderInformation.put("Address", address);
      return this;
    }

    public AccountPayout.Build countryCode(final String countryCode) {
      senderInformation.put("CountryCode", countryCode);
      return this;
    }

    public AccountPayout.Build firstname(final String firstname) {
      senderInformation.put("Firstname", firstname);
      return this;
    }

    public AccountPayout.Build lastname(final String lastname) {
      senderInformation.put("Lastname", lastname);
      return this;
    }

    public AccountPayout.Build customerID(final String customerID) {
      senderInformation.put("CustomerID", customerID);
      return this;
    }

    public AccountPayout.Build dateOfBirth(final String dateOfBirth) {
      senderInformation.put("DateOfBirth", dateOfBirth);
      return this;
    }

    public Request getRequest() {
      return new AccountPayout(this).getRequest();
    }
  }

}
