package com.trustly.api.client;

import com.trustly.api.exceptions.TrustlyValidationException;
import java.io.IOException;

@FunctionalInterface
public interface NotificationEvent<TCallbackData, TAckData> {
  void onNotification(NotificationArgs<? extends TCallbackData, TAckData> args) throws IOException, TrustlyValidationException;
}
