package com.example.carsharingservice.controller;

import com.example.carsharingservice.dto.request.UserRequestDto;
import com.example.carsharingservice.dto.response.UserResponseDto;
import com.example.carsharingservice.mapper.RequestDtoMapper;
import com.example.carsharingservice.mapper.ResponseDtoMapper;
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
    private final ResponseDtoMapper<UserResponseDto, User> responseDtoMapper;
    private final RequestDtoMapper<UserRequestDto, User> requestDtoMapper;

    @PutMapping("/{id}/role")
    public UserResponseDto update(@PathVariable Long id) {
        User user = userService.get(id);
        return user.getRole() == User.Role.CUSTOMER
                ? responseDtoMapper.mapToDto(userService.update(id, User.Role.MANAGER))
                : responseDtoMapper.mapToDto(userService.update(id, User.Role.CUSTOMER));
    }

    @GetMapping("/me")
    public UserResponseDto get(Authentication authentication) {
        return responseDtoMapper.mapToDto(userService.findByEmail(authentication.getName()));
    }

    @PutMapping("/me")
    public UserResponseDto updateProfile(Authentication authentication,
                                @RequestBody UserRequestDto userRequestDto) {
        User user = requestDtoMapper.mapToModel(userRequestDto);
        user.setId(userService.findByEmail(authentication.getName()).getId());
        return responseDtoMapper.mapToDto(userService.update(user));
    }
}
