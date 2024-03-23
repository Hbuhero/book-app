package com.book.management.Exception;

public class UsernameAlreadyExists extends RuntimeException{

    public UsernameAlreadyExists(String errorMsg) {
        super(errorMsg);
    }
}
