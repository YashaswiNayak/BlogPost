package com.yashaswi.BlogPost.exception;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(Integer id) {
        super("The comment with id: "+id+" is not found");
    }
}
