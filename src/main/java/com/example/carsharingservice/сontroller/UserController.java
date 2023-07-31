package com.example.carsharingservice.сontroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @PutMapping("/{id}/role")
    public String update(@PathVariable Long id) {
        return "user was updated with id " + id;
    }

    @GetMapping()
    public String get() {
        return "get user profile info";
    }

    @PutMapping()
    public String updateProfile() {
        return "update user profile info";
    }
}
