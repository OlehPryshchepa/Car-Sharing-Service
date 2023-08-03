package com.example.carsharingservice.service.impl;

import com.example.carsharingservice.model.User;
import com.example.carsharingservice.service.AuthenticationService;
import com.example.carsharingservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;

    @Override
    public User register(String email, String firstName, String lastName, String password) {
        User user = new User();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        user.setRole(User.Role.CUSTOMER);
        user = userService.add(user);
        return user;
    }
}
