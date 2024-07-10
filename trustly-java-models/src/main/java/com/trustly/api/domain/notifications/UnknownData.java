package com.trustly.api.domain.notifications;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Singular;

import java.util.Map;

public class UnknownData {

  @Singular
  @JsonAnySetter
  private Map<String, Object> additionalProperties;
  public void addAdditionalProperty(String key, Object value) {
    this.additionalProperties.put(key, value);
  }

  @JsonAnyGetter
  public Map<String, Object> getAdditionalProperties() {
    return this.additionalProperties;
  }
}
