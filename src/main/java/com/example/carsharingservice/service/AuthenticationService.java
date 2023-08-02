package com.example.carsharingservice.service;

import com.example.carsharingservice.model.User;

public interface AuthenticationService {
    User register(String email, String password);
}
