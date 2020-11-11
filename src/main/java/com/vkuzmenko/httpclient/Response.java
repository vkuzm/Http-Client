package com.vkuzmenko.httpclient;

import java.util.Map;

public class Response {

  private int statusCode;
  private Map<String, String> headers;
  private ResponseBody body;

  public int statusCode() {
    return statusCode;
  }

  public void setStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }

  public Map<String, String> headers() {
    return headers;
  }

  public void setHeaders(Map<String, String> headers) {
    this.headers = headers;
  }

  public ResponseBody body() {
    return this.body;
  }

  public void setBody(String body) {
    this.body = new ResponseBody(body);
  }
}
