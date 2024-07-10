package com.trustly.api;

import com.trustly.api.client.TrustlyApiClient;
import com.trustly.api.client.TrustlyApiClientExtensions;
import com.trustly.api.client.TrustlyApiClientExtensions.NotificationResponder;
import com.trustly.api.domain.Models;
import com.trustly.api.domain.notifications.UnknownNotificationAckData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import java.util.stream.Stream;

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

  private static class Scenario<
    TCallback extends JsonRpcNotification<TCallbackParams>,
    TCallbackParams extends JsonRpcNotificationParams<TCallbackData>,
    TCallbackData,

    TAck extends Models.JsonRpcResponse<TAckRes>,
    TAckRes extends Models.ResponseResult<TAckData>,
    TAckData extends Models.NotificationResponseDataBase<TAckStatus>,
    TAckStatus
    > {

    final String method;
    final Class<TCallback> classNotification;
    final Class<TAck> classAck;
    final Supplier<TAckData> dataSupplier;

    public Scenario(String method, Class<TCallback> classNotification, Class<TAck> classAck, Supplier<TAckData> dataSupplier) {
      this.method = method;
      this.classNotification = classNotification;
      this.classAck = classAck;
      this.dataSupplier = dataSupplier;
    }
  }

  static Stream<Arguments> testNotificationsWithoutSignatureVerification() {
    return Stream.of(
      Arguments.of(new Scenario<>("account", AccountDefaultNotification.class, AccountDefaultNotificationResponse.class, GeneralNotificationResponseData::new)),
      Arguments.of(new Scenario<>("cancel", CancelNotification.class, CancelNotificationResponse.class, GeneralNotificationResponseData::new)),
      Arguments.of(new Scenario<>("credit", CreditDefaultNotification.class, CreditDefaultNotificationResponse.class, GeneralNotificationResponseData::new)),
      Arguments.of(new Scenario<>("debit", DebitDefaultNotification.class, DebitDefaultNotificationResponse.class, DebitNotificationResponseData::new)),
      Arguments.of(new Scenario<>("payoutconfirmation", PayoutConfirmationNotification.class, PayoutConfirmationNotificationResponse.class, GeneralNotificationResponseData::new)),
      Arguments.of(new Scenario<>("pending", PendingDefaultNotification.class, PendingDefaultNotificationResponse.class, GeneralNotificationResponseData::new))
    );
  }

  @ParameterizedTest
  @MethodSource
  <
    TCallback extends JsonRpcNotification<TCallbackParams>,
    TCallbackParams extends JsonRpcNotificationParams<TCallbackData>,
    TCallbackData,

    TAck extends Models.JsonRpcResponse<TAckRes>,
    TAckRes extends Models.ResponseResult<TAckData>,
    TAckData extends Models.NotificationResponseDataBase<TAckStatus>,
    TAckStatus
    >
  void testNotificationsWithoutSignatureVerification(Scenario<TCallback, TCallbackParams, TCallbackData, TAck, TAckRes, TAckData, TAckStatus> scenario) throws Exception {

    try (var client = new TrustlyApiClient(settings, new NoOpJsonRpcSigner())) {

      final var receivedNotificationDataCounter = new AtomicInteger();

      client.addNotificationListener(scenario.method, scenario.classNotification, scenario.classAck, args -> {
        receivedNotificationDataCounter.incrementAndGet();
        args.respondWith(scenario.dataSupplier.get());
      });

      final var is = this.getClass().getResourceAsStream(String.format("/notifications/incoming/%s.json", scenario.method));
      final var status = new AtomicInteger();
      final var responseString = new AtomicReference<String>();

      final var responder = new NotificationResponder() {

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

    try (var client = new TrustlyApiClient(settings, new NoOpJsonRpcSigner())) {

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

    try (var client = new TrustlyApiClient(settings)) {

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
