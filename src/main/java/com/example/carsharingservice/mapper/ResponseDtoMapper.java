package com.example.carsharingservice.mapper;

public interface ResponseDtoMapper<D, T> {
    D mapToDto(T t);
}
