package com.book.management.Controller;

import com.book.management.Dto.CustomerDto;
import com.book.management.Dto.CustomerUpdateProfileDto;
import com.book.management.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody CustomerDto customerdto){
        String responseBody = customerService.registerUser(customerdto);

        return new ResponseEntity<>(responseBody,HttpStatusCode.valueOf(200));
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody CustomerDto customerDto){
        String response = customerService.changePassword(customerDto);
        return new ResponseEntity<>(response,HttpStatusCode.valueOf(200));
    }

    @PostMapping("/update-profile/{id}")
    public ResponseEntity<?> updateProfile(@RequestBody CustomerUpdateProfileDto customerUpdateProfileDto, @PathVariable Long id){
        return customerService.updateProfile(customerUpdateProfileDto, id);
    }


}
