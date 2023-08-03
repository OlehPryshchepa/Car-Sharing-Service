package com.example.carsharingservice.dto.request;

import lombok.Data;

@Data
public class UserRegistrationDto {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String repeatPassword;
}
