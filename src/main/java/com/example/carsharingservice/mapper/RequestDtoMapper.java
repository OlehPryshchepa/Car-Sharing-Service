package com.example.carsharingservice.mapper;

public interface RequestDtoMapper<D, T> {
    T mapToModel(D dto);
}
