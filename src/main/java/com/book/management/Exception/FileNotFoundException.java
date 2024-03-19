package com.book.management.Exception;

public class FileNotFoundException extends RuntimeException{

    private String errorMsg;

    public FileNotFoundException(String errorMsg) {
        super(errorMsg);

    }
}
