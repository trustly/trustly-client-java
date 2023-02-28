package com.trustly.api.domain.base;

import jakarta.validation.Valid;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder(toBuilder = true)
@RequiredArgsConstructor
@Jacksonized
public class JsonRpcResponse<D extends IResponseResultData> {

  String version;

  @Valid
  ResponseResult<D> result;

  @Valid
  ResponseError error;

  public boolean isSuccessfulResult() {
    return this.result != null && this.error == null;
  }

  public String getUUID() {
    return this.isSuccessfulResult() ? this.result.getUuid() : this.error.getError().getUuid();
  }

  public IData getData() {
    if (this.isSuccessfulResult()) {
      return this.result.getData();
    } else {
      if (this.error != null && this.error.getError() != null) {
        return this.error.getError().getData();
      }
    }

    return null;
  }

  public String getMethod() {
    return this.isSuccessfulResult() ? this.result.getMethod() : this.error.getError().getMethod();
  }

  public String getSignature() {
    return this.isSuccessfulResult() ? this.result.getSignature() : this.error.getError().getSignature();
  }
}
