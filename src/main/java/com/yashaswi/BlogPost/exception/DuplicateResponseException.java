package com.yashaswi.BlogPost.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateResponseException extends RuntimeException {
    public DuplicateResponseException(String message) {
        super(message);
    }
}
