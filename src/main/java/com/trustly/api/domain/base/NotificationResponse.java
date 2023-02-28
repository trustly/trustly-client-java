package com.trustly.api.domain.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Value
@EqualsAndHashCode(callSuper = true)
@NonFinal
@AllArgsConstructor
public class NotificationResponse extends AbstractResponseResultData {

  /**
   * Valid values are:
   *
   * <ul>
   *   <li>OK - The notification has been received</li>
   * </ul>
   */
  @JsonProperty("status")
  String status;
}
