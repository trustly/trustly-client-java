package com.trustly.api.client;

import com.trustly.api.domain.base.IRequestParamsData;
import com.trustly.api.domain.exceptions.TrustlyValidationException;
import jakarta.validation.Valid;
import java.io.IOException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotificationArgs<D extends IRequestParamsData> {

  @FunctionalInterface
  public interface NotificationOkHandler {

    void handle(String method, String uuid) throws IOException, TrustlyValidationException;
  }

  @FunctionalInterface
  public interface NotificationFailHandler {

    void handle(String method, String uuid, String message) throws IOException, TrustlyValidationException;
  }

  @Getter
  @Valid
  private final D data;

  private final String method;
  private final String uuid;

  private final NotificationOkHandler onOK;
  private final NotificationFailHandler onFailed;

  public void respondWithOk() throws TrustlyValidationException, IOException {
    this.onOK.handle(this.method, this.uuid);
  }

  public void respondWithFailed(String message) throws TrustlyValidationException, IOException {
    this.onFailed.handle(this.method, this.uuid, message);
  }
}
