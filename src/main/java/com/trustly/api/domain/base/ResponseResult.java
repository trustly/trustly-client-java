package com.trustly.api.domain.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor
@Jacksonized
public class ResponseResult<D extends IResponseResultData> {

  @JsonProperty(value = "signature")
  String signature;

  @JsonProperty(value = "uuid")
  String uuid;

  @JsonProperty(value = "method")
  String method;

  @JsonProperty(value = "data")
  @Valid
  D data;
}
