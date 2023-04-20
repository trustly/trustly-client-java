package com.trustly.api.request;

import com.trustly.api.client.TrustlyApiClientSettings;
import java.io.IOException;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.BasicHttpClientResponseHandler;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.HttpEntities;

public class ApacheHttpClient5HttpRequester implements HttpRequester {

  private HttpClient httpClient;

  @Override
  public String request(TrustlyApiClientSettings settings, String request) throws IOException {

    if (this.httpClient == null) {
      this.httpClient = HttpClients.createDefault();
    }

    final HttpPost httpPost = new HttpPost(settings.getUrl());
    httpPost.setEntity(HttpEntities.create(request, ContentType.APPLICATION_JSON));

    return httpClient.execute(httpPost, new BasicHttpClientResponseHandler());
  }
}
