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


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody PublisherDto publisherDto){
        return publisherService.register(publisherDto);
    }

    @PostMapping("/{id}/update")
    public ResponseEntity<?> updateProfile(@PathVariable Long id, @RequestBody PublisherUpdateDto publisherUpdateDto){
        return publisherService.updateProfile(publisherUpdateDto, id);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody PasswordDto passwordDto){
        return publisherService.forgotPassword(passwordDto);

    }




}
