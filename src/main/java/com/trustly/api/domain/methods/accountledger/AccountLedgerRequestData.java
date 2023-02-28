package com.trustly.api.domain.methods.accountledger;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.trustly.api.domain.base.AbstractToTrustlyRequestData;
import com.trustly.api.domain.base.EmptyRequestDataAttributes;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
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
public class AccountLedgerRequestData extends AbstractToTrustlyRequestData<EmptyRequestDataAttributes> {

  @JsonProperty(value = "FromDate", required = true)
  @NotBlank
  String fromDate;

  @JsonProperty(value = "ToDate", required = true)
  @NotBlank
  String toDate;

  @JsonProperty(value = "Currency", required = true)
  @Pattern(regexp = "[A-Z]{2,3}")
  String currency;
}

    

