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
import java.util.UUID;

import com.google.gson.annotations.SerializedName;
import com.trustly.api.commons.exceptions.TrustlyAPIException;
import com.trustly.api.commons.exceptions.TrustlySignatureException;
import com.trustly.api.data.notification.Notification;
import com.trustly.api.data.request.Request;
import com.trustly.api.data.request.RequestData;
import com.trustly.api.data.response.*;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class SignatureHandler {
    private static SignatureHandler instance;

    private final BASE64Encoder base64Encoder = new BASE64Encoder();
    private final BASE64Decoder base64Decoder = new BASE64Decoder();
    private KeyChain keyChain;

    private String username;
    private String password;

    public SignatureHandler() {
    }

    public static SignatureHandler getInstance() {
        if (instance == null) {
            instance = new SignatureHandler();
        }
        return instance;
    }

    public void init(String privateKeyPath, String keyPassword, String username, String password, boolean testEnvironment) throws KeyException {
        this.username = username;
        this.password = password;

        keyChain = new KeyChain(testEnvironment);
        keyChain.loadMerchantPrivateKey(privateKeyPath, keyPassword);
    }

    /**
     * Inserts the api credentials into the given request.
     * @param request Request in which the credentials are inserted.
     */
    public void insertCredentials(Request request) {
        request.getParams().getData().setUsername(username);
        request.getParams().getData().setPassword(password);
    }

    /**
     * Creates a signature for given request.
     * @param request Request to sign.
     */
    public void signRequest(Request request) {
        RequestData requestData = request.getParams().getData();
        String requestMethod = request.getMethod().toString();
        String uuid = request.getUUID();
        String plainText = String.format("%s%s%s", requestMethod, uuid, serializeData(requestData));

        String signedData = createSignature(plainText);

        request.getParams().setSignature(signedData);
    }

    /**
     * Creates a signature for given notification response.
     * @param response The notification response to sign.
     */
    public void signNotificationResponse(Response response) {
        String requestMethod = response.getResult().getMethod().toString();
        String uuid = response.getUUID();
        Map<String, Object> data = response.getResult().getData();
        String plainText = String.format("%s%s%s", requestMethod, uuid, serializeMap(data));

        String signedData = createSignature(plainText);

        response.getResult().setSignature(signedData);
    }

    private String createSignature(String plainText) {
        try {
            Signature s = Signature.getInstance("SHA1withRSA");
            s.initSign(keyChain.getMerchantPrivateKey());
            s.update(plainText.getBytes("UTF-8"));

            byte[] signature = s.sign();
            return base64Encoder.encode(signature);
        }
        catch (UnsupportedEncodingException e) {
            throw new TrustlySignatureException(e);
        }
        catch (NoSuchAlgorithmException e) {
            throw new TrustlySignatureException(e);
        }
        catch (InvalidKeyException e) {
            throw new TrustlySignatureException("Invalid private key", e);
        }
        catch (SignatureException e) {
            throw new TrustlySignatureException("Failed to create signature", e);
        }
    }

    private String serializeData(Object data) {
        try {
            //Sort all fields found in the data object class
            List<Field> fields = getAllFields(new LinkedList<Field>(), data.getClass());
            Collections.sort(
                    fields, new Comparator<Field>() {
                @Override
                public int compare(Field o1, Field o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });

            //Get values using reflection
            StringBuilder b = new StringBuilder();
            for (Field field : fields) {
                String jsonFieldName;
                if (field.isAnnotationPresent(SerializedName.class)) {
                    jsonFieldName = field.getAnnotation(SerializedName.class).value();
                }
                else {
                    jsonFieldName = field.getName();
                }
                b.append(jsonFieldName);

                if (field.get(data) == null) {
                    continue;
                }

                if (field.getType().equals(Map.class)) {
                    b.append(serializeMap((Map) field.get(data)));
                }
                else {
                    b.append(field.get(data));
                }
            }
            return b.toString();
        }
        catch (IllegalAccessException e) {
            throw new TrustlyAPIException("Failed to serialize data", e);
        }
    }

    private String serializeMap(Map map) {
        StringBuilder b = new StringBuilder();

        ArrayList<String> strings = new ArrayList<String>(map.keySet());
        Collections.sort(strings);
        for (String key : strings) {
            b.append(key);
            if(map.get(key) != null) {
                b.append(map.get(key));
            }
        }

        return b.toString();
    }

    /**
     * Returns a list of declared fields for given class
     * @param fields List to add found fields
     * @param type Type of class
     * @return Given list filled with fields of given class.
     */
    private List<Field> getAllFields(List<Field> fields, Class<?> type) {
        for (Field field: type.getDeclaredFields()) {
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
    public boolean verifyResponseSignature(Response response)  {
        String method;
        String uuid;
        String serializedData;
        String signatureBase64;

        if (response.successfulResult()) {
            Result result = response.getResult();
            method = (result.getMethod() == null) ? "" : result.getMethod().toString();
            uuid = result.getUuid();
            serializedData = serializeMap(result.getData());
            signatureBase64 = result.getSignature();
        }
        else {
            ErrorBody error = response.getError().getError();
            method = (error.getMethod() == null) ? "" : error.getMethod().name();
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
    public boolean verifyNotificationSignature(Notification notification) {
        String method = notification.getMethod().toString();
        String uuid = notification.getUUID();
        String serializedData = serializeData(notification.getParams().getData());
        String signatureBase64 = notification.getParams().getSignature();

        return performSignatureVerification(method, uuid, serializedData, signatureBase64);
    }

    private boolean performSignatureVerification(String method, String uuid, String serializedData, String responseSignature) {
        String expectedPlainText = String.format("%s%s%s", method, uuid, serializedData);
        try {
            byte[] signature = base64Decoder.decodeBuffer(responseSignature);
            Signature s = Signature.getInstance("SHA1withRSA");
            s.initVerify(keyChain.getTrustlyPublicKey());
            s.update(expectedPlainText.getBytes("UTF-8"));
            return s.verify(signature);
        }
        catch (IOException e) {
            throw new TrustlySignatureException("Failed to decode signature", e);
        }
        catch (NoSuchAlgorithmException e) {
            throw new TrustlySignatureException(e);
        }
        catch (InvalidKeyException e) {
            throw new TrustlySignatureException("Invalid public key", e);
        }
        catch (SignatureException e) {
            throw new TrustlySignatureException("Failed to verify signature", e);
        }
    }

    /**
     * Generates a new UUID (Universally Unique Identifier ) based on UUID v4
     * @return a random generated UUID in form of xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx
     * where x is a hexadecimal digit (0-9 and A-F)
     */
    public static String generateNewUUID() {
        return UUID.randomUUID().toString();
    }
}
