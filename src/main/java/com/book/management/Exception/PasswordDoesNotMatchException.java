package com.book.management.Exception;

public class PasswordDoesNotMatchException extends RuntimeException{
    public PasswordDoesNotMatchException(String errorMsg) {
        super(errorMsg);
    }
}
