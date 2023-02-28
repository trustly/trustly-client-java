package com.trustly.api.util;

public class TrustlyStringUtils {

  public static boolean isBlank(String value) {

    if (value == null) {
      return true;
    }

    if (value.equals("")) {
      return true;
    }

    if (value.trim().equals("")) {
      return true;
    }

    return false;
  }

  public static String trimEnd(String value, char[] toTrim) {
    int len = value.length();
    int st = 0;
    while ((st < len) && TrustlyStringUtils.indexOfCharArray(toTrim, value.charAt(len - 1)) >= 0) {
      len--;
    }
    return value.substring(0, len);
  }

  private static int indexOfCharArray(char[] array, char key) {

    for (int i = 0; i < array.length; ++i) {
      if (key == array[i]) {
        return i;
      }
    }

    return -1;
  }
}
