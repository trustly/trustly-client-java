package com.trustly.api.request;

import com.trustly.api.client.TrustlyApiClientSettings;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class ApacheHttpClient4HttpRequester implements HttpRequester {

  private CloseableHttpClient httpClient;

  @Override
  public String request(TrustlyApiClientSettings settings, String request) throws IOException {

    if (this.httpClient == null) {
      this.httpClient = HttpClients.createDefault();
    }

    StringEntity requestEntity = new StringEntity(request, ContentType.APPLICATION_JSON);

    HttpPost postMethod = new HttpPost(settings.getUrl());
    postMethod.setEntity(requestEntity);

    HttpResponse response = this.httpClient.execute(postMethod);

    HttpEntity entity = response.getEntity();
    return EntityUtils.toString(entity, StandardCharsets.UTF_8);
  }
}
