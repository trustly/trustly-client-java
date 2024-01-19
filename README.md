[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.trustly.api/trustly-java-client/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.trustly.api/trustly-java-client/)

Trustly Java Client
===================

This is an example implementation of the communication with the Trustly API using Java. It implements the standard Payments API which includes deposits, withdrawals and refunds.

For full documentation on the Trustly API internals visit our developer website: https://eu.developers.trustly.com. All information about software flows and call patterns can be found on that site. The documentation within this code will only cover the code itself, not how you use the Trustly API.

This code is provided as-is, use it as inspiration, reference or drop it directly into your own project and use it.

If you find a problem in the code or want to extend it, feel free to fork it and send us a pull request.

The Java API is a Maven project built on Java 1.8.

It requires the following third-party frameworks:
* Jackson FasterXML
* Bouncy Castle Crypto API

Additionally if you have one or more of these dependencies on your classpath the client functionality is extended:
* Apache HttpClient 4 (will use it for request/response)
* Apache Commons HttpClient 3 (Version 4 above takes precedence if both exist)
* Hibernate Validator (will be used to validate the request payloads *before* being sent to the Trustly server)

If no HttpClient is found, the code will fallback on using a basic `URLConnection` for its request/response.

## Create Client

You can easily create an instance of the client, giving a settings object with different levels of granular options.

```Java
var client = new TrustlyApiClient(TrustlyApiClientSettings.forDefaultTest());
```

This is a shorthand to two different, more elaborate setups.

If there is an environment variable sent along to the application startup, it will load the username, password and certificates from the default environment variable names:

* `CLIENT_USERNAME`
* `CLIENT_PASSWORD`
* `CLIENT_CERT_PUBLIC`
* `CLIENT_CERT_PRIVATE`

These can of course be modified to something else, they are just the default names.
The `CLIENT_CERT_PUBLIC` and `CLIENT_CERT_PRIVATE` are not the paths to the certificate, but the certificates themselves in UTF-8 charset.

If an environment variable was found, it is virtually the same as create a client using this setup:

1.
```Java
var client = new TrustlyApiClient(TrustlyApiClientSettings
                    .forTest()
                    .withCredentialsFromEnv("CLIENT_USERNAME", "CLIENT_PASSWORD")
                    .withCertificatesFromEnv("CLIENT_CERT_PUBLIC", "CLIENT_CERT_PRIVATE")
                    .andTrustlyCertificate());
```

Or if there is no environment variable set, it will look for files in the client's user home directory.

The default file names are:

* `trustly_client_username.txt`
* `trustly_client_password.txt`
* `trustly_client_public.pem`
* `trustly_client_private.pem`

2.
```Java
var client = new TrustlyApiClient(TrustlyApiClientSettings
                .forTest()
                .withCredentialsFromUserHome("trustly_client_username.txt", "trustly_client_password.txt")
                .withCertificatesFromUserHome("trustly_client_public.pem", "trustly_client_private.pem")
                .andTrustlyCertificate());
```

Which can of course also be overridden and customized.

## Make a request

A Request is done as simply as:

```Java
DepositRequestData request = DepositRequestData.builder()
  .notificationUrl("https://fake.test.notification.trustly.com")
  .endUserId("name@company.com")
  .messageId(UUID.randomUUID().toString())
  .attributes(
    DepositRequestDataAttributes.builder()
      .currency("EUR")
      .amount("100.00")
      .firstname("John")
      .lastname("Doe")
      .email("name@company.com")
      .country("SE")
      .locale("sv_SE")
      .shopperStatement("Trustly Test Deposit")
      .build()
  )
  .build();

DepositResponseData response = client.deposit(request);
String redirectOrIFrameUrl = response.getUrl();
```

Where the request and response types are typesafe and easy to handle. If there ever are properties which are not represented in the model, they will be placed under the `any` dictionary properties on the respective object graph levels.

## Handle notifications

There are two ways to insert the notifications into the client.
All these will end up calling on events available on the client, these are:

* `OnAccount`
* `OnCancel`
* `OnCredit`
* `OnDebit`
* `OnPayoutConfirmation`
* `OnPending`
* `OnUnknown` (All properties will be placed in `any` dictionary property)

You register to these with event listeners:

```Java
client.addOnDebitListener(args ->
{
    System.out.println(String.format("%s was debited", args.getData().getAmount()));
    args.RespondWithOk();
};
```

---

1. Giving your servlet request and response to `TrustlyApiClientJakartaExtensions.handleNotificationRequest`

(or `TrustlyApiClientJavaxExtensions` if you are using an older Java version)

```Java
@Controller
public class Startup {
  
    @PostMapping('/trustly/notifications')
    public void notificationHook(HttpServletRequest request, HttpServletResponse response) {
      TrustlyApiClientJakartaExtensions.handleNotificationRequest(request, response);
    }
}
```

This will register an POST mapping that listens to HTTP POSTs on context path `/trustly/notifications`.

It will automatically find all instantiated Trustly Api clients (clients are registered globally in a static list) and call all of them, if there are multiple ones, until one of them has reported the notification as done by calling `respondWithOk()` or `respondWithFailed()` on the event args.
If no event listener on a client responds with `OK` nor `Failed` an exception will be thrown. If an unexpected exception is thrown, we will respond with a `Fail` with the exception message attached.

*NOTE*: For this to work you *MUST* keep a non-garbage-collected instantiation of the API Client in memory somewhere in your code.
You cannot create an API Client, do a request, and then dispose of the client. It must be kept in memory to be able to receive the request's notification sent asynchronously from Trustly's server.

---

2. Or Manually, by calling on `client.handleNotification(String jsonBody, Callback onOK, Callback onFailed)`.

This will *not* automatically send an `OK` or `Failed` response back to the Trustly server.

Instead you need to implement the `onOK` and `onFailed` callbacks, if you want to use the event args' callback methods.

If you will not use the event args' callback methods, then you do not need to supply these callback arguments, and can respond with a JsonRpc response manually.
