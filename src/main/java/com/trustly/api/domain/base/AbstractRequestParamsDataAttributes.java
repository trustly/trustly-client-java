package com.trustly.api.domain.base;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.Singular;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
@RequiredArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public abstract class AbstractRequestParamsDataAttributes {

  @JsonAnySetter
  @Singular("any")
  private Map<String, Object> any;

  @JsonAnyGetter
  public Map<String, Object> getAny() {
    return this.any;
  }
}
