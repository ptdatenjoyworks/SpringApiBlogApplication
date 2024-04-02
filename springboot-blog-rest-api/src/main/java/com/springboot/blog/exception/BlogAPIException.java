package com.springboot.blog.exception;

import org.springframework.http.HttpStatus;

public class BlogAPIException extends RuntimeException{
    public BlogAPIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    private HttpStatus status;
    private String message;


    public BlogAPIException(String message, HttpStatus httpStatus, String message1) {
        super(message);
        this.status = httpStatus;
        this.message = message1;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
