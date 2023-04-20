package com.trustly.api.domain.methods.accountledger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import com.trustly.api.domain.base.IResponseResultData;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.Value;

@Value
public class AccountLedgerResponseData implements IResponseResultData {

  @JsonValue
  List<AccountLedgerResponseDataEntry> entries;

  @JsonCreator
  public AccountLedgerResponseData(List<AccountLedgerResponseDataEntry> entries) {
    this.entries = entries;
  }

  @Override
  @JsonIgnore
  public Map<String, Object> getAny() {
    return Collections.emptyMap();
  }
}
