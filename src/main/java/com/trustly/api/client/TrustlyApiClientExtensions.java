package com.trustly.api.client;

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

import static com.trustly.api.domain.Models.*;

public class TrustlyApiClientExtensions {

  public interface NotificationResponder {

    default void addHeader(String key, String value) {
    }

    void setStatus(int httpStatus);

    void writeBody(String value) throws IOException;
  }

  public static void handleNotificationRequest(TrustlyApiClient client, InputStream incoming, NotificationResponder responder)
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
//    final AtomicInteger clientCount = new AtomicInteger(0);
//    for (TrustlyApiClient client : TrustlyApiClient.getRegisteredClients()) {
//      clientCount.incrementAndGet();
      client.handleNotification(
        requestStringBody,
        (method, uuid, result) -> {
          responseCount.incrementAndGet();
          TrustlyApiClientExtensions.respond(client, responder, method, uuid, result, null, 200);
        }
      );
//    }

//    if (clientCount.get() == 0) {
//      throw new TrustlyNoNotificationClientException("There are no registered Api Clients listening to notifications");
//    }

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

    String rpcString = TrustlyApiClient.DEFAULT_OBJECT_MAPPER.writeValueAsString(rpcResponse);

    String assemblyVersion = TrustlyApiClientExtensions.class.getPackage().getImplementationVersion();

    responder.addHeader("Content-Type", "application/json");
    responder.addHeader("Accept", "application/json");
    responder.addHeader("User-Agent", "trustly-api-client-java/" + assemblyVersion);
    responder.setStatus(httpStatusCode);
    responder.writeBody(rpcString);
  }
}
