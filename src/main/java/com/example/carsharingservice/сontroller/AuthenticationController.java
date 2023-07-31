package com.example.carsharingservice.сontroller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @PostMapping("/register")
    public String register() {
        return "user was registered";
    }
}
