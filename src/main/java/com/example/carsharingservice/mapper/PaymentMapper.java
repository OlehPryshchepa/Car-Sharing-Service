package com.example.carsharingservice.mapper;

import com.example.carsharingservice.config.MapperConfig;
import com.example.carsharingservice.dto.request.PaymentRequestDto;
import com.example.carsharingservice.dto.response.PaymentResponseDto;
import com.example.carsharingservice.dto.response.UserResponseDto;
import com.example.carsharingservice.model.Payment;
import com.example.carsharingservice.model.User;
import com.example.carsharingservice.service.RentalService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface PaymentMapper {
    @Mapping(target = "paymentAmount", source = "paymentAmount")
    @Mapping(target = "rentalId", source = "payment.rental.id")
    PaymentResponseDto toDto(Payment payment);
}
