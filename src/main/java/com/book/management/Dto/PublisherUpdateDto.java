package com.book.management.Dto;

import lombok.Data;

@Data
public class PublisherUpdateDto {
    private String name;
    private String lastName;
    private String username;
    private Long phoneNumber;
    private String street;
    private String country;
    private String postalCode;
    private String about;
    private String email;
}
