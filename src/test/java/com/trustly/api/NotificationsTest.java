package com.trustly.api;

import com.trustly.api.client.TrustlyApiClient;
import com.trustly.api.client.TrustlyApiClientExtensions;
import com.trustly.api.client.TrustlyApiClientExtensions.NotificationResponder;
import com.trustly.api.client.TrustlyApiClientSettings;
import com.trustly.api.domain.base.IFromTrustlyRequestData;
import com.trustly.api.domain.notifications.AccountNotificationData;
import com.trustly.api.domain.notifications.CancelNotificationData;
import com.trustly.api.domain.notifications.CreditNotificationData;
import com.trustly.api.domain.notifications.DebitNotificationData;
import com.trustly.api.domain.notifications.PayoutConfirmationNotificationData;
import com.trustly.api.domain.notifications.PendingNotificationData;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class NotificationsTest {

  private final TrustlyApiClientSettings settings = TrustlyApiClientSettings.forTest()
    .withCredentials("merchant_username", "merchant_password")
    .withCertificatesFromStreams(
      // We use the same certificates as those found at https://test.trustly.com/signaturetester/
      NotificationsTest.class.getResourceAsStream("/keys/merchant_public_key.pem"),
      NotificationsTest.class.getResourceAsStream("/keys/merchant_private_key.pem")
    )
    .andTrustlyCertificateFromStream(
      // We pretend that Trustly is our own test certificate, so we can properly validate the signature.
      NotificationsTest.class.getResourceAsStream("/keys/merchant_public_key.pem")
    );

  static Stream<Arguments> testNotificationsWithoutSignatureVerification() {
    return Stream.of(
      Arguments.of("account", AccountNotificationData.class),
      Arguments.of("cancel", CancelNotificationData.class),
      Arguments.of("credit", CreditNotificationData.class),
      Arguments.of("debit", DebitNotificationData.class),
      Arguments.of("payoutconfirmation", PayoutConfirmationNotificationData.class),
      Arguments.of("pending", PendingNotificationData.class)
    );
  }

  @ParameterizedTest
  @MethodSource
  void testNotificationsWithoutSignatureVerification(String method, Class<IFromTrustlyRequestData> dataType) throws Exception {

    try (TrustlyApiClient client = new TrustlyApiClient(settings, new NoOpJsonRpcSigner())) {

      final AtomicInteger receivedNotificationDataCounter = new AtomicInteger();

      client.addNotificationListener(method, dataType, args -> {
        receivedNotificationDataCounter.incrementAndGet();
        args.respondWithOk();
      });

      final InputStream is = this.getClass().getResourceAsStream(String.format("/notifications/incoming/%s.json", method));
      final Map<String, String> headers = new HashMap<>();
      final AtomicInteger status = new AtomicInteger();
      final AtomicReference<String> responseString = new AtomicReference<>();

      final NotificationResponder responder = new NotificationResponder() {
        @Override
        public void addHeader(String key, String value) {
          headers.put(key, value);
        }

        @Override
        public void setStatus(int httpStatus) {
          status.set(httpStatus);
        }

        @Override
        public void writeBody(String value) {
          responseString.set(value);
        }
      };

      TrustlyApiClientExtensions.handleNotificationRequest(is, responder);

      Assertions.assertEquals(200, status.get());
      Assertions.assertNotNull(responseString.get());
    }
  }

  @Test
  void testUnknownNotification() throws Exception {

    try (TrustlyApiClient client = new TrustlyApiClient(settings, new NoOpJsonRpcSigner())) {

      final AtomicReference<Object> receivedUnknownValue = new AtomicReference<>();

      client.addOnUnknownNotification(args -> {
        args.respondWithOk();
        receivedUnknownValue.set(args.getData().getAny().get("something"));
      });

      final InputStream is = this.getClass().getResourceAsStream("/notifications/incoming/_unknown.json");
      final Map<String, String> headers = new HashMap<>();
      final AtomicInteger status = new AtomicInteger();
      final AtomicReference<String> responseString = new AtomicReference<>();

      final NotificationResponder responder = new NotificationResponder() {
        @Override
        public void addHeader(String key, String value) {
          headers.put(key, value);
        }

        @Override
        public void setStatus(int httpStatus) {
          status.set(httpStatus);
        }

        @Override
        public void writeBody(String value) {
          responseString.set(value);
        }
      };

      TrustlyApiClientExtensions.handleNotificationRequest(is, responder);

      Assertions.assertEquals(200, status.get());
      Assertions.assertEquals("application/json", headers.get("Content-Type"));
      Assertions.assertNotNull(responseString.get());
      Assertions.assertEquals("abc", receivedUnknownValue.get());
    }
  }

  @Test
  void testCancelNotification() throws Exception {

    try (TrustlyApiClient client = new TrustlyApiClient(settings)) {

      final AtomicInteger receivedNotificationDataCounter = new AtomicInteger();

      client.addOnCancelListener(args -> {

        receivedNotificationDataCounter.incrementAndGet();
        args.respondWithOk();
      });

      final InputStream is = this.getClass().getResourceAsStream("/notifications/incoming/cancel.json");
      final Map<String, String> headers = new HashMap<>();
      final AtomicInteger status = new AtomicInteger();
      final AtomicReference<String> responseString = new AtomicReference<>();

      final NotificationResponder responder = new NotificationResponder() {
        @Override
        public void addHeader(String key, String value) {
          headers.put(key, value);
        }

        @Override
        public void setStatus(int httpStatus) {
          status.set(httpStatus);
        }

        @Override
        public void writeBody(String value) {
          responseString.set(value);
        }
      };

      TrustlyApiClientExtensions.handleNotificationRequest(is, responder);

      Assertions.assertEquals(200, status.get());
      Assertions.assertNotNull(responseString.get());
    }
  }

}
