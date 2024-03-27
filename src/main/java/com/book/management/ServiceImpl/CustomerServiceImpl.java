package com.book.management.ServiceImpl;

import com.book.management.Dto.CustomerDto;
import com.book.management.Dto.CustomerUpdateProfileDto;
import com.book.management.Dto.PasswordDto;
import com.book.management.Exception.PasswordDoesNotMatchException;
import com.book.management.Exception.UserAlreadyExistException;
import com.book.management.Exception.UserNotFoundException;
import com.book.management.Exception.UsernameAlreadyExists;
import com.book.management.Model.Customer;

import com.book.management.Model.Roles;
import com.book.management.Repository.CustomerRepository;
import com.book.management.Repository.PublisherRepository;
import com.book.management.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;
    private final PublisherRepository publisherRepository;
    @Autowired
    public CustomerServiceImpl(PasswordEncoder passwordEncoder, CustomerRepository customerRepository, PublisherRepository publisherRepository) {
        this.passwordEncoder = passwordEncoder;
        this.customerRepository = customerRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public ResponseEntity<?> registerUser(CustomerDto customerdto) {


        if(customerRepository.existsByUsername(customerdto.getUsername()) || publisherRepository.existsByUsername(customerdto.getUsername())){
            return new ResponseEntity<>(new UsernameAlreadyExists("Username Found"), HttpStatusCode.valueOf(409));
        }

        Customer customer = Customer.builder()
                .username(customerdto.getUsername())
                .password(passwordEncoder.encode(customerdto.getPassword()))
                .roles(List.of(Roles.CUSTOMER))
                .name(customerdto.getName())
                .country(customerdto.getCountry())
                .phoneNumber(customerdto.getPhoneNumber())
                .email(customerdto.getEmail())
                .build();
        customerRepository.save(customer);

        return new ResponseEntity<>("User Successfully Registered", HttpStatusCode.valueOf(200));
    }

    @Override
    public ResponseEntity<?> changePassword(PasswordDto passwordDto) {
        Optional<Customer> customerOptional = customerRepository.findByUsername(passwordDto.getUsername());
        if (customerOptional.isEmpty()){
            throw new UserNotFoundException("User Not Found");
        }

        if (!passwordDto.getNewPassword().equals(passwordDto.getConfirmPassword())){
            return new ResponseEntity<>(new PasswordDoesNotMatchException("Password Does Not Match"), HttpStatusCode.valueOf(400));
        }

        Customer customer = customerOptional.get();
        customer.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));

        customerRepository.save(customer);

        return new ResponseEntity<>("Successfully changed Password", HttpStatusCode.valueOf(200));
    }

    @Override
    public ResponseEntity<?> updateProfile(CustomerUpdateProfileDto customerUpdateProfileDto, Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(()-> new UserNotFoundException("Not Found"));

        customer.setCountry(customerUpdateProfileDto.getCountry());
        customer.setName(customerUpdateProfileDto.getName());
        customer.setEmail(customerUpdateProfileDto.getEmail());
        customer.setUsername(customerUpdateProfileDto.getUsername());
        customer.setPhoneNumber(customerUpdateProfileDto.getPhoneNumber());

        customerRepository.save(customer);

        return new ResponseEntity<>("Profile Update Successful", HttpStatusCode.valueOf(200));
    }


}
