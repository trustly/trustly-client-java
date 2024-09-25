package com.trustly.api.util;

public class TrustlyStringUtils {

  private TrustlyStringUtils() {
  }

  public static boolean isBlank(String value) {

    if (value == null) {
      return true;
    }

    if (value.isEmpty()) {
      return true;
    }

    return value.trim().isEmpty();
  }
}
