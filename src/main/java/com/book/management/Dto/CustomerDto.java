package com.book.management.Dto;

import lombok.Data;

@Data
public class CustomerDto {
    private String username;
    private String password;
    private Long phoneNumber;
    private String email;
    private String name;
    private String country;
}
