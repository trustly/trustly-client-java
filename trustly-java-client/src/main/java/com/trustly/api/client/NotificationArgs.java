package com.trustly.api.client;

import com.trustly.api.exceptions.TrustlyValidationException;
import jakarta.validation.Valid;
import java.io.IOException;

public class NotificationArgs<TCallbackData, TAckData> {

  @FunctionalInterface
  public interface NotificationHandler<TAckData> {
    void handle(String method, String uuid, TAckData result) throws IOException, TrustlyValidationException;
  }

  @Valid
  private final TCallbackData data;

  private final String method;
  private final String uuid;

  private final NotificationHandler<TAckData> onOK;

  @Valid
  public TCallbackData getData() {
    return data;
  }

  public NotificationArgs(TCallbackData data, String method, String uuid, NotificationHandler<TAckData> onOK) {
    this.data = data;
    this.method = method;
    this.uuid = uuid;
    this.onOK = onOK;
  }

  public void respondWith(TAckData result) throws TrustlyValidationException, IOException {
    this.onOK.handle(this.method, this.uuid, result);
  }
}
