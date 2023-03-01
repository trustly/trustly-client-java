package com.trustly.api.request;

import com.trustly.api.client.TrustlyApiClientSettings;
import com.trustly.api.util.TrustlyStreamUtils;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class JavaUrlConnectionHttpRequester implements HttpRequester {

  @Override
  public String request(TrustlyApiClientSettings settings, String request) throws IOException {

    byte[] requestBytes = request.getBytes(StandardCharsets.UTF_8);

    URL url = new URL(settings.getUrl());
    HttpURLConnection con = (HttpURLConnection) url.openConnection();

    con.setRequestMethod("POST");
    con.setRequestProperty("Content-Type", "application/json");
    con.setRequestProperty("Content-Length", String.valueOf(requestBytes.length));
    con.setRequestProperty("Accept", "application/json");
    con.setDoOutput(true);

    try (OutputStream os = con.getOutputStream()) {
      os.write(requestBytes, 0, requestBytes.length);
    }

    int status = con.getResponseCode();

    Reader sr;
    if (status > 299) {
      sr = new InputStreamReader(con.getErrorStream(), StandardCharsets.UTF_8);
    } else {
      sr = new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8);
    }

    return TrustlyStreamUtils.readerToString(sr);
  }
}
