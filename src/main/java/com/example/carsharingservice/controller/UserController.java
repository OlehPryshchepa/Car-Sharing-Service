package com.example.carsharingservice.controller;

import com.example.carsharingservice.dto.request.UserRequestDto;
import com.example.carsharingservice.dto.response.UserResponseDto;
import com.example.carsharingservice.mapper.DtoMapper;
import com.example.carsharingservice.model.User;
import com.example.carsharingservice.service.UserService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
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

    //not tested
    @PutMapping("/me")
    public UserResponseDto updateProfile(Authentication authentication,
                                @RequestBody UserRequestDto userRequestDto) {
        User user = userMapper.mapToModel(userRequestDto);
        user.setId(userService.findByEmail(authentication.getName()).getId());
        return userMapper.mapToDto(userService.update(user));
    }
}
