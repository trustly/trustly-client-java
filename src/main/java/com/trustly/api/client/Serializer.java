package com.trustly.api.client;

import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ValueNode;
import com.trustly.api.domain.base.IData;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class Serializer {

  private final ObjectMapper objectMapper = new ObjectMapper();

  public <D extends IData> String serializeData(D data) {

    JsonNode jsonObject = this.objectMapper.valueToTree(data);

    StringBuilder sb = new StringBuilder();
    this.serializeNode(jsonObject, sb);

    return sb.toString();
  }

  public String serializeNode(TreeNode node) {

    StringBuilder sb = new StringBuilder();
    this.serializeNode(node, sb);

    return sb.toString();
  }

  private void serializeNode(TreeNode node, StringBuilder sb) {
    if (node instanceof ObjectNode) {
      serializeObjectNode((ObjectNode) node, sb);
    } else if (node instanceof ValueNode) {
      serializeValueNode((ValueNode) node, sb);
    } else {
      serializeOtherNode(node, sb);
    }
  }

  private void serializeObjectNode(ObjectNode objectNode, StringBuilder sb) {
    List<String> fieldNames = this.iteratorToList(objectNode.fieldNames());
    fieldNames.sort(Comparator.naturalOrder());

    for (String fieldName : fieldNames) {

      StringBuilder propertyBuffer = new StringBuilder();
      this.serializeNode(objectNode.get(fieldName), propertyBuffer);

      sb.append(fieldName);
      sb.append(propertyBuffer);
    }
  }

  private static void serializeValueNode(ValueNode valueNode, StringBuilder sb) {
    if (valueNode.isNull()) {
      // If null, then we do not append anything. It becomes an empty string.
    } else {
      sb.append(valueNode.asText());
    }
  }

  private void serializeOtherNode(TreeNode treeNode, StringBuilder sb) {
    if (treeNode.isArray()) {
      for (int i = 0; i < treeNode.size(); i++) {
        this.serializeNode(treeNode.get(i), sb);
      }
    } else {
      for (String fieldName : iteratorToList(treeNode.fieldNames())) {
        this.serializeNode(treeNode.get(fieldName), sb);
      }
    }
  }

  private <T> List<T> iteratorToList(Iterator<T> it) {

    List<T> list = new ArrayList<>();
    while (it.hasNext()) {
      list.add(it.next());
    }

    return list;
  }
}
