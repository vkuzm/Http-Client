package com.vkuzmenko.httpclient;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;

import com.vkuzmenko.httpclient.exceptions.UrlNotFoundException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Map.Entry;

public class RealExecutor implements Executor {

  private final Request request;

  public RealExecutor(Request request) {
    this.request = request;
  }

  @Override
  public Response execute() {
    final Response response = new Response();

    try {
      final URI uri = new URI(request.getUrl());
      final HttpURLConnection connection = getHttpConnection(uri);
      connection.setDoOutput(true);
      connection.setDoInput(true);

      // Set request method
      connection.setRequestMethod(request.getMethod().toString());

      // Set headers
      if (!request.getHeader().isEmpty()) {
        request.getHeader().forEach(connection::setRequestProperty);
      }

      // Set a request body
      if (!request.getBody().isEmpty()) {
        OutputStreamWriter os = new OutputStreamWriter(connection.getOutputStream());
        os.write(request.getBody());
        os.flush();
      }

      // Execute and get a response
      String responseBody = sendRequestAndReceiveResponse(connection);

      // Set a response request to a response object
      response.setBody(responseBody);
      response.setStatusCode(connection.getResponseCode());
      response.setHeaders(getHeaders(connection));

      connection.disconnect();
    //} catch (FileNotFoundException e) {
     // throw new UrlNotFoundException(e.getMessage(), e.getCause());

    } catch (URISyntaxException | IOException e) {
      e.printStackTrace();
    }

    return response;
  }

  private HttpURLConnection getHttpConnection(URI uri) throws IOException {
    return (HttpURLConnection) uri.toURL().openConnection();
  }

  private String sendRequestAndReceiveResponse(HttpURLConnection connection) throws IOException {
    InputStreamReader inputStream = new InputStreamReader((connection.getInputStream()));
    return getResponse(inputStream);
  }

  private String getResponse(InputStreamReader inputStreamReader) {
    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
    return bufferedReader.lines().collect(joining());
  }

  private Map<String, String> getHeaders(HttpURLConnection connection) {
    return connection.getHeaderFields()
        .entrySet()
        .stream()
        .collect(toMap(Entry::getKey, entry -> {
          if (!entry.getValue().isEmpty()) {
            return entry.getValue().get(0);
          }
          return Constants.EMPTY;
        }));
  }
}
