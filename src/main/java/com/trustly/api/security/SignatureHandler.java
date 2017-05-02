/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Trustly Group AB
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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.security.InvalidKeyException;
import java.security.KeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedTreeMap;
import com.trustly.api.commons.exceptions.TrustlyAPIException;
import com.trustly.api.commons.exceptions.TrustlySignatureException;
import com.trustly.api.data.notification.Notification;
import com.trustly.api.data.request.Request;
import com.trustly.api.data.request.RequestData;
import com.trustly.api.data.response.ErrorBody;
import com.trustly.api.data.response.Response;
import com.trustly.api.data.response.Result;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class SignatureHandler {
    private static SignatureHandler instance;

    private final BASE64Encoder base64Encoder = new BASE64Encoder();
    private final BASE64Decoder base64Decoder = new BASE64Decoder();
    private KeyChain keyChain;

    private String username;
    private String password;

    public static SignatureHandler getInstance() {
        if (instance == null) {
            instance = new SignatureHandler();
        }
        return instance;
    }

    public void init(final String privateKeyPath, final String keyPassword, final String username, final String password, final boolean testEnvironment) throws KeyException {
        this.username = username;
        this.password = password;

        keyChain = new KeyChain(testEnvironment);
        keyChain.loadMerchantPrivateKey(privateKeyPath, keyPassword);
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

    /**
     * Creates a signature for given notification response.
     * @param response The notification response to sign.
     */
    public void signNotificationResponse(final Response response) {
        final String requestMethod = response.getResult().getMethod().toString();
        final String uuid = response.getUUID();
        final Object data = response.getResult().getData();
        final String plainText = String.format("%s%s%s", requestMethod, uuid, serializeObject(data));

        final String signedData = createSignature(plainText);

        response.getResult().setSignature(signedData);
    }

    private String createSignature(final String plainText) {
        try {
            final Signature signatureInstance = Signature.getInstance("SHA1withRSA");
            signatureInstance.initSign(keyChain.getMerchantPrivateKey());
            signatureInstance.update(plainText.getBytes("UTF-8"));

            final byte[] signature = signatureInstance.sign();
            return base64Encoder.encode(signature);
        }
        catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            throw new TrustlySignatureException(e);
        }
        catch (final InvalidKeyException e) {
            throw new TrustlySignatureException("Invalid private key", e);
        }
        catch (final SignatureException e) {
            throw new TrustlySignatureException("Failed to create signature", e);
        }
    }

    private String serializeData(final Object data) {
        return serializeData(data, true);
    }

    private String serializeData(final Object data, final boolean serializeNullMap) {
        try {
            //Sort all fields found in the data object class
            final List<Field> fields = getAllFields(new LinkedList<>(), data.getClass());
            fields.sort(Comparator.comparing(Field::getName));

            //Get values using reflection
            final StringBuilder builder = new StringBuilder();
            for (final Field field : fields) {

                final String jsonFieldName;
                if (field.isAnnotationPresent(SerializedName.class)) {
                    jsonFieldName = field.getAnnotation(SerializedName.class).value();
                }
                else {
                    jsonFieldName = field.getName();
                }

                if (field.getType().equals(Map.class)) {
                    if (serializeNullMap) {
                        builder.append(jsonFieldName);
                        if (field.get(data) != null) {
                            builder.append(serializeObject(field.get(data)));
                        }
                        continue;
                    }
                    else {
                        if (field.get(data) != null) {
                            builder.append(jsonFieldName);
                            builder.append(serializeObject(field.get(data)));
                        }
                        continue;
                    }
                }

                builder.append(jsonFieldName);

                if (field.get(data) != null) {
                    builder.append(field.get(data));
                }
            }
            return builder.toString();
        }
        catch (final IllegalAccessException e) {
            throw new TrustlyAPIException("Failed to serialize data", e);
        }
    }

    private String serializeObject(final Object object) {
        final StringBuilder builder = new StringBuilder();

        if (object instanceof TreeMap || object instanceof LinkedTreeMap) {
            populateStringBuilder(builder, (Map) object);
        }
        else if (object instanceof ArrayList) {
            for (final Object mapEntry : (ArrayList) object) {
                populateStringBuilder(builder, (Map) mapEntry);
            }
        }
        else {
            throw new RuntimeException("Unhandled class of object: " + object.getClass());
        }

        return builder.toString();
    }

    private void populateStringBuilder(final StringBuilder builder, final Map mapEntry) {
        final List<String> strings = new ArrayList<String>(mapEntry.keySet());
        Collections.sort(strings);
        for (final String key : strings) {
            builder.append(key);
            if (mapEntry.get(key) != null) {
                builder.append(mapEntry.get(key));
            }
        }
    }

    /**
     * Returns a list of declared fields for given class.
     * @param fields List to add found fields
     * @param type Type of class
     * @return Given list filled with fields of given class.
     */
    private List<Field> getAllFields(List<Field> fields, final Class<?> type) {
        for (final Field field: type.getDeclaredFields()) {
            field.setAccessible(true);
            fields.add(field);
        }

        if (type.getSuperclass() != null) {
            fields = getAllFields(fields, type.getSuperclass());
        }

        return fields;
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
            serializedData = serializeObject(result.getData());
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
        final String serializedData = serializeData(notification.getParams().getData(), false);
        final String signatureBase64 = notification.getParams().getSignature();

        return performSignatureVerification(method, uuid, serializedData, signatureBase64);
    }

    private boolean performSignatureVerification(final String method, final String uuid, final String serializedData, final String responseSignature) {
        try {
            final byte[] signature = base64Decoder.decodeBuffer(responseSignature);
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
