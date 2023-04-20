package com.trustly.api.domain.methods.charge;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.trustly.api.domain.base.AbstractResponseResultData;
import com.trustly.api.domain.base.IWithRejectionResult;
import com.trustly.api.domain.common.StringBooleanDeserializer;
import com.trustly.api.domain.common.StringBooleanSerializer;
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
public class ChargeResponseData extends AbstractResponseResultData implements IWithRejectionResult {

  /**
   * 1 if the charge was accepted for processing, 0 otherwise. Note that this is an acceptance of the order, no money has been charged from
   * the account until you receive notifications thereof.
   */
  @JsonProperty("result")
  @JsonSerialize(using = StringBooleanSerializer.class)
  @JsonDeserialize(using = StringBooleanDeserializer.class)
  boolean result;

  /**
   * The globally unique OrderID the charge order was assigned in our system, or null if the charge was not accepted. The order has no
   * end-user interaction; it is merely used as a reference for the notifications delivered regarding the charge. See section
   * "Notifications" below for details.
   */
  @JsonProperty("orderid")
  String orderId;

  /**
   * If the charge was NOT accepted, a textual code describing the rejection reason, null otherwise.
   * <p>
   * The possible rejected codes are:
   * <p>
   * ERROR_MANDATE_NOT_FOUND - the AccountID does not have an active mandate ERROR_DIRECT_DEBIT_NOT_ALLOWED - Trustly Direct Debit is not
   * enabled on the merchant's account in Trustly's system. ERROR_ACCOUNT_NOT_FOUND - the specified AccountID does not exist.
   */
  @JsonProperty("rejected")
  String rejected;
}
