package com.book.management.Dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileDto {
    private String filename;
    private String author;
    private String category;
    private String access;
    private MultipartFile multipartFile;
    private Long publisherID;
}
