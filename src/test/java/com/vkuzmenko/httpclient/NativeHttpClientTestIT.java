package com.vkuzmenko.httpclient;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class NativeHttpClientTestIT {

  private HttpClient httpClient;

  @Before
  public void setUp() {
    httpClient = new NativeHttpClient();
  }

  @Test
  public void sendRequestAsString() throws JsonProcessingException {
    Map<String, String> headers = new HashMap<>();
    headers.put("Content-type", "application/json; charset=UTF-8");

    Post post = new Post();
    post.setUserId(1);
    post.setTitle("title test");
    post.setBody("body test");

    ObjectMapper mapper = new ObjectMapper();
    String objectAsString = mapper.writeValueAsString(post);

    Request request = Request.builder()
        .url("https://jsonplaceholder.typicode.com/posts")
        .method(Method.POST)
        .body(objectAsString)
        .headers(headers)
        .build();

    Executor executor = httpClient.create(request);
    Response response = executor.execute();
    JsonNode json = response.body().json();

    assertThat(response.statusCode()).isEqualTo(201);
    assertThat(response.headers()).containsEntry("Content-Type", "application/json; charset=utf-8");
    assertThat(json.get("id").asText()).isEqualTo("101");
    assertThat(json.get("title").asText()).isEqualTo("title test");
    assertThat(json.get("body").asText()).isEqualTo("body test");
    assertThat(json.get("userId").asText()).isEqualTo("1");
  }

  @Test
  public void sendRequestAsObject() {
    Map<String, String> headers = new HashMap<>();
    headers.put("Content-type", "application/json; charset=UTF-8");

    Post post = new Post();
    post.setUserId(1);
    post.setTitle("title test");
    post.setBody("body test");

    Request request = Request.builder()
        .url("https://jsonplaceholder.typicode.com/posts")
        .method(Method.POST)
        .body(post)
        .headers(headers)
        .build();

    Executor executor = httpClient.create(request);
    Response response = executor.execute();
    JsonNode json = response.body().json();

    assertThat(response.statusCode()).isEqualTo(201);
    assertThat(response.headers()).containsEntry("Content-Type", "application/json; charset=utf-8");
    assertThat(json.get("id").asText()).isEqualTo("101");
    assertThat(json.get("title").asText()).isEqualTo("title test");
    assertThat(json.get("body").asText()).isEqualTo("body test");
    assertThat(json.get("userId").asText()).isEqualTo("1");
  }


  @Test
  public void putRequest() throws JsonProcessingException {
    Map<String, String> headers = new HashMap<>();
    headers.put("Content-type", "application/json; charset=UTF-8");

    Post post = new Post();
    post.setId(200);
    post.setUserId(1);
    post.setTitle("title test");
    post.setBody("body test");

    ObjectMapper mapper = new ObjectMapper();
    String objectAsString = mapper.writeValueAsString(post);

    Request request = Request.builder()
        .url("https://jsonplaceholder.typicode.com/posts/1")
        .method(Method.PUT)
        .body(objectAsString)
        .headers(headers)
        .build();

    Executor executor = httpClient.create(request);
    Response response = executor.execute();
    JsonNode json = response.body().json();

    assertThat(response.statusCode()).isEqualTo(200);
    assertThat(response.headers()).containsEntry("Content-Type", "application/json; charset=utf-8");
    assertThat(json.get("id").asText()).isEqualTo("1");
    assertThat(json.get("title").asText()).isEqualTo("title test");
    assertThat(json.get("body").asText()).isEqualTo("body test");
    assertThat(json.get("userId").asText()).isEqualTo("1");
  }

  @Test
  public void getResponse() throws JsonProcessingException {
    Request request = Request.builder()
        .url("https://jsonplaceholder.typicode.com/posts")
        .method(Method.GET)
        .build();

    Executor executor = httpClient.create(request);
    Response response = executor.execute();

    ObjectMapper mapper = new ObjectMapper();
    List<Post> posts = Arrays.asList(mapper.readValue(response.body().string(), Post[].class));

    assertThat(response.statusCode()).isEqualTo(200);
    assertThat(response.headers()).containsEntry("Content-Type", "application/json; charset=utf-8");
    assertThat(posts.size()).isEqualTo(100);
    assertThat(posts.get(0).getId()).isEqualTo(1);
    assertThat(posts.get(0).getTitle()).contains("sunt aut facere repellat provident occaecati excepturi optio reprehenderit");
    assertThat(posts.get(0).getBody()).contains("quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto");

    assertThat(posts.get(1).getId()).isEqualTo(2);
    assertThat(posts.get(1).getTitle()).contains("qui est esse");
    assertThat(posts.get(1).getBody()).contains("est rerum tempore vitae\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\nqui aperiam non debitis possimus qui neque nisi nulla");

    assertThat(posts.get(2).getId()).isEqualTo(3);
    assertThat(posts.get(2).getTitle()).contains("ea molestias quasi exercitationem repellat qui ipsa sit aut");
    assertThat(posts.get(2).getBody()).contains("et iusto sed quo iure\nvoluptatem occaecati omnis eligendi aut ad\nvoluptatem doloribus vel accusantium quis pariatur\nmolestiae porro eius odio et labore et velit aut");
  }
}
