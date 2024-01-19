package com.trustly.api.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trustly.api.domain.base.JsonRpcResponse;
import com.trustly.api.domain.base.NotificationResponse;
import com.trustly.api.domain.exceptions.TrustlyDeprecatedException;
import com.trustly.api.domain.exceptions.TrustlyNoNotificationClientException;
import com.trustly.api.domain.exceptions.TrustlyNoNotificationListenerException;
import com.trustly.api.domain.exceptions.TrustlySignatureException;
import com.trustly.api.domain.exceptions.TrustlyValidationException;
import com.trustly.api.util.TrustlyStreamUtils;
import com.trustly.api.util.TrustlyStringUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;

public class TrustlyApiClientExtensions {

  public interface NotificationResponder {

    void addHeader(String key, String value);

    void setStatus(int httpStatus);

    void writeBody(String value) throws IOException;
  }

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  public static void handleNotificationRequest(InputStream incoming, NotificationResponder responder)
    throws IOException,
    TrustlyNoNotificationClientException,
    TrustlyNoNotificationListenerException,
    TrustlyValidationException,
    TrustlySignatureException {

    String requestStringBody;
    try (InputStreamReader sr = new InputStreamReader(incoming, StandardCharsets.UTF_8)) {
      requestStringBody = TrustlyStreamUtils.readerToString(sr);
    }

    final AtomicInteger responseCount = new AtomicInteger(0);
    final AtomicInteger clientCount = new AtomicInteger(0);
    for (TrustlyApiClient client : TrustlyApiClient.getRegisteredClients()) {
      clientCount.incrementAndGet();
      client.handleNotification(
        requestStringBody,
        (method, uuid) -> {
          responseCount.incrementAndGet();
          TrustlyApiClientExtensions.respond(client, responder, method, uuid, "OK", null, 200);
        },
        (method, uuid, message) -> {
          responseCount.incrementAndGet();
          TrustlyApiClientExtensions.respond(client, responder, method, uuid, "FAILED", message, 500);
        }
      );
    }

    if (clientCount.get() == 0) {
      throw new TrustlyNoNotificationClientException("There are no registered Api Clients listening to notifications");
    }

    if (responseCount.get() == 0) {
      throw new TrustlyNoNotificationClientException(
        "None of your client's event listeners responded with OK or FAILED. That must be done.");
    }
  }

  /**
   * @deprecated Use specific {@link TrustlyApiClientJakartaExtensions} or {@link TrustlyApiClientJavaxExtensions} depending on your need.
   */
  @Deprecated
  public static void handleNotificationRequest(Object request, Object response) throws TrustlyDeprecatedException {
    throw new TrustlyDeprecatedException(
      "Need to use the more specific TrustlyApiClientJakartaExtensions or TrustlyApiClientJavaxExtensions"
    );
  }

  public static void respond(
    TrustlyApiClient client,
    NotificationResponder responder,
    String method,
    String uuid,
    String status,
    String message,
    int httpStatusCode
  ) throws IOException, TrustlyValidationException {

    NotificationResponse notificationResponse = NotificationResponse.builder()
      .status(status)
      .build();

    JsonRpcResponse<NotificationResponse> rpcResponse = client.createResponsePackage(method, uuid, notificationResponse);

    if (client.getSettings().isIncludeMessageInNotificationResponse() && !TrustlyStringUtils.isBlank(message)) {

      rpcResponse = rpcResponse.toBuilder()
        .result(
          rpcResponse.getResult().toBuilder()
            .data(
              rpcResponse.getResult().getData().toBuilder()
                .any("message", message)
                .build()
            )
            .build()
        )
        .build();
    }

    String rpcString = OBJECT_MAPPER.writeValueAsString(rpcResponse);

    String assemblyVersion = TrustlyApiClientExtensions.class.getPackage().getImplementationVersion();

    responder.addHeader("Content-Type", "application/json");
    responder.addHeader("Accept", "application/json");
    responder.addHeader("User-Agent", "trustly-api-client-java/" + assemblyVersion);
    responder.setStatus(httpStatusCode);
    responder.writeBody(rpcString);
  }
}
