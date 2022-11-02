package com.trustly.api.data.request.requestdata;

import com.google.gson.annotations.SerializedName;
import com.trustly.api.data.request.RequestData;

public class AccountPayoutData extends RequestData {

  @SerializedName("NotificationURL")
  private String notificationURL;

  @SerializedName("AccountID")
  private String accountId;

  @SerializedName("EndUserID")
  private String endUserId;

  @SerializedName("MessageID")
  private String messageId;

  @SerializedName("Amount")
  private String amount;

  @SerializedName("Currency")
  private String currency;

  public String getNotificationURL() {
    return notificationURL;
  }

  public void setNotificationURL(String notificationURL) {
    this.notificationURL = notificationURL;
  }

  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  public String getEndUserId() {
    return endUserId;
  }

  public void setEndUserId(String endUserId) {
    this.endUserId = endUserId;
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
