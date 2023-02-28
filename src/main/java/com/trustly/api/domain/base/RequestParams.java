package com.trustly.api.domain.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.With;
import lombok.experimental.NonFinal;

@Value
@NonFinal
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class RequestParams<D extends IRequestParamsData> implements IRequestParams<D> {

  @JsonProperty("Signature")
  @With
  String signature;

  @JsonProperty("UUID")
  String uuid;

  @JsonProperty("Data")
  @Valid
  D data;
}
