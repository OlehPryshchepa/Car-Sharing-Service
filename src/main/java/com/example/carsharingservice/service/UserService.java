package com.example.carsharingservice.service;

import java.util.Optional;
import com.example.carsharingservice.model.User;

public interface UserService {
    User add(User user);

    User get(Long id);

    User update(User user);

    User update(Long id, User.Role role);

    Optional<User> findByEmail(String email);
}
