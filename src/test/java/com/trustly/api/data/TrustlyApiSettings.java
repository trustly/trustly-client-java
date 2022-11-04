package com.trustly.api.data;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TrustlyApiSettings {

  private static final String clientUsername = "trustly_client_username.txt";
  private static final String clientPassword = "trustly_client_password.txt";
  private static final String clientPublicKeyFileName = "trustly_client_public.pem";
  private static final String clientPrivateKeyFileName = "trustly_client_private.pem";
  private static final String userHomePath = System.getProperty("user.home");

  public static String getClientPublicKeyFromUserHome() {
    return userHomePath + "/" + clientPublicKeyFileName;
  }

  public static String getClientPrivateKeyFromUserHome() {
    return userHomePath + "/" + clientPrivateKeyFileName;
  }

  public static String getClientPassword() {

    try {
      byte[] encoded = Files.readAllBytes(Paths.get(userHomePath + "/" + clientPassword));
      return new String(encoded, Charset.defaultCharset()).trim();
    } catch (IOException ioe) {
      return "";
    }
  }

  public static String getClientUsername() {

    try {
      byte[] encoded = Files.readAllBytes(Paths.get(userHomePath + "/" + clientUsername));
      return new String(encoded, Charset.defaultCharset()).trim();
    } catch (IOException ioe) {
      return "";
    }
  }
}
