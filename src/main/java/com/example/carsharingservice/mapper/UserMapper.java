package com.example.carsharingservice.mapper;

import com.example.carsharingservice.dto.request.UserRequestDto;
import com.example.carsharingservice.dto.response.UserResponseDto;
import com.example.carsharingservice.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper implements RequestDtoMapper<UserRequestDto, User>,
        ResponseDtoMapper<UserResponseDto, User> {

    @Override
    public User mapToModel(UserRequestDto dto) {
        User user = new User();
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        return user;
    }

    @Override
    public UserResponseDto mapToDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setRole(String.valueOf(user.getRole()));
        return dto;
    }
}
