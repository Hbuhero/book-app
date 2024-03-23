package com.book.management.Exception;

public class UserAlreadyExistException extends RuntimeException{


    public UserAlreadyExistException(String errorMsg){
        super(errorMsg);
    }
}
