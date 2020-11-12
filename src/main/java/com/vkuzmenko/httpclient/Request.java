package com.vkuzmenko.httpclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;

public class Request {

  private String url;
  private Method method;
  private String body;
  private Map<String, String> headers = new HashMap<>();

  private Request() {
  }

  public static Builder builder() {
    return new Request().new Builder();
  }

  public class Builder {

    private Builder() {
    }

    public Builder url(String url) {
      Request.this.url = url;
      return this;
    }

    public Builder method(Method method) {
      Request.this.method = method;
      return this;
    }

    public Builder body(String body) {
      Request.this.body = body;
      return this;
    }

    public <T> Builder body(T body) {
      Request.this.body = parseObjectToJson(body);
      return this;
    }

    public Builder headers(Map<String, String> headers) {
      Request.this.headers = headers;
      return this;
    }

    public Request build() {
      return Request.this;
    }

    private <T> String parseObjectToJson(T body) {
      try {
        return new ObjectMapper().writeValueAsString(body);
      } catch (JsonProcessingException e) {
        // TODO handle logging
        e.printStackTrace();
      }
      return Constants.EMPTY;
    }
  }

  public String getUrl() {
    return url;
  }

  public Method getMethod() {
    return method;
  }

  public String getBody() {
    return body;
  }

  public Map<String, String> getHeader() {
    return headers;
  }
}
