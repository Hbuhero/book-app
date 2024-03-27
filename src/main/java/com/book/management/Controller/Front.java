package com.book.management.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class Front {
    @GetMapping("login")
    public String login(){
        return "login";
    }

    @GetMapping("signup")
    public String signup(){
        return "signup";
    }

    @GetMapping("welcome")
    public String welcome(){
        return "welcome";
    }

    @GetMapping("signup-customer")
    public String signupUser(){
        return "signup-customer";
    }

    @GetMapping("welcome-customer")
    public String welcomeCustomer(){
        return "welcome-customer";
    }
}
