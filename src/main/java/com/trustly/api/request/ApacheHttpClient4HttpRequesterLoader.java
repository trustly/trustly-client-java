package com.trustly.api.request;

public class ApacheHttpClient4HttpRequesterLoader implements HttpRequesterLoader {

  @Override
  public HttpRequester create() {

    try {
      Class.forName("org.apache.http.HttpEntity");
      Class.forName("org.apache.http.client.HttpClient");
      return new ApacheHttpClient4HttpRequester();
    } catch (ClassNotFoundException e) {
      return null;
    }
  }
}
