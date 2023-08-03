package com.example.carsharingservice.service;

import com.example.carsharingservice.exception.AuthenticationException;
import com.example.carsharingservice.model.User;

public interface AuthenticationService {
    User register(String email, String firstName, String lastName, String password);

    User login(String email, String password) throws AuthenticationException;
}
