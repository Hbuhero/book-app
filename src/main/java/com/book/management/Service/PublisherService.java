package com.book.management.Service;

import com.book.management.Dto.PasswordDto;
import com.book.management.Dto.PublisherDto;
import com.book.management.Dto.PublisherUpdateDto;
import com.book.management.Model.Publisher;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;

public interface PublisherService {
    ResponseEntity<?> getPublisherById(Long id);

    ResponseEntity<?> getPublisherByName(String name);

    ResponseEntity<?> register(PublisherDto publisherDto);

    ResponseEntity<?> forgotPassword(PasswordDto passwordDto);

    ResponseEntity<?> getBooks(Long id);

    ResponseEntity<?> getBooksByName(String name);

    ResponseEntity<?> updateProfile(PublisherUpdateDto publisherUpdateDto);
}
