package com.trustly.api.domain.base;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Value
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@Jacksonized
public class ResponseError extends ResponseErrorData {

  String name;

  ResponseResult<ResponseErrorData> error;

  int code;

  String message;
}
