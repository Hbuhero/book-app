package com.book.management.Dto;

import lombok.Data;

@Data
public class FileUpdateDto {
    private String filename;
    private String author;
    private String category;
    private String access;
}
