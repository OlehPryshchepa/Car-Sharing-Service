package com.example.carsharingservice.service.impl;

import com.example.carsharingservice.model.Car;
import com.example.carsharingservice.model.Rental;
import com.example.carsharingservice.repository.CarRepository;
import com.example.carsharingservice.repository.RentalRepository;
import com.example.carsharingservice.service.RentalService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {
    private final RentalRepository rentalRepository;
    private final CarRepository carRepository;

    @Override
    public Rental save(Rental rental) {
        Car car = carRepository.findById(rental.getCar().getId()).orElseThrow(() ->
                new RuntimeException("Can't find car by id:" + rental.getCar().getId()));
        rental.setCar(car);
        if (car.getInventory() == 0) {
            throw new RuntimeException("Can't decrease car inventory: " + rental);
        }
        car.setInventory(car.getInventory() - 1);
        carRepository.save(car);
        return rentalRepository.save(rental);
    }

    @Override
    public Rental find(Long id) {
        return rentalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Can`t find rental with id:" + id));
    }

    @Override
    public List<Rental> findByUSerId(Long id, boolean isActive) {
        if (isActive) {
            return rentalRepository.findByUserIdAndActualReturnDateIsNull(id);
        }
        return rentalRepository.findByUserIdAndActualReturnDateIsNotNull(id);
    }

    @Override
    public Rental returnCar(Long id) {
        Rental rentalToUpdate = find(id);
        Car car = rentalToUpdate.getCar();
        car.setInventory(car.getInventory() + 1);
        carRepository.save(car);
        rentalToUpdate.setActualReturnDate(LocalDateTime.now());
        return rentalRepository.save(rentalToUpdate);
    }
}
