package com.trustly.api.client;

import com.trustly.api.domain.base.IFromTrustlyRequestData;
import com.trustly.api.domain.exceptions.TrustlyValidationException;
import java.io.IOException;

@FunctionalInterface
public interface NotificationEvent<D extends IFromTrustlyRequestData> {

  void onNotification(NotificationArgs<D> args) throws IOException, TrustlyValidationException;
}
