package com.book.management.Controller;

import com.book.management.Dto.CustomerDto;
import com.book.management.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
public class CustomerController {

    @Autowired
    private CustomerService userService;


    @PostMapping("/register")
    public ResponseEntity<String> registerUSer(@RequestBody CustomerDto customerdto){
        String responseBody = userService.registerUser(customerdto);

        return new ResponseEntity<>(responseBody,HttpStatusCode.valueOf(200));
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody CustomerDto customerDto){
        String response = userService.changePassword(customerDto);
        return new ResponseEntity<>(response,HttpStatusCode.valueOf(200));
    }
}
