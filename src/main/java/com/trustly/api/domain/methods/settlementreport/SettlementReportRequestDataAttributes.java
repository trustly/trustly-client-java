package com.trustly.api.domain.methods.settlementreport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.trustly.api.domain.base.AbstractRequestParamsDataAttributes;
import jakarta.validation.constraints.NotBlank;
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
public class SettlementReportRequestDataAttributes extends AbstractRequestParamsDataAttributes {

  /**
   * Required. The APIVersion.
   *
   * <p>Must be "1.2". We also have older versions of the report, but those should not be implemented by new merchants.</p>
   *
   * <pre>{@code 1.2}</pre>
   */
  @JsonProperty(value = "APIVersion", required = true)
  @NotBlank
  @Pattern(regexp = "1\\.2")
  String apiVersion;
}
