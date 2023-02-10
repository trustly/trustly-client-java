/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Trustly Group AB
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.trustly.api.security;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.trustly.api.commons.exceptions.TrustlySignatureException;
import com.trustly.api.data.notification.Notification;
import com.trustly.api.data.request.Request;
import com.trustly.api.data.request.RequestData;
import com.trustly.api.data.response.ErrorBody;
import com.trustly.api.data.response.Response;
import com.trustly.api.data.response.Result;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Base64;
import java.util.TreeSet;
import java.util.UUID;

public class SignatureHandler {

    private final Base64.Encoder base64Encoder = Base64.getEncoder();
    private final Base64.Decoder base64Decoder = Base64.getDecoder();
    private KeyChain keyChain;

    private String username;
    private String password;

    public void init(
        final InputStream privateKey,
        final String keyPassword,
        final String username,
        final String password,
        final KeyChain keyChain
    ) throws KeyException {
        this.username = username;
        this.password = password;
        this.keyChain = keyChain;

        this.keyChain.loadMerchantPrivateKey(privateKey, keyPassword);
    }

    /**
     * Inserts the api credentials into the given request.
     * @param request Request in which the credentials are inserted.
     */
    public void insertCredentials(final Request request) {
        request.getParams().getData().setUsername(username);
        request.getParams().getData().setPassword(password);
    }

    /**
     * Creates a signature for given request.
     * @param request Request to sign.
     */
    public void signRequest(final Request request) {
        final RequestData requestData = request.getParams().getData();
        final String requestMethod = request.getMethod().toString();
        final String uuid = request.getUUID();
        final String plainText = String.format("%s%s%s", requestMethod, uuid, serializeData(requestData));

        final String signedData = createSignature(plainText);

        request.getParams().setSignature(signedData);
    }

    public String serializeData(Object requestData) {
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(requestData);
        StringBuilder serializedData = new StringBuilder();
        serializeJsonData(jsonElement, serializedData);

        return serializedData.toString();
    }

    private void serializeJsonData(final JsonElement data, StringBuilder builder) {

        TreeSet<String> keys = new TreeSet<>(data.getAsJsonObject().keySet());

        for (String key : keys) {
            JsonElement value = data.getAsJsonObject().get(key);

            if (value.isJsonNull()) {
                continue;
            }

            if (value.isJsonPrimitive()) {
                builder.append(key).append(value.getAsString());
            } else if (value.isJsonArray() || value.isJsonObject()) {
                builder.append(key);
                serializeJsonData(value, builder);
            }
        }
    }

    /**
     * Creates a signature for given notification response.
     * @param response The notification response to sign.
     */
    public void signNotificationResponse(final Response response) {
        final String requestMethod = response.getResult().getMethod().toString();
        final String uuid = response.getUUID();
        final Object data = response.getResult().getData();
        final String plainText = String.format("%s%s%s", requestMethod, uuid, serializeData(data));

        final String signedData = createSignature(plainText);

        response.getResult().setSignature(signedData);
    }

    private String createSignature(final String plainText) {
        try {
            final Signature signatureInstance = Signature.getInstance("SHA1withRSA");
            signatureInstance.initSign(keyChain.getMerchantPrivateKey());
            signatureInstance.update(plainText.getBytes(StandardCharsets.UTF_8));

            final byte[] signature = signatureInstance.sign();
            return base64Encoder.encodeToString(signature);
        }
        catch (NoSuchAlgorithmException e) {
            throw new TrustlySignatureException(e);
        }
        catch (final InvalidKeyException e) {
            throw new TrustlySignatureException("Invalid private key", e);
        }
        catch (final SignatureException e) {
            throw new TrustlySignatureException("Failed to create signature", e);
        }
    }

    /**
     * Verifies the signature of an incoming response.
     * @param response The response to verify
     * @return true if signature is valid
     */
    public boolean verifyResponseSignature(final Response response)  {
        final String method;
        String uuid;
        final String serializedData;
        final String signatureBase64;

        if (response.successfulResult()) {
            final Result result = response.getResult();
            method = result.getMethod() == null ? "" : result.getMethod().toString();
            uuid = result.getUuid();
            serializedData = serializeData(result.getData());
            signatureBase64 = result.getSignature();
        }
        else {
            final ErrorBody error = response.getError().getError();
            method = error.getMethod() == null ? "" : error.getMethod().toString();
            uuid = error.getUuid();
            serializedData = serializeData(error.getData());
            signatureBase64 = error.getSignature();
        }

        if (uuid == null) {
            uuid = "";
        }

        return performSignatureVerification(method, uuid, serializedData, signatureBase64);
    }

    /**
     * Verifies the signature of an incoming notification.
     * @param notification The notification to verify
     * @return true if signature is valid
     */
    public boolean verifyNotificationSignature(final Notification notification) {
        final String method = notification.getMethod().toString();
        final String uuid = notification.getUUID();
        final String serializedData = serializeData(notification.getParams().getData());
        final String signatureBase64 = notification.getParams().getSignature();

        return performSignatureVerification(method, uuid, serializedData, signatureBase64);
    }

    private boolean performSignatureVerification(final String method, final String uuid, final String serializedData, final String responseSignature) {
        try {
            final byte[] signature = base64Decoder.decode(responseSignature);
            final Signature signatureInstance = Signature.getInstance("SHA1withRSA");
            signatureInstance.initVerify(keyChain.getTrustlyPublicKey());
            final String expectedPlainText = String.format("%s%s%s", method, uuid, serializedData);
            signatureInstance.update(expectedPlainText.getBytes("UTF-8"));
            return signatureInstance.verify(signature);
        }
        catch (final IOException e) {
            throw new TrustlySignatureException("Failed to decode signature", e);
        }
        catch (final NoSuchAlgorithmException e) {
            throw new TrustlySignatureException(e);
        }
        catch (final InvalidKeyException e) {
            throw new TrustlySignatureException("Invalid public key", e);
        }
        catch (final SignatureException e) {
            throw new TrustlySignatureException("Failed to verify signature", e);
        }
    }

    /**
     * Generates a new UUID (Universally Unique Identifier) based on UUID v4
     * @return a random generated UUID in form of xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx
     * where x is a hexadecimal digit (0-9 and A-F)
     */
    public static String generateNewUUID() {
        return UUID.randomUUID().toString();
    }
}
