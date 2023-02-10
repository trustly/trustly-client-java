package com.trustly.api.data.request;

import com.trustly.api.SignedAPI;
import com.trustly.api.commons.Currency;
import com.trustly.api.data.TrustlyApiSettings;
import com.trustly.api.data.response.Response;
import com.trustly.api.requestbuilders.AccountPayout;
import com.trustly.api.requestbuilders.AccountPayout.Build.SenderInformationBuilder;
import com.trustly.api.requestbuilders.CancelCharge;
import com.trustly.api.requestbuilders.Charge;
import com.trustly.api.requestbuilders.Deposit;
import com.trustly.api.requestbuilders.Refund;
import com.trustly.api.requestbuilders.RegisterAccount;
import com.trustly.api.requestbuilders.RegisterAccountPayout;
import com.trustly.api.requestbuilders.RegisterAccountPayout.Build;
import com.trustly.api.requestbuilders.SelectAccount;
import com.trustly.api.requestbuilders.Withdraw;
import java.util.UUID;
import java.util.function.Supplier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * These test will only work if a local environment exists. At the USER_HOME root these files is needed: trustly_client_username.txt - your
 * username trustly_client_password.txt - your password trustly_client_public.pem - your public key trustly_client_public.pem - your private
 * key
 * <p>
 * If those files exist and is correct you are able to make requests to Trustlys test environment
 */
class TestRequests {

    private static final Supplier<UUID> UUID_SUPPLIER = UUID::randomUUID;

    private SignedAPI api;

    @BeforeEach
    void setup() {
        api = new SignedAPI();

        // Init for test environment
        api.init(
            TrustlyApiSettings.getClientPrivateKey(),
            "",
            TrustlyApiSettings.getClientUsername(),
            TrustlyApiSettings.getClientPassword(),
            true
        );
    }

    @Test
    void testAccountPayout() {

        SenderInformationBuilder senderInformationBuilder = new AccountPayout.Build.SenderInformationBuilder()
            .partytype("PERSON")
            .address("Street 1, 12345 Barcelona")
            .countryCode("SE")
            .firstname("Steve")
            .lastname("Smith")
            .customerID("123456789")
            .dateOfBirth("1990-03-31");

        Request accountPayoutRequest = new AccountPayout.Build(
            "https://notify.me",
            "AccountID",
            "EndUserId",
            "MessageId",
            "99.99",
            "SEK"
        )
            // Attributes
            .pspMerchant("Merchant Ltd.")
            .shopperStatement("MyBrand.com")
            .externalReference("23423525234")
            .merchantCategoryCode("5499")
            .pspMerchantURL("www.merchant.com")
            .senderInformation(senderInformationBuilder)
            .getRequest();

        Response accountPayoutResponse = api.sendRequest(accountPayoutRequest);

        Assertions.assertNotNull(accountPayoutResponse);
        Assertions.assertNotNull(accountPayoutResponse.getError());
    }

    @Test
    void testCancelCharge() {

        Request cancelChargeRequest = new CancelCharge.Build("accountId")
            .getRequest();

        Response cancelChargeResponse = api.sendRequest(cancelChargeRequest);

        // Order won't exist, assert error response
        Assertions.assertNotNull(cancelChargeResponse);
        Assertions.assertNotNull(cancelChargeResponse.getError());
    }

    @Test
    void testCharge() {

        Request chargeRequest = new Charge.Build(
            "accountId",
            "https://notify.me",
            "EndUserId",
            UUID_SUPPLIER.get().toString(),
            "99.90",
            Currency.SEK,
            "Shopper statement",
            "test@trustly.com"
        )
            // Attributes
            .pspMerchant("Merchant Ltd.")
            .externalReference("External reference")
            .merchantCategoryCode("5499")
            .pspMerchantURL("www.merchant.com")
            .getRequest();

        Response chargeResponse = api.sendRequest(chargeRequest);

        Assertions.assertNotNull(chargeResponse);
        Assertions.assertNotNull(chargeResponse.getError());
    }

