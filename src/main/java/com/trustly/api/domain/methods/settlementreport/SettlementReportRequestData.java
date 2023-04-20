package com.trustly.api.domain.methods.settlementreport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.trustly.api.domain.base.AbstractToTrustlyRequestData;
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
public class SettlementReportRequestData extends AbstractToTrustlyRequestData<SettlementReportRequestDataAttributes> {

  /**
   * If the value is specified (i.e. not "null"), the system will only search for a settlement executed in that particular currency. If
   * unspecified, settlements executed in any currency are included in the report.
   */
  @JsonProperty(value = "Currency")
  String currency;

  /**
   * The date when the settlement was processed.
   *
   * <pre>{@code 2014-04-01}</pre>
   */
  @JsonProperty(value = "SettlementDate")
  String settlementDate;
}


