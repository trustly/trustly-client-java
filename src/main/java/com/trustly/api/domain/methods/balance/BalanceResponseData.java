package com.trustly.api.domain.methods.balance;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import com.trustly.api.domain.base.IResponseResultData;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.Value;

@Value
public class BalanceResponseData implements IResponseResultData {

  @JsonValue
  List<BalanceResponseDataEntry> entries;

  @JsonCreator
  public BalanceResponseData(List<BalanceResponseDataEntry> entries) {
    this.entries = entries;
  }

  @Override
  @JsonIgnore
  public Map<String, Object> getAny() {
    return Collections.emptyMap();
  }
}
