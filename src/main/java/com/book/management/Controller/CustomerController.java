package com.book.management.Controller;

import com.book.management.Dto.CustomerDto;
import com.book.management.Dto.CustomerUpdateProfileDto;
import com.book.management.Dto.PasswordDto;
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
    public ResponseEntity<?> registerUser(@RequestBody CustomerDto customerdto){


        return customerService.registerUser(customerdto);
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordDto passwordDto){

        return customerService.changePassword(passwordDto);
    }

    @PostMapping("/{id}/update-profile")
    public ResponseEntity<?> updateProfile(@RequestBody CustomerUpdateProfileDto customerUpdateProfileDto, @PathVariable Long id){
        return customerService.updateProfile(customerUpdateProfileDto, id);
    }


}
