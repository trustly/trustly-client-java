package com.trustly.api.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TrustlyFileUtils {

  private TrustlyFileUtils() {
  }

  public static String readAllText(String path) throws IOException {
    return Files.readString(Paths.get(path), StandardCharsets.UTF_8);
  }
}
