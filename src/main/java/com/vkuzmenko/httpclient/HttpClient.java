package com.vkuzmenko.httpclient;

public interface HttpClient {

  Executor create(Request request);
}
