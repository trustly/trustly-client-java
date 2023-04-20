package com.trustly.api.domain.base;

public interface IWithRejectionResult {

  boolean isResult();

  String getRejected();
}
