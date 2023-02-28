package com.trustly.api.domain.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@RequiredArgsConstructor
@AllArgsConstructor
public abstract class AbstractToTrustlyRequestData<A extends AbstractRequestParamsDataAttributes>
  implements IToTrustlyRequestParams {

  /**
   * You do not have to set this property. It is set automatically by the API Client.
   */
  @JsonProperty(value = "Username", required = true)
  @NotBlank
  String username;

  /**
   * You do not have to set this property. It is set automatically by the API Client.
   */
  @JsonProperty(value = "Password", required = true)
  @NotBlank
  String password;

  @JsonProperty(value = "Attributes", required = true)
  @JsonInclude(Include.NON_NULL)
  @Valid
  A attributes;
}
