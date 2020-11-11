package com.vkuzmenko.httpclient;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Before;
import org.junit.Test;

public class ResponseTest {

  private Response response;

  @Before
  public void setUp() {
    response = new Response();
  }

  @Test
  public void responseToJson() {
    String responseText = "{"
        + "\"userId\": 1,"
        + "\"id\": 50,"
        + "\"title\": \"response test title\","
        + "\"body\": \"response test text\""
        + "}";

    response.setBody(responseText);

    JsonNode json = response.body().json();

    assertThat(json.get("userId").asText()).isEqualTo("1");
    assertThat(json.get("id").asText()).isEqualTo("50");
    assertThat(json.get("title").asText()).isEqualTo("response test title");
    assertThat(json.get("body").asText()).isEqualTo("response test text");
  }

  @Test
  public void responseFromJson() {
    String json = "{"
        + "\"userId\": 1,"
        + "\"id\": 1,"
        + "\"title\": \"sunt aut facere repellat provident occaecati excepturi optio reprehenderit\",\n"
        + "\"body\": \"quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto\"\n"
        + "}";

  }
}