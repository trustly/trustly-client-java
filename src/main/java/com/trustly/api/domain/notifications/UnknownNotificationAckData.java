package com.trustly.api.domain.notifications;

import com.trustly.api.domain.Models;

public class UnknownNotificationAckData extends Models.NotificationResponseDataBase<String> {

  public UnknownNotificationAckData() {
    this("OK");
  }

  public UnknownNotificationAckData(String status) {
    this.setStatus(status);
  }
}
