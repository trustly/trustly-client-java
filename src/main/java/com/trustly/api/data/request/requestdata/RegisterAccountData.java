package com.trustly.api.data.request.requestdata;

import com.google.gson.annotations.SerializedName;
import com.trustly.api.data.request.RequestData;

public class RegisterAccountData extends RequestData {

  @SerializedName("EndUserID")
  private String endUserId;

  @SerializedName("ClearingHouse")
  private String clearingHouse;

  @SerializedName("BankNumber")
  private String bankNumber;

  @SerializedName("AccountNumber")
  private String accountNumber;

  @SerializedName("Firstname")
  private String firstname;

  @SerializedName("Lastname")
  private String lastname;

  public String getEndUserId() {
    return endUserId;
  }

  public void setEndUserId(String endUserId) {
    this.endUserId = endUserId;
  }

  public String getClearingHouse() {
    return clearingHouse;
  }

  public void setClearingHouse(String clearingHouse) {
    this.clearingHouse = clearingHouse;
  }

  public String getBankNumber() {
    return bankNumber;
  }

  public void setBankNumber(String bankNumber) {
    this.bankNumber = bankNumber;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }
}
