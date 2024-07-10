package com.trustly.api.domain.notifications;

import static com.trustly.api.domain.Models.*;

public class UnknownNotification extends JsonRpcNotification<JsonRpcNotificationParams<UnknownData>> {
  public UnknownNotification(String method) {
    super(method);
  }
}
