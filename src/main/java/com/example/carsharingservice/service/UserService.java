package com.example.carsharingservice.service;

import com.example.carsharingservice.model.User;
import java.util.Optional;

public interface UserService {
    User add(User user);

    User get(Long id);

    User update(User user);

    Optional<User> findByEmail(String email);
}
