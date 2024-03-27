package com.book.management.ServiceImpl;

import com.book.management.Dto.PasswordDto;
import com.book.management.Dto.PublisherDto;
import com.book.management.Dto.PublisherUpdateDto;
import com.book.management.Exception.PasswordDoesNotMatchException;
import com.book.management.Exception.UserAlreadyExistException;

import com.book.management.Exception.UserNotFoundException;
import com.book.management.Exception.UsernameAlreadyExists;
import com.book.management.Model.Publisher;
import com.book.management.Model.Roles;
import com.book.management.Repository.CustomerRepository;
import com.book.management.Repository.PublisherRepository;
import com.book.management.Service.PublisherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;

    private final PasswordEncoder passwordEncoder;

    private final CustomerRepository customerRepository;

    @Autowired
    public PublisherServiceImpl(PublisherRepository publisherRepository, PasswordEncoder passwordEncoder, CustomerRepository customerRepository) {
        this.publisherRepository = publisherRepository;
        this.passwordEncoder = passwordEncoder;
        this.customerRepository = customerRepository;
    }

    @Override
    public ResponseEntity<?> getPublisherById(Long id) {
        Optional<Publisher> optionalPublisher = publisherRepository.findById(id);
        if (optionalPublisher.isEmpty()){
            return new ResponseEntity<>(new UserNotFoundException("Publisher Not Found"), HttpStatusCode.valueOf(404));
        }

        return new ResponseEntity<>(optionalPublisher.get(), HttpStatusCode.valueOf(200));
    }

    @Override
    public ResponseEntity<?> register(PublisherDto publisherDto) {

        if(customerRepository.existsByUsername(publisherDto.getUsername()) || publisherRepository.existsByUsername(publisherDto.getUsername())){
            return new ResponseEntity<>(new UsernameAlreadyExists("Username Found"), HttpStatusCode.valueOf(409));
        }

         var publisher = Publisher.builder()
                  .name(publisherDto.getName())
                  .password(passwordEncoder.encode(publisherDto.getPassword()))
                  .phoneNumber(publisherDto.getPhoneNumber())
                  .country(publisherDto.getCountry())
                  .street(publisherDto.getStreet())
                  .postalCode(publisherDto.getPostalCode())
                  .username(publisherDto.getUsername())
                  .about(publisherDto.getAbout())
                 .roles(List.of(Roles.PUBLISHER))
                  .build();
        publisherRepository.save(publisher);

        return new ResponseEntity<>("Registered Successfully",HttpStatusCode.valueOf(200));
    }

    @Override
    public ResponseEntity<?> forgotPassword(PasswordDto passwordDto) {
        Optional<Publisher> optionalPublisher = publisherRepository.findByUsername(passwordDto.getUsername());
        if (optionalPublisher.isEmpty()){
            return new ResponseEntity<>(new UserNotFoundException("Publisher Not Found"), HttpStatusCode.valueOf(404));
        }

        if (!passwordDto.getNewPassword().equals(passwordDto.getConfirmPassword())){
            return new ResponseEntity<>(new PasswordDoesNotMatchException("Password Does Not Match"), HttpStatusCode.valueOf(400));
        }

        var publisher = optionalPublisher.get();
        publisher.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
        publisherRepository.save(publisher);
        return new ResponseEntity<>("Password Changed Successfully",HttpStatusCode.valueOf(200));
    }


    @Override
    public ResponseEntity<?> updateProfile(PublisherUpdateDto publisherUpdateDto, Long id) {
        //get publisher from repo
        Optional<Publisher> optionalPublisher = publisherRepository.findById(id);

        //check if exists
        if(optionalPublisher.isEmpty()){
            return new ResponseEntity<>(new UserNotFoundException("User Not Found"), HttpStatusCode.valueOf(404));
        }

        //check if the new username is already used
        if (publisherRepository.findByUsername(publisherUpdateDto.getUsername()).isPresent()){
            return new ResponseEntity<>(new UsernameAlreadyExists("Username Already Exists"), HttpStatusCode.valueOf(409));
        }

        Publisher publisher = optionalPublisher.get();

        publisher.setAbout(publisherUpdateDto.getAbout());
        publisher.setEmail(publisherUpdateDto.getEmail());
        publisher.setName(publisherUpdateDto.getName());

        publisher.setUsername(publisherUpdateDto.getUsername());
        publisher.setPhoneNumber(publisherUpdateDto.getPhoneNumber());
        publisher.setStreet(publisherUpdateDto.getStreet());
        publisher.setCountry(publisherUpdateDto.getCountry());
        publisher.setPostalCode(publisherUpdateDto.getPostalCode());

        publisherRepository.save(publisher);
        return new ResponseEntity<>("Update Success", HttpStatusCode.valueOf(200));
    }


}
