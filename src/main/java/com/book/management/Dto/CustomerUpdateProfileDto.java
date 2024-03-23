package com.book.management.Dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class CustomerUpdateProfileDto {
    private String name;
    private String username;
    private String email;
    private Long phoneNumber;
    private String country;
}
