package com.example.carsharingservice.mapper;

import com.example.carsharingservice.config.MapperConfig;
import com.example.carsharingservice.dto.request.UserRequestDto;
import com.example.carsharingservice.dto.response.UserResponseDto;
import com.example.carsharingservice.model.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(config = MapperConfig.class)
public interface UserMapper {
    User mapToModel(UserRequestDto dto);
    UserResponseDto mapToDto(User model);
}
