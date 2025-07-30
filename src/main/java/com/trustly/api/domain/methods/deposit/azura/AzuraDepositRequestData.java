package com.trustly.api.domain.methods.deposit.azura;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.trustly.api.domain.base.AbstractToTrustlyRequestData;
import jakarta.validation.Valid;
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
public class AzuraDepositRequestData extends AbstractToTrustlyRequestData<AzuraDepositRequestDataAttributes> {

  @JsonProperty(value = "MessageID", required = true)
  @NotBlank
  private String messageId;

  @JsonProperty(value = "Attributes", required = true)
  @JsonInclude(Include.NON_NULL)
  @Valid
  @Override
  public AzuraDepositRequestDataAttributes getAttributes() {
    return super.getAttributes();
  }
}
