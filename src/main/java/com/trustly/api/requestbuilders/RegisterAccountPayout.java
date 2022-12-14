package com.trustly.api.requestbuilders;

import com.trustly.api.commons.Method;
import com.trustly.api.data.request.Request;
import com.trustly.api.data.request.RequestParameters;
import com.trustly.api.data.request.requestdata.RegisterAccountPayoutData;
import com.trustly.api.security.SignatureHandler;
import java.util.Map;
import java.util.TreeMap;

/**
 * Creates a RegisterAccountPayout request ready to be sent to Trustly API.
 * The constructor contains the required fields of an RegisterAccountPayoutRequest.
 *
 * Builder lets you add additional information if any is available for the given request.
 *
 * Example use for a default RegisterAccountPayout request:
 * Request registerAccountPayout = new RegisterAccountPayout.Build(...).getRequest();
 */
public class RegisterAccountPayout {

  private final Request request = new Request();

  private RegisterAccountPayout(final RegisterAccountPayout.Build builder) {
    final RequestParameters params = new RequestParameters();
    params.setUUID(SignatureHandler.generateNewUUID());
    params.setData(builder.data);

    request.setMethod(Method.REGISTER_ACCOUNT_PAYOUT);
    request.setParams(params);
  }

  public Request getRequest() { return request; }

  public static class Build {

    private final RegisterAccountPayoutData data = new RegisterAccountPayoutData();

    private final Map<String, Object> attributes = new TreeMap<>();

    public Build(
        final String endUserId,
        final String clearingHouse,
        final String bankNumber,
        final String accountNumber,
        final String firstname,
        final String lastname,
        final String notificationURL,
        final String messageId,
        final String amount,
        final String currency
    ) {
      data.setEndUserId(endUserId);
      data.setClearingHouse(clearingHouse);
      data.setBankNumber(bankNumber);
      data.setAccountNumber(accountNumber);
      data.setFirstname(firstname);
      data.setLastname(lastname);
      data.setNotificationURL(notificationURL);
      data.setMessageId(messageId);
      data.setAmount(amount);
      data.setCurrency(currency);
      data.setAttributes(attributes);
    }

    /* Attributes */
    public Build shopperStatement(final String shopperStatement) {
      attributes.put("ShopperStatement", shopperStatement);
      return this;
    }

    public Build externalReference(final String externalReference) {
      attributes.put("ExternalReference", externalReference);
      return this;
    }

    public Build pspMerchant(final String pspMerchant) {
      attributes.put("PSPMerchant", pspMerchant);
      return this;
    }

    public Build pspMerchantURL(final String pspMerchantURL) {
      attributes.put("PSPMerchantURL", pspMerchantURL);
      return this;
    }

    public Build merchantCategoryCode(final String merchantCategoryCode) {
      attributes.put("MerchantCategoryCode", merchantCategoryCode);
      return this;
    }

    public Build senderInformation(SenderInformationBuilder senderInformation) {
      attributes.put("SenderInformation", senderInformation.getSenderInformation());
      return this;
    }

    public Build dateOfBirth(final String dateOfBirth) {
      attributes.put("DateOfBirth", dateOfBirth);
      return this;
    }

    public Build mobilePhone(final String mobilePhone) {
      attributes.put("MobilePhone", mobilePhone);
      return this;
    }

    public Build nationalIdentificationNumber(final String nationalIdentificationNumber) {
      attributes.put("NationalIdentificationNumber", nationalIdentificationNumber);
      return this;
    }

    public Build addressCountry(final String addressCountry) {
      attributes.put("AddressCountry", addressCountry);
      return this;
    }

    public Build addressPostalCode(final String addressPostalCode) {
      attributes.put("AddressPostalCode", addressPostalCode);
      return this;
    }

    public Build addressCity(final String addressCity) {
      attributes.put("AddressCity", addressCity);
      return this;
    }

    public Build addressLine1(final String addressLine1) {
      attributes.put("AddressLine1", addressLine1);
      return this;
    }

    public Build addressLine2(final String addressLine2) {
      attributes.put("AddressLine2", addressLine2);
      return this;
    }

    public Build address(final String address) {
      attributes.put("Address", address);
      return this;
    }

    public Build email(final String email) {
      attributes.put("Email", email);
      return this;
    }

    /* Sender information */
    public static class SenderInformationBuilder {

      private final Map<String, Object> senderInformation = new TreeMap<>();

      public SenderInformationBuilder() {}

      public SenderInformationBuilder partytype(final String partytype) {
        senderInformation.put("Partytype", partytype);
        return this;
      }

      public SenderInformationBuilder address(final String address) {
        senderInformation.put("Address", address);
        return this;
      }

      public SenderInformationBuilder countryCode(final String countryCode) {
        senderInformation.put("CountryCode", countryCode);
        return this;
      }

      public SenderInformationBuilder firstname(final String firstname) {
        senderInformation.put("Firstname", firstname);
        return this;
      }

      public SenderInformationBuilder lastname(final String lastname) {
        senderInformation.put("Lastname", lastname);
        return this;
      }

      public SenderInformationBuilder customerID(final String customerID) {
        senderInformation.put("CustomerID", customerID);
        return this;
      }

      public SenderInformationBuilder dateOfBirth(final String dateOfBirth) {
        senderInformation.put("DateOfBirth", dateOfBirth);
        return this;
      }

      public Map<String, Object> getSenderInformation() {
        return senderInformation;
      }
    }

    public Request getRequest() { return new  RegisterAccountPayout(this).getRequest(); }
  }
}
