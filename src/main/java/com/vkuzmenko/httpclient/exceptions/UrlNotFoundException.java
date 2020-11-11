package com.vkuzmenko.httpclient.exceptions;

public class UrlNotFoundException extends RuntimeException {

  private final String message;
  private final Throwable cause;

  public UrlNotFoundException(String message, Throwable cause) {
    this.message = message;
    this.cause = cause;
  }
}
