package com.trustly.api.domain.notifications;

import static com.trustly.api.domain.Models.*;

public class UnknownNotification extends JsonRpcNotification<JsonRpcNotificationParams<Any>> {
  public UnknownNotification(String method) {
    super(method);
  }
}
