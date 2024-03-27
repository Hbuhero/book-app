package com.book.management.Configuration;

import com.book.management.Exception.UserNotFoundException;
import com.book.management.Repository.CustomerRepository;
import com.book.management.Repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {
    private final CustomerRepository customerRepository;

    private final PublisherRepository publisherRepository;

    @Autowired
    public ApplicationUserDetailsService(CustomerRepository customerRepository, PublisherRepository publisherRepository) {
        this.customerRepository = customerRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (customerRepository.existsByUsername(username)){
            return customerRepository.findByUsername(username).get();
        }else {
           return publisherRepository.findByUsername(username).orElseThrow(()-> new UserNotFoundException("User Not Found"));
        }
    }
}
