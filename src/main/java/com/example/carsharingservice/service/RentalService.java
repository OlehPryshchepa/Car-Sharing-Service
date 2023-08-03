package com.example.carsharingservice.service;

import com.example.carsharingservice.model.Rental;

import java.util.List;

public interface RentalService {

    Rental save(Rental e);

    Rental find(Long id);

    List<Rental> findByUSerId(Long id, boolean isActive);

    Rental returnCar(Long id);
}