    @Test
    void testDeposit() {

        Request depositRequest = new Deposit.Build(
            "https://test.trustly.com/trustlynotification",
            UUID_SUPPLIER.get().toString(),
            UUID_SUPPLIER.get().toString(),
            Currency.SEK,
            "Jon",
            "Doe",
            "test@example.com"
        )
            .externalReference("23423525234")
            .pspMerchant("Merchant Ltd.")
            .pspMerchantURL("www.merchant.com")
            .merchantCategoryCode("5499")
            .amount("22")
            .getRequest();

        Response depositResponse = api.sendRequest(depositRequest);

        Assertions.assertNotNull(depositResponse.getResult().getData());
        Assertions.assertNull(depositResponse.getError());
    }

    @Test
    void testRefund() {

        Request refundRequest = new Refund.Build(
            "123456",
            "22",
            Currency.SEK
        )
            .externalReference("123")
            .getRequest();

        // Order won't exist, assert error
        Response refundResponse = api.sendRequest(refundRequest);
        Assertions.assertNotNull(refundResponse.getError());
    }

    @Test
    void testRegisterAccount() {

        Request registerAccountRequest = new RegisterAccount.Build(
            "123123",
            "SWEDEN",
            "6112",
            "69706212",
            "Steve",
            "Smith"
        )
            .addressCountry("SE")
            .dateOfBirth("1990-01-20")
            .email("test@trustly.com")
            .getRequest();

        Response registerAccountResponse = api.sendRequest(registerAccountRequest);

        Assertions.assertNotNull(registerAccountResponse.getResult());
        Assertions.assertNotNull(registerAccountResponse.getResult().getData());
        Assertions.assertNull(registerAccountResponse.getError());
    }

    @Test
    void testSelectAccount() {

        Request selectAccountRequest = new SelectAccount.Build(
            "https://notify.me",
            "EndUserId",
            UUID_SUPPLIER.get().toString()
        )
            // Attributes
            .pspMerchant("Merchant Ltd.")
            .shopperStatement("MyBrand.com")
            .merchantCategoryCode("5499")
            .pspMerchantURL("www.merchant.com")
            .getRequest();

        Response selectAccountResponse = api.sendRequest(selectAccountRequest);

        Assertions.assertNotNull(selectAccountResponse);
        Assertions.assertNotNull(selectAccountResponse.getResult());
        Assertions.assertNull(selectAccountResponse.getError());
    }

    @Test
    void testWithdraw() {

        Request withdrawRequest = new Withdraw.Build(
            "https://test.trustly.com/trustlynotification",
            UUID_SUPPLIER.get().toString(),
            UUID_SUPPLIER.get().toString(),
            Currency.SEK,
            "Jon",
            "Doe",
            "test@example.com",
            "1990-01-20"
        )
            .externalReference("23423525234")
            .pspMerchant("Merchant Ltd.")
            .pspMerchantURL("www.merchant.com")
            .merchantCategoryCode("5499")
            .getRequest();

        Response withdrawResponse = api.sendRequest(withdrawRequest);

        Assertions.assertNotNull(withdrawResponse.getResult().getData());
        Assertions.assertNull(withdrawResponse.getError());
    }

    @Test
    void testRegisterAccountPayout() {
        String uniqueMessageId = UUID.randomUUID().toString();
        Build.SenderInformationBuilder senderInformationBuilder = new RegisterAccountPayout.Build.SenderInformationBuilder()
            .partytype("PERSON")
            .address("Street 1, 12345 Barcelona")
            .countryCode("SE")
            .firstname("Steve")
            .lastname("Smith")
            .customerID("123456789")
            .dateOfBirth("1990-03-31");

        Request registerAccountPayoutRequest = new RegisterAccountPayout.Build(
            "123123",
            "SWEDEN",
            "6112",
            "69706212",
            "Steve",
            "Smith",
            "https://test.trustly.com/trustlynotification",
            uniqueMessageId,
            "99.99",
            "SEK"
        )        // Attributes
            .pspMerchant("Merchant Ltd.")
            .shopperStatement("MyBrand.com")
            .externalReference("23423525234")
            .merchantCategoryCode("5499")
            .pspMerchant("pspMerchant")
            .pspMerchantURL("www.merchant.com")
            .merchantCategoryCode("5499")
            .senderInformation(senderInformationBuilder)
            .addressCountry("SE")
            .dateOfBirth("1990-01-20")
            .email("test@trustly.com")
            .getRequest();

        Response registerAccountPayoutResponse = api.sendRequest(registerAccountPayoutRequest);
        Assertions.assertNotNull(registerAccountPayoutResponse.getResult().getData());
        Assertions.assertNull(registerAccountPayoutResponse.getError());
    }
}
