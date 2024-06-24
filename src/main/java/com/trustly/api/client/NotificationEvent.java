package com.trustly.api.client;

import com.trustly.api.domain.Models;
import com.trustly.api.domain.exceptions.TrustlyValidationException;
import java.io.IOException;

@FunctionalInterface
public interface NotificationEvent<D, TAckData extends Models.NotificationResponseDataBase<?>> {

  void onNotification(NotificationArgs<D, TAckData> args) throws IOException, TrustlyValidationException;
}
