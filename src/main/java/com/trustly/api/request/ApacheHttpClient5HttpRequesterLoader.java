package com.trustly.api.request;

public class ApacheHttpClient5HttpRequesterLoader implements HttpRequesterLoader {

  @Override
  public HttpRequester create() {

    try {
      Class.forName("org.apache.hc.client5.http.impl.classic.HttpClients");
      return new ApacheHttpClient5HttpRequester();
    } catch (ClassNotFoundException e) {
      return null;
    }
  }
}
