package com.trustly.api.client;

import com.trustly.api.util.TrustlyFileUtils;
import com.trustly.api.util.TrustlyStringUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Locale;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

public class TrustlyApiClientSettings {

  static final String URL_TEST = "https://test.trustly.com/api/1";
  static final String URL_PRODUCTION = "https://api.trustly.com/1";

  private String url;

  private String username;

  private String password;

  private PublicKey clientPublicKey;

  private PrivateKey clientPrivateKey;

  private PublicKey trustlyPublicKey;

  private boolean includeMessageInNotificationResponse = true;

  private boolean includeExceptionMessageInNotificationResponse = false;

  public String getUrl() {
    return url;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public PublicKey getClientPublicKey() {
    return clientPublicKey;
  }

  public PrivateKey getClientPrivateKey() {
    return clientPrivateKey;
  }

  public PublicKey getTrustlyPublicKey() {
    return trustlyPublicKey;
  }

  public boolean isIncludeMessageInNotificationResponse() {
    return includeMessageInNotificationResponse;
  }

  public void setIncludeMessageInNotificationResponse(boolean includeMessageInNotificationResponse) {
    this.includeMessageInNotificationResponse = includeMessageInNotificationResponse;
  }

  public boolean isIncludeExceptionMessageInNotificationResponse() {
    return includeExceptionMessageInNotificationResponse;
  }

  public void setIncludeExceptionMessageInNotificationResponse(boolean includeExceptionMessageInNotificationResponse) {
    this.includeExceptionMessageInNotificationResponse = includeExceptionMessageInNotificationResponse;
  }

  private TrustlyApiClientSettings() {
  }

  /**
   * Creates settings instance, by default looking among environment variables, or using the given parameters.
   *
   * @param username       Username; If null, looks for env or in user home.
   * @param password       Password; If null, looks for env or in user home.
   * @param publicKeyPath  Public Key; If null, looks for env or in user home.
   * @param privateKeyPath Private Key; If null, looks for env or in user home.
   * @param envUsername    Name of username env variable
   * @param envPassword    Name of password env variable
   * @param envCertPublic  Name of public key env variable
   * @param envCertPrivate Name of private key env variable
   * @return The complete settings instance to use with {@link TrustlyApiClient}.
   */
  public static TrustlyApiClientSettings forDefaultProduction(
    String username,
    String password,
    String publicKeyPath,
    String privateKeyPath,

    String envUsername,
    String envPassword,
    String envCertPublic,
    String envCertPrivate
  ) throws IOException {
    return forDefaultCustom(URL_PRODUCTION,
                            username, password, publicKeyPath, privateKeyPath,
                            envUsername, envPassword, envCertPublic, envCertPrivate
    );
  }

  /**
   * Creates settings instance, by default looking among environment variables, or using the given parameters.
   *
   * @param username       Username; If null, looks for env or in user home.
   * @param password       Password; If null, looks for env or in user home.
   * @param publicKeyPath  Public Key; If null, looks for env or in user home.
   * @param privateKeyPath Private Key; If null, looks for env or in user home.
   * @param envUsername    Name of username env variable
   * @param envPassword    Name of password env variable
   * @param envCertPublic  Name of public key env variable
   * @param envCertPrivate Name of private key env variable
   * @return The complete settings instance to use with {@link TrustlyApiClient}.
   */
  public static TrustlyApiClientSettings forDefaultTest(
    String username,
    String password,
    String publicKeyPath,
    String privateKeyPath,

    String envUsername,
    String envPassword,
    String envCertPublic,
    String envCertPrivate
  ) throws IOException {
    return forDefaultCustom(
      URL_TEST,
      username, password, publicKeyPath, privateKeyPath,
      envUsername, envPassword, envCertPublic, envCertPrivate
    );
  }

  /**
   * Quickly create a settings instance with a custom target URL.
   *
   * @param url            Custom URL for the client to send its requests to
   * @param username       Username of the merchant
   * @param password       Password for the merchant user
   * @param publicKeyPath  Path to public key. If null, it will be looked for in user home
   * @param privateKeyPath Path to private key. If null, it will be looked for in user home
   * @param envUsername    Name of username env variable
   * @param envPassword    Name of password env variable
   * @param envCertPublic  Name of public key env variable
   * @param envCertPrivate Name of private key env variable
   *                       @return The complete settings instance to use with {@link TrustlyApiClient}.
   */
  public static TrustlyApiClientSettings forDefaultCustom(
    String url,
    String username,
    String password,
    String publicKeyPath,
    String privateKeyPath,

    String envUsername,
    String envPassword,
    String envCertPublic,
    String envCertPrivate
  ) throws IOException {

    envUsername = (envUsername == null) ? "CLIENT_USERNAME" : envUsername;
    envPassword = (envPassword == null) ? "CLIENT_PASSWORD" : envPassword;
    envCertPublic = (envCertPublic == null) ? "CLIENT_CERT_PUBLIC" : envCertPublic;
    envCertPrivate = (envCertPrivate == null) ? "CLIENT_CERT_PRIVATE" : envCertPrivate;

    WithEnvironment settings = new WithEnvironment(new TrustlyApiClientSettings(), url);

    boolean hasEnvUsername = !TrustlyStringUtils.isBlank(System.getenv(envUsername));

    if (hasEnvUsername) {
      return forTest()
        .withCredentialsFromEnv(envUsername, envPassword)
        .withCertificatesFromEnv(envCertPublic, envCertPrivate)
        .andTrustlyCertificate();
    } else {
      WithCredentials withCredentials;
      if (TrustlyStringUtils.isBlank(username)) {
        withCredentials = settings
          .withCredentialsFromUserHome(null, null);
      } else {
        withCredentials = settings
          .withCredentials(username, password);
      }

      WithClientCertificates withCertificates;
      if (TrustlyStringUtils.isBlank(privateKeyPath)) {
        withCertificates = withCredentials
          .withCertificatesFromUserHome(null, null);
      } else {
        withCertificates = withCredentials
          .withCertificatesFromFiles(publicKeyPath, privateKeyPath);
      }

      return withCertificates.andTrustlyCertificate();
    }
  }

  public static WithEnvironment forProduction() {
    return new WithEnvironment(new TrustlyApiClientSettings(), URL_PRODUCTION);
  }

  public static WithEnvironment forTest() {
    return new WithEnvironment(new TrustlyApiClientSettings(), URL_TEST);
  }

  public static WithEnvironment forCustom(String url) {
    if (TrustlyStringUtils.isBlank(url)) {
      throw new IllegalArgumentException("The URL must not be null nor empty");
    }

    return new WithEnvironment(new TrustlyApiClientSettings(), url);
  }

  public static class WithEnvironment {

    private final TrustlyApiClientSettings settings;

    public WithEnvironment(TrustlyApiClientSettings settings, String url) {
      this.settings = settings;
      this.settings.url = url;
    }

    /**
     * For internal use, do not use. You must supply credentials to be able to make requests.
     */
    public WithCredentials withoutCredentials() {
      return new WithCredentials(this.settings, null, null);
    }

    public WithCredentials withCredentials(String username, String password) {
      return new WithCredentials(this.settings, username, password);
    }

    public WithCredentials withCredentialsFromEnv(String envUsername, String envPassword) {

      envUsername = (envUsername == null) ? "CLIENT_USERNAME" : envUsername;
      envPassword = (envPassword == null) ? "CLIENT_PASSWORD" : envPassword;

      return new WithCredentials(
        this.settings,
        System.getenv(envUsername),
        System.getenv(envPassword)
      );
    }

    public WithCredentials withCredentialsFromUserHome() {
      return this.withCredentialsFromUserHome(null, null);
    }

    public WithCredentials withCredentialsFromUserHome(
      String clientUsernameFileName,
      String clientPasswordFileName
    ) {

      String directory = TrustlyApiClientSettings.getUserHome();

      try {
        return this.withCredentialsFromDirectory(directory, clientUsernameFileName, clientPasswordFileName);
      } catch (IOException ex) {
        throw new IllegalArgumentException("Could not load credentials from user home", ex);
      }
    }

    public WithCredentials withCredentialsFromDirectory(
      String directoryPath,
      String clientUsernameFileName,
      String clientPasswordFileName
    ) throws IOException {

      clientUsernameFileName = (clientUsernameFileName == null) ? "trustly_client_username.txt" : null;
      clientPasswordFileName = (clientPasswordFileName == null) ? "trustly_client_password.txt" : null;

      String usernamePath = Paths.get(directoryPath, clientUsernameFileName).toString();
      String passwordPath = Paths.get(directoryPath, clientPasswordFileName).toString();

      return this.withCredentialsFromFiles(usernamePath, passwordPath);
    }

    public WithCredentials withCredentialsFromFiles(String usernamePath, String passwordPath) throws IOException {
      if (!new File(usernamePath).exists()) {
        throw new IllegalArgumentException(String.format("Cannot create api settings since username key file %s is missing", usernamePath));
      }
      if (!new File(passwordPath).exists()) {
        throw new IllegalArgumentException(String.format("Cannot create api settings since password key file %s is missing", passwordPath));
      }

      return new WithCredentials(
        this.settings,
        TrustlyFileUtils.readAllText(usernamePath).trim(),
        TrustlyFileUtils.readAllText(passwordPath).trim()
      );
    }
  }

  public static class WithCredentials {

    private final TrustlyApiClientSettings settings;

    public WithCredentials(TrustlyApiClientSettings settings, String username, String password) {
      this.settings = settings;
      this.settings.username = username;
      this.settings.password = password;
    }

    public WithClientCertificates withCertificatesFromEnv(String envCertPublic, String envCertPrivate) throws IOException {

      envCertPublic = (envCertPublic == null) ? "CLIENT_CERT_PUBLIC" : envCertPublic;
      envCertPrivate = (envCertPrivate == null) ? "CLIENT_CERT_PRIVATE" : envCertPrivate;

      String certPublic = System.getenv(envCertPublic);
      String certPrivate = System.getenv(envCertPrivate);

      try (InputStream streamPublic = new ByteArrayInputStream(StandardCharsets.UTF_8.encode(certPublic).array())) {
        try (InputStream streamPrivate = new ByteArrayInputStream(StandardCharsets.UTF_8.encode(certPrivate).array())) {
          return this.withCertificatesFromStreams(streamPublic, streamPrivate);
        }
      }
    }

    public WithClientCertificates withCertificatesFromUserHome() {
      return this.withCertificatesFromUserHome(null, null);
    }

    public WithClientCertificates withCertificatesFromUserHome(
      String clientPublicKeyFileName,
      String clientPrivateKeyFileName
    ) {

      clientPublicKeyFileName = (clientPublicKeyFileName == null) ? "trustly_client_public.pem" : clientPublicKeyFileName;
      clientPrivateKeyFileName = (clientPrivateKeyFileName == null) ? "trustly_client_private.pem" : clientPrivateKeyFileName;

      String directory = TrustlyApiClientSettings.getUserHome();
      return this.withCertificatesFromDirectory(directory, clientPublicKeyFileName, clientPrivateKeyFileName);
    }

    public WithClientCertificates withCertificatesFromDirectory(
      String directoryPath,
      String clientPublicKeyFileName,
      String clientPrivateKeyFileName
    ) {

      clientPublicKeyFileName = (clientPublicKeyFileName == null) ? "trustly_client_public.pem" : clientPublicKeyFileName;
      clientPrivateKeyFileName = (clientPrivateKeyFileName == null) ? "trustly_client_private.pem" : clientPrivateKeyFileName;

      return this.withCertificatesFromFiles(
        Paths.get(directoryPath, clientPublicKeyFileName).toString(),
        Paths.get(directoryPath, clientPrivateKeyFileName).toString()
      );
    }

    public WithClientCertificates withCertificatesFromFiles(String clientPublicKeyPath, String clientPrivateKeyPath) {
      try (FileInputStream publicFileStream = new FileInputStream(clientPublicKeyPath)) {
        try (FileInputStream privateFileStream = new FileInputStream(clientPrivateKeyPath)) {
          return this.withCertificatesFromStreams(publicFileStream, privateFileStream);
        }
      } catch (IOException ex) {
        throw new IllegalArgumentException("Could not read certificates from given paths", ex);
      }
    }

    public WithClientCertificates withCertificatesFromStreams(
      InputStream publicFileStream,
      InputStream privateFileStream
    ) {

      try {
        PublicKey javaPublicKey = streamToJavaPublicKey(publicFileStream, null);
        PrivateKey javaPrivateKey = streamToJavaPrivateKey(privateFileStream, null);

        return new WithClientCertificates(this.settings, javaPublicKey, javaPrivateKey);
      } catch (IOException ex) {
        throw new IllegalArgumentException("Could not initialize with certificates from stream", ex);
      }
    }
  }

  public static class WithClientCertificates {

    private final TrustlyApiClientSettings settings;

    public WithClientCertificates(TrustlyApiClientSettings settings, PublicKey clientPublicKey, PrivateKey clientPrivateKey) {
      this.settings = settings;

      this.settings.clientPublicKey = clientPublicKey;
      this.settings.clientPrivateKey = clientPrivateKey;
    }

    public TrustlyApiClientSettings andTrustlyCertificate() {
      if (TrustlyApiClientSettings.URL_PRODUCTION.equals(this.settings.getUrl())) {
        return this.andTrustlyCertificateProduction();
      } else if (TrustlyApiClientSettings.URL_TEST.equals(this.settings.getUrl())) {
        return this.andTrustlyCertificateTest();
      } else {
        throw new IllegalArgumentException(
          "You can only automatically choose the Trustly certificate if you used the ForProduction() or ForTest() builder steps");
      }
    }

    public TrustlyApiClientSettings andTrustlyCertificateProduction() {

      return this.andTrustlyCertificateFromStream(
        this.getClass().getResourceAsStream("/keys/trustly_live_public.pem")
      );
    }

    public TrustlyApiClientSettings andTrustlyCertificateTest() {

      return this.andTrustlyCertificateFromStream(
        this.getClass().getResourceAsStream("/keys/trustly_test_public.pem")
      );
    }

    public TrustlyApiClientSettings andTrustlyCertificateFromUserHome(String trustlyPublicKeyFileName) throws IOException {

      trustlyPublicKeyFileName = (trustlyPublicKeyFileName == null) ? "trustly_public.pem" : trustlyPublicKeyFileName;

      String directory = TrustlyApiClientSettings.getUserHome();
      return this.andTrustlyCertificateFromDirectory(directory, trustlyPublicKeyFileName);
    }

    public TrustlyApiClientSettings andTrustlyCertificateFromDirectory(String directoryPath, String trustlyPublicKeyFileName)
      throws IOException {

      trustlyPublicKeyFileName = (trustlyPublicKeyFileName == null) ? "trustly_public.pem" : trustlyPublicKeyFileName;

      return this.andTrustlyCertificateFromFile(
        Paths.get(directoryPath, trustlyPublicKeyFileName).toString()
      );
    }

    public TrustlyApiClientSettings andTrustlyCertificateFromFile(String filePath) throws IOException {
      try (FileInputStream publicFileStream = new FileInputStream(filePath)) {
        return this.andTrustlyCertificateFromStream(publicFileStream);
      }
    }

    public TrustlyApiClientSettings andTrustlyCertificateFromStream(InputStream stream) {

      try {

        this.settings.trustlyPublicKey = streamToJavaPublicKey(stream, null);
        if (this.settings.trustlyPublicKey == null) {
          throw new IllegalArgumentException("Failed to load Trustly public key from stream");
        }

        if (TrustlyStringUtils.isBlank(this.settings.getUsername())) {
          throw new IllegalArgumentException("The username must be set");
        }

        if (TrustlyStringUtils.isBlank(this.settings.getPassword())) {
          throw new IllegalArgumentException("The password must be set");
        }

        return this.settings;
      } catch (IOException ex) {
        throw new IllegalArgumentException("Could not load Trustly certificate from input stream", ex);
      }
    }
  }

  private static String getUserHome() {
    return System.getProperty("user.home");
  }

  private static PemObject readerToPemObject(InputStream is) throws IOException {

    try (InputStreamReader publicReader = new InputStreamReader(is)) {

      PemReader publicPemParser = new PemReader(publicReader);
      return publicPemParser.readPemObject();
    }
  }

  private static PublicKey streamToJavaPublicKey(InputStream is, String filename) throws IOException {

    byte[] content;
    if (filename != null && filename.toLowerCase(Locale.ROOT).endsWith(".der")) {

      int bufferSize = 1024;
      byte[] buffer = new byte[bufferSize];
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      for (int numRead; (numRead = is.read(buffer, 0, buffer.length)) > 0; ) {
        baos.write(buffer, 0, numRead);
      }

      content = baos.toByteArray();

    } else {
      PemObject publicObject = readerToPemObject(is);
      content = publicObject.getContent();
    }

    X509EncodedKeySpec spec = new X509EncodedKeySpec(content);

    try {

      if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
        Security.addProvider(new BouncyCastleProvider());
      }

      KeyFactory factory = KeyFactory.getInstance("RSA", BouncyCastleProvider.PROVIDER_NAME);
      return factory.generatePublic(spec);
    } catch (NoSuchAlgorithmException e) {
      throw new IOException("Could not find the required algorithm", e);
    } catch (InvalidKeySpecException e) {
      throw new IOException("Could not load the public key because of an invalid key spec", e);
    } catch (NoSuchProviderException e) {
      throw new IOException("Could not find the BouncyCastle key provider", e);
    }
  }

  private static PrivateKey streamToJavaPrivateKey(InputStream is, String filename) throws IOException {

    byte[] content;
    if (filename != null && filename.toLowerCase(Locale.ROOT).endsWith(".der")) {

      int bufferSize = 1024;
      byte[] buffer = new byte[bufferSize];
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      for (int numRead; (numRead = is.read(buffer, 0, buffer.length)) > 0; ) {
        baos.write(buffer, 0, numRead);
      }

      content = baos.toByteArray();

    } else {
      PemObject publicObject = readerToPemObject(is);
      content = publicObject.getContent();
    }

    PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(content);

    try {

      if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
        Security.addProvider(new BouncyCastleProvider());
      }

      KeyFactory factory = KeyFactory.getInstance("RSA", BouncyCastleProvider.PROVIDER_NAME);
      return factory.generatePrivate(spec);
    } catch (NoSuchAlgorithmException e) {
      throw new IOException("Could not find the required algorithm", e);
    } catch (InvalidKeySpecException e) {
      throw new IOException("Could not load the public key because of an invalid key spec", e);
    } catch (NoSuchProviderException e) {
      throw new IOException("Could not find the BouncyCastle key provider", e);
    }
  }
}
