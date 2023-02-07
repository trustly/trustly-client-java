package com.trustly.api.data;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TrustlyApiSettings {

    private static final String clientUsername = "trustly_client_username.txt";
    private static final String clientPassword = "trustly_client_password.txt";
    private static final String userHomePath = System.getProperty("user.home");

    public static InputStream getClientPrivateKey() {
        return TrustlyApiSettings.class.getResourceAsStream("/keys/trustly_client_private.pem");
    }

    public static String getClientPassword() {

        try {
            byte[] encoded = Files.readAllBytes(Paths.get(userHomePath + "/" + clientPassword));
            return new String(encoded, StandardCharsets.UTF_8).trim();
        } catch (IOException ioe) {
            return "";
        }
    }

    public static String getClientUsername() {

        try {
            byte[] encoded = Files.readAllBytes(Paths.get(userHomePath + "/" + clientUsername));
            return new String(encoded, StandardCharsets.UTF_8).trim();
        } catch (IOException ioe) {
            return "";
        }
    }
}
