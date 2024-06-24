package com.trustly.api.client;

import com.trustly.api.domain.Models;
import com.trustly.api.domain.exceptions.TrustlyValidationException;
import jakarta.validation.Valid;
import java.io.IOException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotificationArgs<D, TAckData extends Models.NotificationResponseDataBase<?>> {

  @FunctionalInterface
  public interface NotificationHandler<TAckData extends Models.NotificationResponseDataBase<?>> {
    void handle(String method, String uuid, TAckData result) throws IOException, TrustlyValidationException;
  }

  @Getter
  @Valid
  private final D data;

  private final String method;
  private final String uuid;

  private final NotificationHandler<TAckData> onOK;

  public void respondWith(TAckData result) throws TrustlyValidationException, IOException {
    this.onOK.handle(this.method, this.uuid, result);
  }
}
