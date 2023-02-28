package com.trustly.api.data.security;

//import com.trustly.api.commons.Currency;
//import com.trustly.api.data.TrustlyApiSettings;
//import com.trustly.api.data.request.Request;
//import com.trustly.api.requestbuilders.Deposit;
//import com.trustly.api.security.KeyChain;
//import com.trustly.api.security.SignatureHandler;
//import java.security.KeyException;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;

/**
 * These test will only work if a local environment exists. At the USER_HOME root these files is needed:
 * trustly_client_username.txt - your username
 * trustly_client_password.txt - your password
 * trustly_client_public.pem - your public key
 * trustly_client_public.pem - your private key
 */
class SignatureHandlerTest {
//
//  private final SignatureHandler signatureHandler = new SignatureHandler();
//
//  @BeforeEach
//  void setup() throws KeyException {
//    signatureHandler.init(
//        TrustlyApiSettings.getClientPrivateKey(),
//        "",
//        TrustlyApiSettings.getClientUsername(),
//        TrustlyApiSettings.getClientPassword(),
//        new KeyChain(true)
//    );
//  }
//
//  @Test
//  void testItSerializesCorrect() {
//
//    final String expected = "AttributesCountrySECurrencySEKEmailtest@trustly.comFirstnameJohnLastnameDoeLocalesv_SEMobilePhone+46709876543EndUserID12345MessageIDyour_unique_deposit_idNotificationURLhttps://URL_to_your_notification_service";
//
//    final Request depositRequest = new Deposit.Build(
//        "https://URL_to_your_notification_service",
//        "12345",
//        "your_unique_deposit_id",
//        Currency.SEK,
//        "John",
//        "Doe",
//        "test@trustly.com"
//    )
//        .mobilePhone("+46709876543")
//        .locale("sv_SE")
//        .country("SE")
//        .getRequest();
//
//    String serialized = signatureHandler.serializeData(depositRequest.getParams().getData());
//
//    Assertions.assertEquals(expected, serialized);
//  }
//
//  @Test
//  void testItSignsCorrect() {
//
//    final Request depositRequest = new Deposit.Build(
//        "https://URL_to_your_notification_service",
//        "12345",
//        "your_unique_deposit_id",
//        Currency.SEK,
//        "John",
//        "Doe",
//        "test@trustly.com"
//    )
//        .mobilePhone("+46709876543")
//        .locale("sv_SE")
//        .country("SE")
//        .getRequest();
//
//    Assertions.assertDoesNotThrow(() -> signatureHandler.signRequest(depositRequest));
//  }
}
