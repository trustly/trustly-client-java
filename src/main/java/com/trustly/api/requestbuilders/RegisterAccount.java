package com.trustly.api.requestbuilders;

import com.trustly.api.commons.Method;
import com.trustly.api.data.request.Request;
import com.trustly.api.data.request.RequestParameters;
import com.trustly.api.data.request.requestdata.RegisterAccountData;
import com.trustly.api.security.SignatureHandler;
import java.util.Map;
import java.util.TreeMap;

public class RegisterAccount {

  private final Request request = new Request();

  private RegisterAccount(final RegisterAccount.Build builder) {
    final RequestParameters params = new RequestParameters();
    params.setUUID(SignatureHandler.generateNewUUID());
    params.setData(builder.data);

    request.setMethod(Method.REGISTER_ACCOUNT);
    request.setParams(params);
  }

  public Request getRequest() {
    return request;
  }

  public static class Build {

    private final RegisterAccountData data = new RegisterAccountData();
    private final Map<String, Object> attributes = new TreeMap<>();

    public Build(
        final String endUserId,
        final String clearingHouse,
        final String bankNumber,
        final String accountNumber,
        final String firstname,
        final String lastname
    ) {
      data.setEndUserId(endUserId);
      data.setClearingHouse(clearingHouse);
      data.setBankNumber(bankNumber);
      data.setAccountNumber(accountNumber);
      data.setFirstname(firstname);
      data.setLastname(lastname);

      data.setAttributes(attributes);
    }

    /* Attributes */
    public RegisterAccount.Build dateOfBirth(final String dateOfBirth) {
      attributes.put("DateOfBirth", dateOfBirth);
      return this;
    }

    public RegisterAccount.Build mobilePhone(final String mobilePhone) {
      attributes.put("MobilePhone", mobilePhone);
      return this;
    }

    public RegisterAccount.Build nationalIdentificationNumber(final String nationalIdentificationNumber) {
      attributes.put("NationalIdentificationNumber", nationalIdentificationNumber);
      return this;
    }

    public RegisterAccount.Build addressCountry(final String addressCountry) {
      attributes.put("AddressCountry", addressCountry);
      return this;
    }

    public RegisterAccount.Build addressPostalCode(final String addressPostalCode) {
      attributes.put("AddressPostalCode", addressPostalCode);
      return this;
    }

    public RegisterAccount.Build addressCity(final String addressCity) {
      attributes.put("AddressCity", addressCity);
      return this;
    }

    public RegisterAccount.Build addressLine1(final String addressLine1) {
      attributes.put("AddressLine1", addressLine1);
      return this;
    }

    public RegisterAccount.Build addressLine2(final String addressLine2) {
      attributes.put("AddressLine2", addressLine2);
      return this;
    }

    public RegisterAccount.Build address(final String address) {
      attributes.put("Address", address);
      return this;
    }

    public RegisterAccount.Build email(final String email) {
      attributes.put("Email", email);
      return this;
    }

    public Request getRequest() {
      return new RegisterAccount(this).getRequest();
    }
  }

}
