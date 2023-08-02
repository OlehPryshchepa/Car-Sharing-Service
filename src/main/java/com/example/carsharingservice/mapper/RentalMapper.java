package com.example.carsharingservice.mapper;

import com.example.carsharingservice.dto.request.RentalRequestDto;
import com.example.carsharingservice.dto.response.RentalResponseDto;
import com.example.carsharingservice.model.Car;
import com.example.carsharingservice.model.Rental;
import org.springframework.stereotype.Component;

@Component
public class RentalMapper implements RequestDtoMapper<RentalRequestDto, Rental>,
        ResponseDtoMapper<RentalResponseDto, Rental> {

    @Override
    public Rental mapToModel(RentalRequestDto dto) {
        Car car = new Car();
        car.setId(dto.getCarId());
        Rental rental = new Rental();
        rental.setCar(car);
        rental.setReturnDate(dto.getReturnDate());
        rental.setRentalDate(dto.getRentalDate());
        return rental;
    }

    @Override
    public RentalResponseDto mapToDto(Rental rental) {
        RentalResponseDto dto = new RentalResponseDto();
        dto.setId(rental.getId());
        dto.setRentalDate(rental.getRentalDate());
        dto.setActualReturnDate(rental.getActualReturnDate());
        dto.setReturnDate(rental.getReturnDate());
        dto.setCarId(rental.getCar().getId());
        dto.setUserId(rental.getUser().getId());
        return dto;
    }
}
