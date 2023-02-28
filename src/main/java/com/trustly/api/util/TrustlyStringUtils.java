package com.trustly.api.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TrustlyStringUtils {

  public static boolean isBlank(String value) {

    if (value == null) {
      return true;
    }

    if (value.equals("")) {
      return true;
    }

    return value.trim().equals("");
  }
}
