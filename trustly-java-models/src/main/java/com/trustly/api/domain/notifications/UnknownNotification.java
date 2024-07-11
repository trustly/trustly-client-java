package com.trustly.api.domain.notifications;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.Map;

import static com.trustly.api.domain.Models.*;

@Getter
@Jacksonized
@Setter
@SuperBuilder
public class UnknownNotification extends JsonRpcNotification<UnknownNotification.Params> {
  public UnknownNotification(String method) {
    super(method);
  }

  @Getter
  @Jacksonized
  @Setter
  @SuperBuilder
  public static class Params extends JsonRpcNotificationParams<Params.Data> {

    @Getter
    @Jacksonized
    @Setter
    @SuperBuilder
    public static class Data extends AbstractNotificationRequestData {

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
  }
}
