package com.trustly.api.domain.base;

public interface IToTrustlyRequestParams extends IRequestParamsData {

  String getUsername();

  void setUsername(String value);

  String getPassword();

  void setPassword(String value);
}
