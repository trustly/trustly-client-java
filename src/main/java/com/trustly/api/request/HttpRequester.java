package com.trustly.api.request;

import com.trustly.api.client.TrustlyApiClientSettings;
import java.io.IOException;

public interface HttpRequester {

  String request(TrustlyApiClientSettings settings, String request) throws IOException;
}
