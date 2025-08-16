package com.book.exception.type;

public class CustomNotFoundException extends RuntimeException{
    public CustomNotFoundException(String message) {
        super(message);
    }
}
