package com.example.carsharingservice.mapper;

public interface ResponseDtoMapper<T, D> {
    D mapToDto(T t);
}
