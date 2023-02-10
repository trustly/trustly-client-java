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

package com.trustly.api;

import com.trustly.api.security.KeyChain;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyException;
import java.security.SecureRandom;

import java.util.regex.Pattern;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trustly.api.commons.Method;
import com.trustly.api.commons.exceptions.TrustlyConnectionException;
import com.trustly.api.commons.exceptions.TrustlyDataException;
import com.trustly.api.commons.exceptions.TrustlySignatureException;
import com.trustly.api.data.request.Request;
import com.trustly.api.data.response.Response;
import com.trustly.api.security.SignatureHandler;

public class SignedAPI {

    private final SignatureHandler signatureHandler = new SignatureHandler();

    private static final Pattern TEST_ENVIRONMENT_API_URL_AMERICA = Pattern.compile(".*?\\.one");
    private static final String TEST_ENVIRONMENT_API_URL = "https://test.trustly.com/api/1";
    private static final String LIVE_ENVIRONMENT_API_URL = "https://api.trustly.com/1";

    private String apiUrl;

    /**
     * Method used for initializing a SignatureHandler.
     *
     * @param keyPassword Seeing that the private key is somewhat considered a password in itself, the private key password is usually an
     *                    empty string.
     */
    public void init(final String privateKeyPath, final String keyPassword, final String username, final String password)
        throws IOException {
        init(privateKeyPath, keyPassword, username, password, false);
    }

    public void init(
        final String privateKeyPath,
        final String keyPassword,
        final String username,
        final String password,
        final boolean testEnvironment
    ) throws IOException {
        init(
            Files.newInputStream(Paths.get(privateKeyPath)),
            keyPassword,
            username,
            password,
            testEnvironment ? TEST_ENVIRONMENT_API_URL : LIVE_ENVIRONMENT_API_URL,
            new KeyChain(testEnvironment)
        );
    }

    public void init(
        final InputStream privateKey,
        final String keyPassword,
        final String username,
        final String password,
        final boolean testEnvironment
    ) {
        init(
            privateKey,
            keyPassword,
            username,
            password,
            testEnvironment ? TEST_ENVIRONMENT_API_URL : LIVE_ENVIRONMENT_API_URL,
            new KeyChain(testEnvironment)
        );
    }

    /**
     * Method used for initializing a SignatureHandler.
     *
     * @param keyPassword Usually an empty string
     */
    public void init(
        final InputStream privateKey,
        final String keyPassword,
        final String username,
        final String password,
        final String url,
        final KeyChain keyChain
    ) {

        if (TEST_ENVIRONMENT_API_URL_AMERICA.matcher(url).matches()) {
            throw new IllegalArgumentException("You seem to have given an URL to the American Trustly API; This library is for the EMEA version");
        }

        this.apiUrl = url;

        try {
            signatureHandler.init(privateKey, keyPassword, username, password, keyChain);
        } catch (final KeyException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends given request to Trustly.
     *
     * @param request Request to send to Trustly API
     * @return Response generated from the request.
     */
    public Response sendRequest(final Request request) {
        final GsonBuilder gsonBuilder = new GsonBuilder();

        if (request.getMethod() == Method.VIEW_AUTOMATIC_SETTLEMENT_DETAILS_CSV) {
            gsonBuilder.serializeNulls();
        }

        final Gson gson = gsonBuilder.create();

        signatureHandler.insertCredentials(request);
        signatureHandler.signRequest(request);

        final String jsonResponse = newHttpPost(gson.toJson(request, Request.class));

        return handleJsonResponse(jsonResponse, request.getUUID());
    }

    /**
     * Sends a POST data to Trustly server.
     *
     * @param request String representation of a request.
     * @return String representation of a response.
     */
    private String newHttpPost(final String request) {
        try (final CloseableHttpClient httpClient = HttpClients.createDefault()) {
            final HttpPost httpPost = new HttpPost(apiUrl);
            final StringEntity jsonRequest = new StringEntity(request, "UTF-8");
            httpPost.addHeader("content-type", "application/json");
            httpPost.setEntity(jsonRequest);

            final HttpResponse result = httpClient.execute(httpPost);
            return EntityUtils.toString(result.getEntity(), "UTF-8");
        } catch (final IOException e) {
            throw new TrustlyConnectionException("Failed to send request.", e);
        }
    }

    /**
     * Deserializes and verifies incoming response.
     *
     * @param responseJson response from Trustly.
     * @param requestUUID  UUID from the request that resulted in the response.
     * @return Response object
     */
    private Response handleJsonResponse(final String responseJson, final String requestUUID) {
        final Gson gson = new Gson();
        final Response response = gson.fromJson(responseJson, Response.class);
        verifyResponse(response, requestUUID);
        return response;
    }

    private void verifyResponse(final Response response, final String requestUUID) {
        if (!signatureHandler.verifyResponseSignature(response)) {
            throw new TrustlySignatureException("Incoming data signature is not valid");
        }
        if (response.getUUID() != null && !response.getUUID().equals(requestUUID)) {
            throw new TrustlyDataException("Incoming data signature is not valid");
        }
    }

    /**
     * Generates a random messageID. Good for testing.
     *
     * @return return a random generated messageid.
     */
    public String newMessageID() {
        final SecureRandom random = new SecureRandom();

        return new BigInteger(130, random).toString(32);
    }

    SignatureHandler getSignatureHandler() {
        return signatureHandler;
    }
}
