package com.trustly.api.client;

import com.trustly.api.TrustlyApiClientSettings;
import com.trustly.api.exceptions.TrustlyNoNotificationClientException;
import com.trustly.api.exceptions.TrustlyValidationException;
import com.trustly.api.util.TrustlyStreamUtils;
import com.trustly.api.util.TrustlyStringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;

import static com.trustly.api.domain.Models.NotificationResponseDataBase;

public class TrustlyApiClientExtensions {

  public static final String GENERIC_ERROR_MESSAGE = "An exception occurred (error message given by trustly-api-client for Java)";

  public interface NotificationResponder {

    default void addHeader(String key, String value) {
    }

    void setStatus(int httpStatus);

    void writeBody(String value) throws IOException;
  }

  public static void handleNotificationRequest(TrustlyApiClient client, InputStream incoming, NotificationResponder responder)
    throws IOException,
    TrustlyNoNotificationClientException {

    String requestStringBody;
    try (var sr = new InputStreamReader(incoming, StandardCharsets.UTF_8)) {
      requestStringBody = TrustlyStreamUtils.readerToString(sr);
    }

    final var responseCount = new AtomicInteger(0);
    try {
      client.handleNotification(
        requestStringBody,
        (method, uuid, result) -> {
          responseCount.incrementAndGet();
          TrustlyApiClientExtensions.respond(client, responder, method, uuid, result, null, 200);
        }
      );
    } catch (Exception ex) {

      var assemblyVersion = TrustlyApiClientExtensions.class.getPackage().getImplementationVersion();

      responder.addHeader("Content-Type", "application/json");
      responder.addHeader("Accept", "application/json");
      responder.addHeader("User-Agent", "trustly-api-client-java/" + assemblyVersion);
      responder.setStatus(500);

      if (client.getSettings().isIncludeExceptionMessageInNotificationResponse()) {
        responder.writeBody(ex.getMessage());
      } else {
        responder.writeBody(GENERIC_ERROR_MESSAGE);
      }
    }

    if (responseCount.get() == 0) {
      throw new TrustlyNoNotificationClientException(
        "None of your client's event listeners responded with OK or FAILED. That must be done.");
    }
  }

  public static <D extends NotificationResponseDataBase<?>> void respond(
    TrustlyApiClient client,
    NotificationResponder responder,
    String method,
    String uuid,
    D status,
    String message,
    int httpStatusCode
  ) throws IOException, TrustlyValidationException {

    var rpcResponse = client.createResponsePackage(method, uuid, status);

    if (client.getSettings().isIncludeMessageInNotificationResponse() && !TrustlyStringUtils.isBlank(message)) {
      rpcResponse.getResult().getData().setMessage(message);
    }

    var rpcString = TrustlyApiClientSettings.DEFAULT_OBJECT_MAPPER.writeValueAsString(rpcResponse);
    var assemblyVersion = TrustlyApiClientExtensions.class.getPackage().getImplementationVersion();

    responder.addHeader("Content-Type", "application/json");
    responder.addHeader("Accept", "application/json");
    responder.addHeader("User-Agent", "trustly-api-client-java/" + assemblyVersion);
    responder.setStatus(httpStatusCode);
    responder.writeBody(rpcString);
  }
}
