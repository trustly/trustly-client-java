package com.trustly.api.domain.base;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.experimental.SuperBuilder;

@Value
@NonFinal
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
public class ResponseErrorData extends AbstractResponseResultData {

  int code;

  String message;

  ResponseErrorData() {
    this.code = -1;
    this.message = null;
  }
}
