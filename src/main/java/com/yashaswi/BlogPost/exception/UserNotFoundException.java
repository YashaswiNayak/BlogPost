package com.yashaswi.BlogPost.exception;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(String username) {
    super("The user with "+ username +" was not found");
  }
}
