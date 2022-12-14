package com.trustly.api.data.request.requestdata;

import com.google.gson.annotations.SerializedName;
import com.trustly.api.data.request.RequestData;

public class RegisterAccountPayoutData extends RequestData {

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

  @SerializedName("NotificationURL")
  private String notificationURL;

  @SerializedName("MessageID")
  private String messageId;

  @SerializedName("Amount")
  private String amount;

  @SerializedName("Currency")
  private String currency;

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

  public String getNotificationURL() {
    return notificationURL;
  }

  public void setNotificationURL(String notificationURL) {
    this.notificationURL = notificationURL;
  }

  public String getMessageId() {
    return messageId;
  }

  public void setMessageId(String messageId) {
    this.messageId = messageId;
  }

  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }
}
