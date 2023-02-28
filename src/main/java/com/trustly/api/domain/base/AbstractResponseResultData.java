package com.trustly.api.domain.base;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Singular;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.experimental.SuperBuilder;

@Value
@SuperBuilder(toBuilder = true)
@NonFinal
@AllArgsConstructor
public abstract class AbstractResponseResultData implements IResponseResultData {

  @JsonAnySetter
  @Singular("any")
  Map<String, Object> any;

  @JsonAnyGetter
  public Map<String, Object> getAny() {
    return this.any;
  }

  protected AbstractResponseResultData() {
    this.any = new HashMap<>();
  }
}
