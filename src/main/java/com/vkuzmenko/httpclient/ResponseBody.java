package com.vkuzmenko.httpclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class ResponseBody {

  private final String value;

  public ResponseBody(String value) {
    this.value = value;
  }

  public String string() {
    return value;
  }

  public JsonNode json() {
    try {
      return new ObjectMapper().readTree(value);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }
}
