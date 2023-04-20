package com.trustly.api.domain.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;

public class StringBooleanSerializer extends StdSerializer<Boolean> {

  protected StringBooleanSerializer() {
    super(Boolean.class);
  }

  @Override
  public void serialize(Boolean value, JsonGenerator gen, SerializerProvider provider) throws IOException {
    gen.writeString(Boolean.TRUE.equals(value) ? "1" : "0");
  }
}
