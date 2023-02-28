package com.trustly.api.domain.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.experimental.SuperBuilder;

@Value
@SuperBuilder(toBuilder = true)
@NonFinal
@AllArgsConstructor
public class AbstractFromTrustlyRequestData<A extends AbstractRequestParamsDataAttributes>
  implements IFromTrustlyRequestData {

  public AbstractFromTrustlyRequestData() {
    this.attributes = null;
  }

  @JsonProperty(value = "attributes")
  @JsonInclude(Include.NON_NULL)
  @Valid
  A attributes;
}
