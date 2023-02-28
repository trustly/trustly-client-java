package com.trustly.api.request;

import com.trustly.api.client.TrustlyApiClientSettings;
import java.io.IOException;

public interface HttpRequester {

  default boolean isAvailable() {
    return true;
  }

  String request(TrustlyApiClientSettings settings, String request) throws IOException;
}
