package com.book.management.Service;

import com.book.management.Dto.CustomerDto;

public interface CustomerService {
    String registerUser(CustomerDto customerdto);

    String changePassword(CustomerDto customerDto);
}
