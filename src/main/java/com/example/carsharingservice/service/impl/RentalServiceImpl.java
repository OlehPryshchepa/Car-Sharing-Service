package com.example.carsharingservice.service.impl;

import com.example.carsharingservice.model.Car;
import com.example.carsharingservice.model.Rental;
import com.example.carsharingservice.model.User;
import com.example.carsharingservice.repository.RentalRepository;
import com.example.carsharingservice.service.CarService;
import com.example.carsharingservice.service.NotificationService;
import com.example.carsharingservice.service.RentalService;
import com.example.carsharingservice.service.UserService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {
    private final RentalRepository rentalRepository;
    private final CarService carService;
    private final UserService userService;
    private final NotificationService notificationService;

    @Override
    public Rental save(Rental rental) {
        Car car = carService.get(rental.getCar().getId());
        rental.setCar(car);
        if (car.getInventory() == 0) {
            throw new RuntimeException("Can't decrease car inventory: " + rental);
        }
        car.setInventory(car.getInventory() - 1);
        carService.add(car);
        Rental createdRental = rentalRepository.save(rental);
        User user = userService.get(createdRental.getUser().getId());
        notificationService.sendTelegramMessage(user, String
                .format("New rental was created.\n"
                                + "Rental info: %s\n"
                                + "User info: %s\n"
                                + "Car info: %s", createdRental, user, car));
        return createdRental;
}

    @Override
    public Rental find(Long id) {
        return rentalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Can`t find rental with id:" + id));
    }

    @Override
    public List<Rental> findByUSerId(Long id, boolean isActive, PageRequest request) {
        if (isActive) {
            return rentalRepository.findByUserIdAndActualReturnDateIsNull(id, request);
        }
        return rentalRepository.findByUserIdAndActualReturnDateIsNotNull(id, request);
    }

    @Override
    public Rental returnCar(Long id) {
        Rental rentalToUpdate = find(id);
        Car car = rentalToUpdate.getCar();
        car.setInventory(car.getInventory() + 1);
        carService.update(car.getId(), car);
        rentalToUpdate.setActualReturnDate(LocalDateTime.now());
        return rentalRepository.save(rentalToUpdate);
    }

    @Override
    public List<Rental> getOverdueRentals() {
        return rentalRepository.findAllByActualReturnDateNullAndReturnDateLessThan(LocalDateTime.now());
    }
}
