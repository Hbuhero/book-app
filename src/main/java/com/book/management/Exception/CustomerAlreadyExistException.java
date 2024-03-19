package com.book.management.Exception;

public class CustomerAlreadyExistException extends RuntimeException{
    private String errorMsg;

    public CustomerAlreadyExistException(String errorMsg){
        super(errorMsg);
    }
}
