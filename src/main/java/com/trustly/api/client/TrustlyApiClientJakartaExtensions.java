package com.trustly.api.client;

import com.trustly.api.domain.exceptions.TrustlyNoNotificationClientException;
import com.trustly.api.domain.exceptions.TrustlyNoNotificationListenerException;
import com.trustly.api.domain.exceptions.TrustlySignatureException;
import com.trustly.api.domain.exceptions.TrustlyValidationException;
import java.io.IOException;
import java.io.InputStream;

public final class TrustlyApiClientJakartaExtensions {

  private TrustlyApiClientJakartaExtensions() {
  }

  public static class JakartaNotificationResponder implements TrustlyApiClientExtensions.NotificationResponder {

    private final jakarta.servlet.http.HttpServletResponse response;

    public JakartaNotificationResponder(jakarta.servlet.http.HttpServletResponse response) {
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
  public static void handleNotificationRequest(
    jakarta.servlet.http.HttpServletRequest request,
    jakarta.servlet.http.HttpServletResponse response
  ) throws
    IOException,
    TrustlyNoNotificationClientException,
    TrustlyNoNotificationListenerException,
    TrustlyValidationException,
    TrustlySignatureException {

    TrustlyApiClientExtensions.handleNotificationRequest(request.getInputStream(), new JakartaNotificationResponder(response));
  }

  public static void handleNotificationRequest(
    InputStream incoming,
    jakarta.servlet.http.HttpServletResponse response
  ) throws
    IOException,
    TrustlyNoNotificationClientException,
    TrustlyNoNotificationListenerException,
    TrustlyValidationException,
    TrustlySignatureException {

    TrustlyApiClientExtensions.handleNotificationRequest(incoming, new JakartaNotificationResponder(response));
  }
}
