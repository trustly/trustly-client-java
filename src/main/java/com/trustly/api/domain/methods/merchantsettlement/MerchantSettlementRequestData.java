package com.trustly.api.domain.methods.merchantsettlement;

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
public class MerchantSettlementRequestData extends AbstractToTrustlyRequestData<EmptyRequestDataAttributes> {

  /**
   * Your unique ID for the settlement transaction.
   */
  @JsonProperty(value = "MessageID", required = true)
  @NotBlank
  String messageId;

  /**
   * The amount to send with exactly two decimals. Only digits. Use dot (.) as decimal separator. If the end-user holds a balance in the
   * merchant's system then the amount must have been deducted from that balance before calling this method.
   */
  @JsonProperty(value = "Amount", required = true)
  @NotBlank
  String amount;

  /**
   * The currency of the end-user's account in the merchant's system.
   */
  @JsonProperty(value = "Currency", required = true)
  @NotBlank
  String currency;
}

