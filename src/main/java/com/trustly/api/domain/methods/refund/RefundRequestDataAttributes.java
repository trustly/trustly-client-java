package com.trustly.api.domain.methods.refund;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.trustly.api.domain.base.AbstractRequestParamsDataAttributes;
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
public class RefundRequestDataAttributes extends AbstractRequestParamsDataAttributes {

  /**
   * This is a reference set by the merchant for any purpose and does not need to be unique for every API call.
   * <p>
   * This will be included in version {@code 1.2} of the settlement report, {@code ViewAutomaticSettlementDetailsCSV}.
   */
  @JsonProperty(value = "ExternalReference")
  String externalReference;
}
