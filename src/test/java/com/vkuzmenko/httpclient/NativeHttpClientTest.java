package com.vkuzmenko.httpclient;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class NativeHttpClientTest {

  private HttpClient httpClient;

  @Mock
  private Executor executor;

  @Before
  public void setUp() {
    httpClient = new NativeHttpClient();
  }

/*  @Test
  public void checkIfResponseHeadersSetCorrectly() {
    Map<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "text/json");
    headers.put("Accept-Type", "text/json");

    Request request = Request.builder()
        .url("http://open-api.com/users/")
        .method(Method.GET)
        .headers(headers)
        .build();

    when(executor.execute())
        .thenReturn();

    Platform.get().con

    Executor executor = httpClient.create(request);
    executor.execute();
  }*/


/*
  @Test
  public void checkIfResponseHeadersSetCorrectly() {
    Response response = new Response();
    response.setMessage("test response");
    response.setStatusCode(200);
    response.setHeader();

    Map<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "text/json");
    headers.put("Accept-Type", "text/json");

    Request request = Request.builder()
        .url("http://open-api.com/users/")
        .method(Method.GET)
        .headers(headers)
        .build();

    when(executor.execute())
        .thenReturn();

    Executor executor = httpClient.create(request);
    Response response = executor.execute();

*/

  @Test
  public void getResponse() {
    Map<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "text/json");
    headers.put("Accept", "text/json");

    Request request = Request.builder()
        .url("https://jsonplaceholder.typicode.com/posts")
        .method(Method.GET)
        .headers(headers)
        .build();

    Executor executor = httpClient.create(request);
    Response response = executor.execute();

    ResponseBody responseBody = response.body();

    assertThat(response.body().string()).isEqualTo("response text");
    assertThat(responseBody).isEqualTo("test response body");
  }
}
