package com.trustly.api;

import com.trustly.api.client.TrustlyApiClient;
import com.trustly.api.client.TrustlyApiClientExtensions;
import com.trustly.api.client.TrustlyApiClientExtensions.NotificationResponder;
import com.trustly.api.client.TrustlyApiClientSettings;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import com.trustly.api.domain.notifications.UnknownNotificationAckData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static com.trustly.api.domain.Models.*;

@Execution(ExecutionMode.CONCURRENT)
class NotificationsTest {

  // We use the same certificates as those found at https://test.trustly.com/signaturetester/
  private final TrustlyApiClientSettings settings = TrustlyApiClientSettings.forTest()
    .withCredentials("merchant_username", "merchant_password")
    .withCertificatesFromStreams(
      NotificationsTest.class.getResourceAsStream("/keys/merchant_public_key.pem"),
      NotificationsTest.class.getResourceAsStream("/keys/merchant_private_key.pem")
    )
    .andTrustlyCertificateFromStream(
      NotificationsTest.class.getResourceAsStream("/keys/merchant_public_key.pem")
    );

  static Stream<Arguments> testNotificationsWithoutSignatureVerification() {
    return Stream.of(
      Arguments.of("account", AccountDefaultNotification.Params.Data.class),
      Arguments.of("cancel", CancelDefaultNotification.Params.Data.class),
      Arguments.of("credit", CreditDefaultNotification.Params.Data.class),
      Arguments.of("debit", DebitDefaultNotification.Params.Data.class),
      Arguments.of("payoutconfirmation", PayoutConfirmationNotification.Params.Data.class),
      Arguments.of("pending", PendingDefaultNotification.Params.Data.class)
    );
  }

  @ParameterizedTest
  @MethodSource
  void testNotificationsWithoutSignatureVerification(String method, Class<?> dataType) throws Exception {

    try (TrustlyApiClient client = new TrustlyApiClient(settings, new NoOpJsonRpcSigner())) {

      final AtomicInteger receivedNotificationDataCounter = new AtomicInteger();

      client.addNotificationListener(method, dataType, args -> {
        receivedNotificationDataCounter.incrementAndGet();
        args.respondWith(new UnknownNotificationAckData("FOO"));
      });

      final InputStream is = this.getClass().getResourceAsStream(String.format("/notifications/incoming/%s.json", method));
      final AtomicInteger status = new AtomicInteger();
      final AtomicReference<String> responseString = new AtomicReference<>();

      final NotificationResponder responder = new NotificationResponder() {

        @Override
        public void setStatus(int httpStatus) {
          status.set(httpStatus);
        }

        @Override
        public void writeBody(String value) {
          responseString.set(value);
        }
      };

      TrustlyApiClientExtensions.handleNotificationRequest(client, is, responder);

      Assertions.assertEquals(200, status.get());
      Assertions.assertNotNull(responseString.get());
    }
  }

  @Test
  void testUnknownNotification() throws Exception {

    try (TrustlyApiClient client = new TrustlyApiClient(settings, new NoOpJsonRpcSigner())) {

      final AtomicReference<Object> receivedUnknownValue = new AtomicReference<>();

      client.addOnUnknownNotification(args -> {
        args.respondWith(new UnknownNotificationAckData());
        receivedUnknownValue.set(args.getData().getAdditionalProperties().get("something"));
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

      TrustlyApiClientExtensions.handleNotificationRequest(client, is, responder);

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
        args.respondWith(GeneralNotificationResponseData.builder().status(GeneralNotificationResponseData.Status.OK).build());
      });

      final InputStream is = this.getClass().getResourceAsStream("/notifications/incoming/cancel.json");
      final AtomicInteger status = new AtomicInteger();
      final AtomicReference<String> responseString = new AtomicReference<>();

      final NotificationResponder responder = new NotificationResponder() {

        @Override
        public void setStatus(int httpStatus) {
          status.set(httpStatus);
        }

        @Override
        public void writeBody(String value) {
          responseString.set(value);
        }
      };

      TrustlyApiClientExtensions.handleNotificationRequest(client, is, responder);

      Assertions.assertEquals(200, status.get());
      Assertions.assertNotNull(responseString.get());
    }
  }

}
