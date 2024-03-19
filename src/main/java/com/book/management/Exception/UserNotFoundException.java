package com.book.management.Exception;

public class UserNotFoundException extends RuntimeException {
    private String errorMsg;
    public UserNotFoundException(String errorMsg) {
        super(errorMsg);
    }
}
