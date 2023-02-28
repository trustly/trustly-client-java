package com.trustly.api.domain.methods.cancelcharge;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.trustly.api.domain.base.AbstractToTrustlyRequestData;
import com.trustly.api.domain.base.EmptyRequestDataAttributes;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Jacksonized
@JsonInclude(Include.NON_NULL)
public class CancelChargeRequestData extends AbstractToTrustlyRequestData<EmptyRequestDataAttributes> {

  /**
   * The OrderID of the Charge request that should be canceled.
   */
  @JsonProperty(value = "OrderID", required = true)
  @NotBlank
  String orderId;
}


