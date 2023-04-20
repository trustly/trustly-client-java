package com.trustly.api.domain.methods.settlementreport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.trustly.api.domain.base.AbstractResponseResultData;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Value
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Jacksonized
public class SettlementReportResponseData extends AbstractResponseResultData {

  String csvContent;

  @JsonIgnore
  List<SettlementReportResponseDataEntry> entries;

  @JsonCreator
  public SettlementReportResponseData(@JsonProperty("view_automatic_settlement_details") String csvContent) {
    this.csvContent = csvContent;
    this.entries = new SettlementReportParser().parse(csvContent);
  }
}
