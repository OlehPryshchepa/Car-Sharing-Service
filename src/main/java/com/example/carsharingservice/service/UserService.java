package com.example.carsharingservice.service;

import com.example.carsharingservice.model.User;
import java.util.Optional;

public interface UserService {
    User add(User user);

    User get(Long id);

    User update(User user);

    User update(Long id, User.Role role);

    Optional<User> findByEmail(String email);
}
