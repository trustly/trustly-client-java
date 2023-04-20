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
public class NotificationRequest<D extends IFromTrustlyRequestData> implements IRequest<NotificationRequestParams<D>> {

  @JsonProperty("method")
  String method;

  @JsonProperty("params")
  @Valid
  NotificationRequestParams<D> params;

  @Default
  @JsonProperty("version")
  double version = 1.1;
}
