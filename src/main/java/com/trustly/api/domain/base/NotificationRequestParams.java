package com.trustly.api.domain.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.With;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Value
@RequiredArgsConstructor
@SuperBuilder
@Jacksonized
public class NotificationRequestParams<D extends IFromTrustlyRequestData> implements IRequestParams<D> {

  @JsonProperty("signature")
  @With
  String signature;

  @JsonProperty("uuid")
  String uuid;

  @JsonProperty("data")
  @Valid
  D data;
}
