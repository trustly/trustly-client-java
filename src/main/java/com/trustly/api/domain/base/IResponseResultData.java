package com.trustly.api.domain.base;

import java.util.Map;

public interface IResponseResultData extends IData {

  Map<String, Object> getAny();
}
