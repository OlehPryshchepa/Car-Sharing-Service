package com.example.carsharingservice.controller;

import com.example.carsharingservice.dto.request.UserRequestDto;
import com.example.carsharingservice.dto.response.UserResponseDto;
import com.example.carsharingservice.mapper.DtoMapper;
import com.example.carsharingservice.model.User;
import com.example.carsharingservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final DtoMapper<UserRequestDto, UserResponseDto, User> userMapper;

    @PutMapping("/{id}/role")
    public UserResponseDto update(@PathVariable Long id) {
        User user = userService.get(id);
        return user.getRole() == User.Role.CUSTOMER
                ? userMapper.mapToDto(userService.update(id, User.Role.MANAGER))
                : userMapper.mapToDto(userService.update(id, User.Role.CUSTOMER));
    }

    @GetMapping("/me")
    public UserResponseDto get(Authentication authentication) {
        return userMapper.mapToDto(userService.findByEmail(authentication.getName()));
    }

    @PutMapping("/me")
    public UserResponseDto updateProfile(Authentication authentication,
                                         @RequestBody UserRequestDto userRequestDto) {
        User userDB = userService.findByEmail(authentication.getName());
        User userUpdate = userMapper.mapToModel(userRequestDto);
        userUpdate.setId(userDB.getId());
        userUpdate.setEmail(userUpdate.getEmail() == null
                ? userDB.getEmail() : userUpdate.getEmail());
        userUpdate.setFirstName(userUpdate.getFirstName() == null
                ? userDB.getFirstName() : userUpdate.getFirstName());
        userUpdate.setLastName(userUpdate.getLastName() == null
                ? userDB.getLastName() : userUpdate.getLastName());
        userUpdate.setPassword(userUpdate.getPassword() == null
                ? userDB.getPassword() : passwordEncoder.encode(userUpdate.getPassword()));
        userUpdate.setRole(userUpdate.getRole() == null
                ? userDB.getRole() : userUpdate.getRole());
        return userMapper.mapToDto(userService.update(userUpdate));
    }
}
