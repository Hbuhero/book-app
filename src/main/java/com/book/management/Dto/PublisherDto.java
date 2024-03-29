package com.book.management.Dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class PublisherDto {
    private String name;
    private String username;
    private String password;
    private Long phoneNumber;
    private String street;
    private String country;
    private String postalCode;
    private String about;
}
