package com.book.management.Service;

import com.book.management.Dto.CustomerDto;
import com.book.management.Dto.CustomerUpdateProfileDto;
import org.springframework.http.ResponseEntity;

public interface CustomerService {
    String registerUser(CustomerDto customerdto);

    String changePassword(CustomerDto customerDto);

    ResponseEntity<?> updateProfile(CustomerUpdateProfileDto customerUpdateProfileDto, Long id);


}
