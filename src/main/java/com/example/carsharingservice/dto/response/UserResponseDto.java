package com.example.carsharingservice.dto.response;

import java.util.Set;

public class UserResponseDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Set<Long> rolesIds;
}
