package com.example.carsharingservice.controller;

import com.example.carsharingservice.dto.request.UserRequestDto;
import com.example.carsharingservice.dto.response.UserResponseDto;
import com.example.carsharingservice.mapper.ResponseDtoMapper;
import com.example.carsharingservice.model.User;
import com.example.carsharingservice.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authService;
    private final ResponseDtoMapper<UserResponseDto, User> userDtoResponseMapper;

    @PostMapping("/register")
    public UserResponseDto register(@RequestBody UserRequestDto requestDto) {
        User user = authService.register(requestDto.getEmail(), requestDto.getPassword());
        return userDtoResponseMapper.mapToDto(user);
    }
}
