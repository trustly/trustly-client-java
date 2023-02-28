package com.trustly.api.domain.base;

public interface IRequest<P extends IRequestParams<?>> {

  String getMethod();

  double getVersion();

  P getParams();
}
