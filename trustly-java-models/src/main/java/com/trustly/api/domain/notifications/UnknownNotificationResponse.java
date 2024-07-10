package com.trustly.api.domain.notifications;

import com.trustly.api.domain.Models;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@RequiredArgsConstructor
@Setter
@SuperBuilder
public class UnknownNotificationResponse extends Models.JsonRpcResponse<UnknownNotificationResponse.Result> {
  @Getter
  @Jacksonized
  @RequiredArgsConstructor
  @Setter
  @SuperBuilder
  public static class Result extends Models.ResponseResult<UnknownNotificationAckData> {

  }
}
