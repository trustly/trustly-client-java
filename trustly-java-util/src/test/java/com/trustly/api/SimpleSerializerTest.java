package com.trustly.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SimpleSerializerTest {

  @Test
  void testSerializer() {

    final var serializer = new Serializer();
    final var om = new ObjectMapper();

    final var o = om.createObjectNode()
      .put("q", "str")
      .put("a", 1.2)
      ;

    final var b = o.putObject("b")
      .put("a", 3)
      ;

    Assertions.assertEquals("a1.2ba3qstr", serializer.serializeNode(o));
  }
}
