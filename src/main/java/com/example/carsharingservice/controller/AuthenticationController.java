package com.example.carsharingservice.controller;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import com.example.carsharingservice.dto.request.UserLoginDto;
import com.example.carsharingservice.dto.request.UserRegistrationDto;
import com.example.carsharingservice.dto.request.UserRequestDto;
import com.example.carsharingservice.dto.response.UserResponseDto;
import com.example.carsharingservice.exception.AuthenticationException;
import com.example.carsharingservice.mapper.DtoMapper;
import com.example.carsharingservice.model.User;
import com.example.carsharingservice.security.jwt.JwtTokenProvider;
import com.example.carsharingservice.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authService;
    private final JwtTokenProvider jwtTokenProvider;
    private final DtoMapper<UserRequestDto, UserResponseDto, User> userMapper;

    @PostMapping("/register")
    public UserResponseDto register(@RequestBody UserRegistrationDto request) {
        User user = authService.register(request.getEmail(), request.getFirstName(),
                request.getLastName(), request.getPassword());
        return userMapper.mapToDto(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid UserLoginDto userLoginDto) throws AuthenticationException {
        User user = authService.login(userLoginDto.getLogin(),
                userLoginDto.getPassword());
        String token = jwtTokenProvider.createToken(user.getEmail(), List.of(user.getRole().name()));
        return new ResponseEntity<>(Map.of("token", token), HttpStatus.OK);
    }
}
