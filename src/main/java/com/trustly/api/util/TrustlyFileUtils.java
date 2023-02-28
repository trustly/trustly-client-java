package com.trustly.api.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TrustlyFileUtils {

  public static String readAllText(String path) throws IOException {
    return new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
  }
}
