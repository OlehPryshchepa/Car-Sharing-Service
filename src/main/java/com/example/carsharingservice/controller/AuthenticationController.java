package com.example.carsharingservice.controller;

import com.example.carsharingservice.dto.request.UserLoginDto;
import com.example.carsharingservice.dto.request.UserRegistrationDto;
import com.example.carsharingservice.dto.request.UserRequestDto;
import com.example.carsharingservice.dto.response.UserResponseDto;
import com.example.carsharingservice.exception.AuthenticationException;
import com.example.carsharingservice.mapper.DtoMapper;
import com.example.carsharingservice.model.User;
import com.example.carsharingservice.security.jwt.JwtTokenProvider;
import com.example.carsharingservice.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Tag(name = "Authentication", description = "The Authentication API. "
        + "Describes registration of new users and authentication of already registered users")
public class AuthenticationController {
    private final AuthenticationService authService;
    private final JwtTokenProvider jwtTokenProvider;
    private final DtoMapper<UserRequestDto, UserResponseDto, User> userMapper;

    @Operation(summary = "Register new user",
            description = "The method registers a new user. It takes in a UserRegistrationDto "
                    + "object as a request body, containing the user's registration details like "
                    + "email, first name, last name, password and password. The method then calls "
                    + "the authService to register the new user with the provided information. "
                    + " Upon successful registration, it receives a User object, which it maps "
                    + "to its corresponding response DTO (UserResponseDto) using the userMapper "
                    + "and returns it.")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = User.class),
                            mediaType = "application/json")})
    })
    @PostMapping("/register")
    public UserResponseDto register(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Here you need to specify the fields required for user "
                    + "registration in json format")
                                        @RequestBody UserRegistrationDto request) {
        User user = authService.register(request.getEmail(), request.getFirstName(),
                request.getLastName(), request.getPassword());
        return userMapper.mapToDto(user);
    }

    @Operation(summary = "Authenticate new user",
            description = "The method handles user login functionality. It takes in a validated "
                    + "UserLoginDto object as a request body, containing the user's login "
                    + "credentials (email and password). The method then calls the authService "
                    + "to authenticate the user's login credentials. If the login is successful, "
                    + "it generates a JSON Web Token using the jwtTokenProvider, containing the "
                    + "user's email and role, and returns it as a response body wrapped in a "
                    + "ResponseEntity. The generated token can be used for further authentication "
                    + "and authorization purposes.")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = ResponseEntity.class),
                            mediaType = "application/json")})
    })
    @PostMapping("/login")
    public ResponseEntity<Object> login(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Here you need to specify the fields required for user "
                    + "authentication in json format")
                                            @RequestBody @Valid UserLoginDto userLoginDto)
            throws AuthenticationException {
        User user = authService.login(userLoginDto.getLogin(),
                userLoginDto.getPassword());
        String token = jwtTokenProvider.createToken(user.getEmail(),
                List.of(user.getRole().name()));
        return new ResponseEntity<>(Map.of("token", token), HttpStatus.OK);
    }
}
