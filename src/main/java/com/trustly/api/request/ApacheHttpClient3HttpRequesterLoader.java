package com.trustly.api.request;

public class ApacheHttpClient3HttpRequesterLoader implements HttpRequesterLoader {

  @Override
  public HttpRequester create() {

    try {
      Class.forName("org.apache.commons.httpclient.HttpClient");
      return new ApacheHttpClient3HttpRequester();
    } catch (ClassNotFoundException e) {
      return null;
    }
  }
}
