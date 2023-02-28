package com.trustly.api.domain.common;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;

public class StringBooleanDeserializer extends StdDeserializer<Boolean> {

  protected StringBooleanDeserializer() {
    super(Boolean.class);
  }

  @Override
  public Boolean deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
    String valueString = p.getValueAsString("");
    if ("1".equals(valueString)) {
      return true;
    }

    return false;
  }
}
