package com.trustly.api.domain.base;

public interface IRequestParams<D extends IData> {

  String getSignature();

  String getUuid();

  D getData();

  IRequestParams<D> withSignature(String value);
}
