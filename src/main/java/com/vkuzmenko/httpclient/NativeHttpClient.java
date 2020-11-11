package com.vkuzmenko.httpclient;

public class NativeHttpClient implements HttpClient {

  @Override
  public Executor create(Request request) {
    return new RealExecutor(request);
  }
}
