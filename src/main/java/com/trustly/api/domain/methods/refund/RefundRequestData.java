package com.trustly.api.domain.methods.refund;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.trustly.api.domain.base.AbstractToTrustlyRequestData;
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
public class RefundRequestData extends AbstractToTrustlyRequestData<RefundRequestDataAttributes> {

  /**
   * The OrderID of the initial deposit.
   */
  @JsonProperty(value = "OrderID", required = true)
  @NotBlank
  String orderId;

  /**
   * The amount to refund the customer with exactly two decimals. Only digits. Use dot (.) as decimal separator.
   */
  @JsonProperty(value = "Amount", required = true)
  @NotBlank
  String amount;

  /**
   * The currency of the amount to refund the customer.
   */
  @JsonProperty(value = "Currency", required = true)
  @NotBlank
  String currency;
}

