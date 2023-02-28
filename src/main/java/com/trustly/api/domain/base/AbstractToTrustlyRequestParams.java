package com.trustly.api.domain.base;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@RequiredArgsConstructor
@AllArgsConstructor
public abstract class AbstractToTrustlyRequestParams<D extends IData> implements IRequestParams<D> {

  @Valid
  D data;
}
