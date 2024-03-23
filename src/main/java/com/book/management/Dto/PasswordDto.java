package com.book.management.Dto;

import lombok.Data;

@Data
public class PasswordDto {
    private String username;
    private String newPassword;
    private String confirmPassword;
}
