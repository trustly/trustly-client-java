package com.trustly.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.trustly.api.exceptions.TrustlySignatureException;
import com.trustly.api.util.TrustlyStringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Base64;

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
  public String sign(Object requestData, String method, String uuid) {

    String serializedData = this.serializer.serializeData(requestData);
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

  public void verify(String method, String uuid, JsonNode dataNode, String expectedSignature)
    throws TrustlySignatureException {

    if (TrustlyStringUtils.isBlank(expectedSignature)) {
      throw new IllegalArgumentException("There was no expected signature given. The payload seems malformed");
    }

    // If possible, we will serialize based on the actual data node instead of the data object.
    // This way we can differentiate between a field that has as null value and was not given at all.
    // This can happen with values given back from the Trustly remote server.
    var serializedResponseData = this.serializer.serializeNode(dataNode);

    var responsePlainText = this.createPlaintext(serializedResponseData, method, uuid);

    var responseBytes = responsePlainText.getBytes(StandardCharsets.UTF_8);
    var expectedSignatureBytes = Base64.getDecoder().decode(expectedSignature);

    try {

      if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
        Security.addProvider(new BouncyCastleProvider());
      }

      var signer = Signature.getInstance(SHA1_WITH_RSA, BouncyCastleProvider.PROVIDER_NAME);
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
