package com.trustly.api.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.trustly.api.domain.base.IData;
import com.trustly.api.domain.base.IRequest;
import com.trustly.api.domain.base.IRequestParams;
import com.trustly.api.domain.base.IRequestParamsData;
import com.trustly.api.domain.base.IResponseResultData;
import com.trustly.api.domain.base.JsonRpcRequest;
import com.trustly.api.domain.base.JsonRpcResponse;
import com.trustly.api.domain.exceptions.TrustlySignatureException;
import com.trustly.api.util.TrustlyStringUtils;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class DefaultJsonRpcSigner implements JsonRpcSigner {

  public static final String SHA1_WITH_RSA = "SHA1withRSA";

  private final Serializer serializer;
  private final TrustlyApiClientSettings settings;

  public DefaultJsonRpcSigner(Serializer serializer, TrustlyApiClientSettings settings) {
    this.serializer = serializer;
    this.settings = settings;
  }

  public String createPlaintext(String serializedData, String method, String uuid) {
    return String.format("%s%s%s", method, uuid, serializedData);
  }

  @Override
  public <T extends IRequestParamsData> JsonRpcRequest<T> sign(JsonRpcRequest<T> request) {

    String signature = this.createSignature(request.getMethod(), request.getParams().getUuid(), request.getParams().getData());

    return request.toBuilder()
      .params(
        request.getParams().withSignature(signature)
      )
      .build();
  }

  @Override
  public <T extends IResponseResultData> JsonRpcResponse<T> sign(JsonRpcResponse<T> response) {

    String signature = this.createSignature(response.getMethod(), response.getUUID(), response.getData());

    return response.toBuilder()
      .result(
        response.getResult().toBuilder()
          .signature(signature)
          .build()
      )
      .build();
  }

  private <T extends IData> String createSignature(String method, String uuid, T data) {
    String serializedData = this.serializer.serializeData(data);
    String plainText = this.createPlaintext(serializedData, method, uuid);

    Signature signer;
    try {
      signer = Signature.getInstance(SHA1_WITH_RSA, BouncyCastleProvider.PROVIDER_NAME);
    } catch (NoSuchAlgorithmException ex) {
      throw new IllegalArgumentException("Could not find signing algorithm. Has BouncyCastle not been initialized?", ex);
    } catch (NoSuchProviderException ex) {
      throw new IllegalArgumentException("Could not find provider. Has BouncyCastle not been initialized?", ex);
    }

    try {
      signer.initSign(this.settings.getClientPrivateKey());
    } catch (InvalidKeyException e) {
      throw new IllegalArgumentException("Could not sign using given client private key", e);
    }

    byte[] plainBytes = plainText.getBytes(StandardCharsets.UTF_8);
    byte[] signedBytes;

    try {
      signer.update(plainBytes);
      signedBytes = signer.sign();
    } catch (SignatureException e) {
      throw new IllegalArgumentException(String.format("Could not create signature for method %s", method), e);
    }

    return Base64.getEncoder().encodeToString(signedBytes);
  }

  @Override
  public <D extends IRequestParamsData, P extends IRequestParams<D>> void verify(IRequest<P> request) throws TrustlySignatureException {

    String uuid = (request.getParams() == null) ? null : request.getParams().getUuid();
    String signature = (request.getParams() == null) ? null : request.getParams().getSignature();
    D data = (request.getParams() == null) ? null : request.getParams().getData();

    this.verify(request.getMethod(), uuid, signature, data, null);
  }

  @Override
  public <T extends IResponseResultData> void verify(JsonRpcResponse<T> response, JsonNode nodeResponse) throws TrustlySignatureException {

    JsonNode dataNode = null;
    if (nodeResponse != null) {
      dataNode = nodeResponse.at("/result/data");
      if (dataNode.isMissingNode()) {
        dataNode = nodeResponse.at("/error/data");
      }
    }

    this.verify(response.getMethod(), response.getUUID(), response.getSignature(), response.getData(), dataNode);
  }

  private void verify(String method, String uuid, String expectedSignature, IData data, JsonNode dataNode)
    throws TrustlySignatureException {

    if (TrustlyStringUtils.isBlank(expectedSignature)) {
      throw new IllegalArgumentException("There was no expected signature given. The payload seems malformed");
    }

    // If possible, we will serialize based on the actual data node instead of the data object.
    // This way we can differentiate between a field that has as null value and was not given at all.
    // This can happen with values given back from the Trustly remote server.
    String serializedResponseData = (dataNode != null && !dataNode.isMissingNode() && !dataNode.isNull())
      ? this.serializer.serializeNode(dataNode)
      : this.serializer.serializeData(data);

    String responsePlainText = this.createPlaintext(serializedResponseData, method, uuid);

    byte[] responseBytes = responsePlainText.getBytes(StandardCharsets.UTF_8);
    byte[] expectedSignatureBytes = Base64.getDecoder().decode(expectedSignature);

    try {

      if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
        Security.addProvider(new BouncyCastleProvider());
      }

      Signature signer = Signature.getInstance(SHA1_WITH_RSA, BouncyCastleProvider.PROVIDER_NAME);
      signer.initVerify(this.settings.getTrustlyPublicKey());
      signer.update(responseBytes);

      if (!signer.verify(expectedSignatureBytes)) {
        throw new TrustlySignatureException(
          String.format("Could not verify signature '%s' of message '%s' with method '%s'", expectedSignature, uuid, method));
      }
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalArgumentException("Could not find the algorithm, has BouncyCastle not been initialized?", e);
    } catch (NoSuchProviderException e) {
      throw new IllegalArgumentException("Could not find the security provider, has BouncyCastle not been initialized?", e);
    } catch (SignatureException e) {
      throw new IllegalArgumentException("Could not update the signature with the given response bytes", e);
    } catch (InvalidKeyException e) {
      throw new IllegalArgumentException("Could not verify the data with the given Trustly public key", e);
    }
  }
}
