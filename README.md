Trustly Java Client
===================

This is an example implementation of the communication with the Trustly API using Java. It implements the standard Payments API which includes deposits, withdrawals and refunds.

For full documentation on the Trustly API internals visit our developer website: http://trustly.com/developer. All information about software flows and call patterns can be found on that site. The documentation within this code will only cover the code itself, not how you use the Trustly API.

This code is provided as-is, use it as inspiration, reference or drop it directly into your own project and use it.

If you find a problem in the code or want to extend it, feel free to fork it and send us a pull request.

The Java API is a Maven project built on Java 1.8. It requires the following third-party frameworks: Google Gson, Apache HttpClient and Bouncy Castle Crypto API.

Structure
=========
- `commons`

Contains exceptions, enums and deserializers used throughout the project.

- `data`

Everything under this package are data structures that serializes into json and deserializes from json.

- `requestbuilders`

Contains the builder classes for the requests. Use these to create the Trustly API requests.

- `security`

Contains classes for handling of keys and signatures.

Overview
========

The code provides wrappers for calling the Trustly API. Create an instance of the API and initialize it with your merchant credentials and use the stubs in that class for calling the API. The API will default to communicate with https://trustly.com. A secondary init method exists for communication with https://test.trustly.com.

When processing an incoming notification the handleNotification() method of the API will help with parsing and verifying the message signature. Use prepareNotificationResponse() to build a proper response object.

The examples below represent a very basic usage of the calls. A minimum of error handling around this code would be to check for the following exceptions during processing.

- `TrustlyConnectionException`

Thrown when unable to communicate with the Trustly API. This can be due to the lack of Internet connection or other forms of service errors.

- `TrustlyDataException`

Thrown upon various problems with the API returned data. For instance when a responding message contains a different UUID than the sent message or when the response structure is incomplete.

- `TrustlySignatureException`

Issued when the authenticity of messages cannot be verified. If ever this exception is caught the data in the communication should be voided as it can be a forgery.

To use the implementation, start with initializing the API. Then create your preferred type of request using any of the builder classes found under com.trustly.api.data.methods. Do not attempt to create the requests manually, instead use the builder classes. The constructor for each builder class contains the required fields of the given request, and then lets you add additional data if available for that specific request.

Initializing the API

		SignedAPI api = new SignedAPI();
    	api.init("path/to/private.pem", "keypass", "username", "password");
    	api.init("path/to/private.pem", "keypass", "username", "password", true);


Example deposit call

		Request deposit = new Deposit.Build("https://example.com/trustlynotification", "user@email.com", "abas421csd123zds1wd99", Currency.SEK, "Steve", "Smith", "steve@smith.com")
                .locale("sv_SE")
                .amount("54.12")
                .mobilePhone("070-1234567")
                .nationalIdentificationNumber("891212-4545")
                .getRequest();

        Response response = api.sendRequest(deposit);

        String iframeUrl = response.getResult().getData().get("url");

Example refund call

		Request refund = new Refund.Build("123543567", "54.12", Currency.SEK)
                .getRequest();

        Response response = api.sendRequest(refund);

Example withdrawal call

        Request withdraw = new Withdraw.Build("https://example.com/trustlynotification", "user@email.com", "41bj1h423b12bh323", Currency.PLN, "Steve", "Smith", "steve@smith.com", "1989-12-12")
                .locale("sv_SE")
                .nationalIdentificationNumber("891212-4545")
                .clearingHouse("1234")
                .accountNumber("56789012")
                .getRequest();

        Response response = api.sendRequest(withdraw);

        Assert.assertTrue(response.successfulResult());

Example of the notification process

        // A notification is sent to your web server.

        // Convert the incoming notification json to a Notification object.
        NotificationHandler handler = new NotificationHandler();
        Notification notification = handler.handleNotification(incomingNotificationJson);

        // Process and verify the incoming notification data

        Response notificationResponse = handler.prepareNotificationResponse(notification.getMethod(), notification.getUUID(), ResponseStatus.OK);

        final String responseJson = handler.toJson(notificationResponse);
        // Respond with responseJson as message body.
