
package com.example.carsharingservice.mapper.impl;

import com.example.carsharingservice.dto.request.CarRequestDto;
import com.example.carsharingservice.dto.response.CarResponseDto;
import com.example.carsharingservice.mapper.RequestDtoMapper;
import com.example.carsharingservice.mapper.ResponseDtoMapper;
import com.example.carsharingservice.model.Car;
import org.springframework.stereotype.Component;

@Component
public class CarMapper implements RequestDtoMapper<CarRequestDto, Car>,
        ResponseDtoMapper<CarResponseDto, Car> {
    @Override
    public Car mapToModel(CarRequestDto dto) {
        Car car = new Car();
        car.setModel(dto.getModel());
        car.setType(Car.CarType.valueOf((dto.getType().name())));
        car.setInventory(dto.getInventory());
        car.setBrand(dto.getBrand());
        car.setDailyFee(dto.getDailyFee());
        return car;
    }

    @Override
    public CarResponseDto mapToDto(Car car) {
        CarResponseDto dto = new CarResponseDto();
        dto.setId(car.getId());
        dto.setInventory(car.getInventory());
        dto.setBrand(car.getBrand());
        dto.setModel(car.getModel());
        dto.setType(car.getType());
        dto.setDailyFee(car.getDailyFee());
        return dto;
    }
}
