package com.trustly.api.request;

import com.trustly.api.client.TrustlyApiClientSettings;
import com.trustly.api.util.TrustlyStreamUtils;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

public class ApacheHttpClient3HttpRequester implements HttpRequester {

  private HttpClient httpClient;

  @Override
  public String request(TrustlyApiClientSettings settings, String request) throws IOException {

    if (this.httpClient == null) {
      this.httpClient = new HttpClient();
    }

    StringRequestEntity requestEntity = new StringRequestEntity(request, "application/json", "UTF-8");

    PostMethod postMethod = new PostMethod(settings.getUrl());
    postMethod.setRequestEntity(requestEntity);

    int statusCode = httpClient.executeMethod(postMethod);

    String charset = postMethod.getResponseCharSet();
    if (charset == null) {
      charset = StandardCharsets.UTF_8.name();
    }

    Reader sr = new InputStreamReader(postMethod.getResponseBodyAsStream(), charset);
    String responseBody = TrustlyStreamUtils.readerToString(sr);

    if (statusCode > 299) {
      throw new IOException(String.format("Received error response %d: %s", statusCode, responseBody));
    }

    return responseBody;
  }
}
