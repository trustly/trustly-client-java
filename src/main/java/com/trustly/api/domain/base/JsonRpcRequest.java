package com.trustly.api.domain.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder(toBuilder = true)
@RequiredArgsConstructor
@Jacksonized
public class JsonRpcRequest<D extends IRequestParamsData> {

  @JsonProperty("method")
  String method;

  @JsonProperty("params")
  @Valid
  IRequestParams<D> params;

  @Default
  @JsonProperty("version")
  double version = 1.1;
}
