package com.trustly.api.domain.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public abstract class AbstractAmountConstrainedAccountDataAttributes extends AbstractAccountDataAttributes {

  /**
   * The minimum amount the end-user is allowed to deposit in the currency specified by Currency.Only digits. Use dot (.) as decimal
   * separator.
   */
  @JsonProperty(value = "SuggestedMinAmount")
  String suggestedMinAmount;

  /**
   * The maximum amount the end-user is allowed to deposit in the currency specified by Currency.Only digits. Use dot (.) as decimal
   * separator.
   */
  @JsonProperty(value = "SuggestedMaxAmount")
  String suggestedMaxAmount;
}
