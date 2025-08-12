package com.yashaswi.BlogPost.exception;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(Integer id) {
        super("The task with ID " + id + "was not found.Please try again");
    }
}
