package com.example.carsharingservice.service;

import com.example.carsharingservice.model.Rental;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

public interface RentalService {

    Rental save(Rental e);

    Rental find(Long id);

    List<Rental> findByUSerId(Long id, boolean isActive);

    void updateActualReturnDate(Long id);
}
