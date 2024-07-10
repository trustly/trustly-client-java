package com.trustly.api;

import com.fasterxml.jackson.databind.JsonNode;

class NoOpJsonRpcSigner implements JsonRpcSigner {

  @Override
  public String sign(Object requestData, String method, String uuid) {
    return String.format("<%s>", NoOpJsonRpcSigner.class.getName());
  }

  @Override
  public void verify(String method, String uuid, JsonNode dataNode, String expectedSignature) {

  }
}
