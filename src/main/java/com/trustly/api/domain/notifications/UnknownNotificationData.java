package com.trustly.api.domain.notifications;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.trustly.api.domain.base.AbstractFromTrustlyRequestData;
import com.trustly.api.domain.base.EmptyRequestDataAttributes;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Singular;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Value
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Jacksonized
public class UnknownNotificationData extends AbstractFromTrustlyRequestData<EmptyRequestDataAttributes> {

  @JsonAnySetter
  @Singular("any")
  Map<String, Object> any;

  @JsonAnyGetter
  public Map<String, Object> getAny() {
    return this.any;
  }

  private UnknownNotificationData() {
    this.any = new HashMap<>();
  }
}
