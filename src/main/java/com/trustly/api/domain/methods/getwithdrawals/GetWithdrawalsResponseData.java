package com.trustly.api.domain.methods.getwithdrawals;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.trustly.api.domain.base.IResponseResultData;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.Value;

@Value
public class GetWithdrawalsResponseData implements IResponseResultData {

  @JsonValue
  List<GetWithdrawalsResponseDataEntry> entries;

  @JsonCreator
  public GetWithdrawalsResponseData(List<GetWithdrawalsResponseDataEntry> entries) {
    this.entries = entries;
  }

  @Override
  public Map<String, Object> getAny() {
    return Collections.emptyMap();
  }
}
