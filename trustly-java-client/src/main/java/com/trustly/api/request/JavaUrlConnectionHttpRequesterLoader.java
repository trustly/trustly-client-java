package com.trustly.api.request;

public class JavaUrlConnectionHttpRequesterLoader implements HttpRequesterLoader {

  @Override
  public HttpRequester create() {
    return new JavaUrlConnectionHttpRequester();
  }
}
