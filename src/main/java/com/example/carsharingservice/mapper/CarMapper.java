
package com.example.carsharingservice.mapper;

import com.example.carsharingservice.config.MapperConfig;
import com.example.carsharingservice.dto.request.CarRequestDto;
import com.example.carsharingservice.dto.response.CarResponseDto;
import com.example.carsharingservice.model.Car;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface CarMapper {
    @Mapping(target = "dailyFee", source = "dailyFee")
    Car mapToModel(CarRequestDto dto);

    @Mapping(target = "dailyFee", source = "dailyFee")
    CarResponseDto mapToDto(Car model);
}
