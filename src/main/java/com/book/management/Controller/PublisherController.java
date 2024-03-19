package com.book.management.Controller;

import com.book.management.Model.Publisher;
import com.book.management.Service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/publisher")
public class PublisherController {

    private final PublisherService publisherService;

    @Autowired
    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

//    @PostMapping("/register")
//    public ResponseEntity<String> registerPublisher(@RequestBody PublisherDto publisherDto){
////        publisherService.register(publisherDto);
//        return null;
//    }
}
