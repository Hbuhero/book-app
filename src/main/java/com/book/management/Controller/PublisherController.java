package com.book.management.Controller;

import com.book.management.Dto.PasswordDto;
import com.book.management.Dto.PublisherDto;
import com.book.management.Dto.PublisherUpdateDto;
import com.book.management.Model.Publisher;
import com.book.management.Service.PublisherService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/publisher")
public class PublisherController {

    private final PublisherService publisherService;

    @Autowired
    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getPublisherById(@PathVariable Long id){
        return publisherService.getPublisherById(id);
    }

//    @GetMapping("/{name}")
//    public ResponseEntity<?> getPublisherByName(@PathVariable String name){
//        return publisherService.getPublisherByName(name);
//    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody PublisherDto publisherDto){
        return publisherService.register(publisherDto);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateProfile(@RequestBody PublisherUpdateDto publisherUpdateDto){
        return publisherService.updateProfile(publisherUpdateDto);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody PasswordDto passwordDto){
        return publisherService.forgotPassword(passwordDto);

    }

    @GetMapping("/{id}/books")
    public ResponseEntity<?> getBooks(@PathVariable Long id){
        return publisherService.getBooks(id);
    }

    @GetMapping("/books/")
    public ResponseEntity<?> getBooksByPublisher(@RequestParam String name){
        return publisherService.getBooksByName(name);
    }

}
