package com.trustly.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.trustly.api.exceptions.TrustlySignatureException;

public interface JsonRpcSigner {

  String sign(Object requestData, String method, String uuid);

  void verify(String method, String uuid, JsonNode dataNode, String expectedSignature) throws TrustlySignatureException;
}
