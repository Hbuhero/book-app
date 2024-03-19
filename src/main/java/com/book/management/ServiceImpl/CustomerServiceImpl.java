package com.book.management.ServiceImpl;

import com.book.management.Dto.CustomerDto;
import com.book.management.Exception.CustomerAlreadyExistException;
import com.book.management.Exception.UserNotFoundException;
import com.book.management.Model.Customer;

import com.book.management.Repository.CustomerRepository;
import com.book.management.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(PasswordEncoder passwordEncoder, CustomerRepository customerRepository) {
        this.passwordEncoder = passwordEncoder;
        this.customerRepository = customerRepository;
    }

    @Override
    public String registerUser(CustomerDto customerdto) {

        Optional<Customer> optionalUser = customerRepository.findByUsername(customerdto.getUsername());

        if (optionalUser.isPresent()){
            throw new CustomerAlreadyExistException("User Already Exist");
        }

        Customer customer = Customer.builder()
                .username(customerdto.getUsername())
                .password(passwordEncoder.encode(customerdto.getPassword()))
                .build();
        customerRepository.save(customer);

        return "User Successfully Registered";
    }

    @Override
    public String changePassword(CustomerDto customerDto) {
        Optional<Customer> customerOptional = customerRepository.findByUsername(customerDto.getUsername());
        if (customerOptional.isEmpty()){
            throw new UserNotFoundException("User Not Found");
        }

        Customer customer = customerOptional.get();
        customer.setPassword(passwordEncoder.encode(customerDto.getPassword()));

        customerRepository.save(customer);

        return "Successfully changed Password";
    }
}
