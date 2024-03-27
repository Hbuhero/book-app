package com.book.management.Service;

import com.book.management.Dto.CustomerDto;
import com.book.management.Dto.CustomerUpdateProfileDto;
import com.book.management.Dto.PasswordDto;
import org.springframework.http.ResponseEntity;

public interface CustomerService {
    ResponseEntity<?> registerUser(CustomerDto customerdto);

    ResponseEntity<?> changePassword(PasswordDto passwordDto);

    ResponseEntity<?> updateProfile(CustomerUpdateProfileDto customerUpdateProfileDto, Long id);


}
