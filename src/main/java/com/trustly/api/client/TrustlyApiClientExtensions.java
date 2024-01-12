package com.trustly.api.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trustly.api.domain.base.JsonRpcResponse;
import com.trustly.api.domain.base.NotificationResponse;
import com.trustly.api.domain.exceptions.TrustlyNoNotificationClientException;
import com.trustly.api.domain.exceptions.TrustlyNoNotificationListenerException;
import com.trustly.api.domain.exceptions.TrustlySignatureException;
import com.trustly.api.domain.exceptions.TrustlyValidationException;
import com.trustly.api.util.TrustlyStreamUtils;
import com.trustly.api.util.TrustlyStringUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicInteger;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TrustlyApiClientExtensions {

  public interface NotificationResponder {

    void addHeader(String key, String value);

    void setStatus(int httpStatus);

    void writeBody(String value) throws IOException;
  }

  public static class DefaultNotificationResponder implements NotificationResponder {

    private final HttpServletResponse response;

    public DefaultNotificationResponder(HttpServletResponse response) {
      this.response = response;
    }

    @Override
    public void addHeader(String key, String value) {
      this.response.addHeader(key, value);
    }

    @Override
    public void setStatus(int httpStatus) {
      this.response.setStatus(httpStatus);
    }

    @Override
    public void writeBody(String value) throws IOException {
      this.response.getWriter().write(value);
    }
  }

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  /**
   * Will deserialize, verify and validate the incoming payload for you.
   * <p>
   * It will then call the appropriate notification listeners for this client only. If the incoming notification method does not have a
   * listener, the {@code Unknown} notification listener will be called.
   * <p>
   * It is up to your listener to call the appropriate {@link NotificationArgs#respondWithOk()} or
   * {@link NotificationArgs#respondWithFailed} methods, which will callback to your here given {@code onOK} or {@code onFailed} arguments.
   * <p>
   *
   * @param request The incoming request that contains a notification
   * @param response The outgoing response that we should send our notification response to
   *
   * @throws IOException If the JSON string could not be deserialized or the response could not be sent.
   * @throws TrustlyNoNotificationListenerException If there was no listener for the notification, nor one for unknown ones.
   * @throws TrustlyValidationException If the response data could not be properly validated.
   * @throws TrustlySignatureException If the signature of the response could not be properly verified.
   */
  public static void handleNotificationRequest(HttpServletRequest request, HttpServletResponse response)
    throws IOException,
    TrustlyNoNotificationClientException,
    TrustlyNoNotificationListenerException,
    TrustlyValidationException,
    TrustlySignatureException {

    TrustlyApiClientExtensions.handleNotificationRequest(request.getInputStream(), new DefaultNotificationResponder(response));
  }

  public static void handleNotificationRequest(InputStream incoming, HttpServletResponse response)
    throws IOException,
    TrustlyNoNotificationClientException,
    TrustlyNoNotificationListenerException,
    TrustlyValidationException,
    TrustlySignatureException {

    TrustlyApiClientExtensions.handleNotificationRequest(incoming, new DefaultNotificationResponder(response));
  }

  public static void handleNotificationRequest(InputStream incoming, NotificationResponder responder)
    throws IOException,
    TrustlyNoNotificationClientException,
    TrustlyNoNotificationListenerException,
    TrustlyValidationException,
    TrustlySignatureException {

    String requestStringBody;
    try (InputStreamReader sr = new InputStreamReader(incoming)) {
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
