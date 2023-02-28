package com.trustly.api.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class TrustlyStreamUtils {

  public static String readerToString(Reader reader) throws IOException {

    BufferedReader in = new BufferedReader(reader);
    String inputLine;
    StringBuilder sb = new StringBuilder();
    while ((inputLine = in.readLine()) != null) {
      sb.append(inputLine);
    }
    in.close();

    return sb.toString();
  }
}
