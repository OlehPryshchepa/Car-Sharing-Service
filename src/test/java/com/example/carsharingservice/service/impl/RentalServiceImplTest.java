package com.example.carsharingservice.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import com.example.carsharingservice.model.Car;
import com.example.carsharingservice.model.Rental;
import com.example.carsharingservice.model.User;
import com.example.carsharingservice.repository.CarRepository;
import com.example.carsharingservice.repository.RentalRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class RentalServiceImplTest {

    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private RentalServiceImpl rentalService;

    @Test
    void testSaveRental() {
        Car car = new Car();
        car.setId(1L);
        car.setInventory(5);

        Rental rental = new Rental();
        rental.setId(1L);
        rental.setCar(car);
        rental.setUser(new User());
        rental.setRentalDate(LocalDateTime.now());
        rental.setReturnDate(LocalDateTime.of(2023, Month.DECEMBER, 5, 0, 0));

        when(carRepository.findById(anyLong())).thenReturn(Optional.of(car));
        when(carRepository.save(any())).thenReturn(car);
        when(rentalRepository.save(any())).thenReturn(rental);

        Rental savedRental = rentalService.save(rental);

        assertNotNull(savedRental);
        assertEquals(4, car.getInventory());
    }

    @Test
    void testFindById() {
        Long rentalId = 1L;
        Rental rental = new Rental();
        rental.setId(rentalId);

        when(rentalRepository.findById(rentalId)).thenReturn(Optional.of(rental));

        Rental foundRental = rentalService.find(rentalId);

        assertNotNull(foundRental);
        assertEquals(rentalId, foundRental.getId());

        verify(rentalRepository, times(1)).findById(rentalId);
    }

    @Test
    void testFindByUserId() {
        Long userId = 1L;
        boolean isActive = true;
        int page = 0;
        int size = 10;
        PageRequest request = PageRequest.of(page, size);

        List<Rental> activeRentals = Arrays.asList(new Rental(), new Rental(), new Rental());

        when(rentalRepository.findByUserIdAndActualReturnDateIsNull(userId, request))
                .thenReturn(activeRentals);

        List<Rental> foundRentals = rentalService.findByUSerId(userId, isActive, request);

        assertNotNull(foundRentals);
        assertEquals(activeRentals.size(), foundRentals.size());

        verify(rentalRepository, times(1)).findByUserIdAndActualReturnDateIsNull(userId, request);
    }

    @Test
    void testReturnCar() {
        Long rentalId = 1L;
        Rental rental = new Rental();
        rental.setId(rentalId);
        Car car = new Car();
        car.setInventory(4);
        rental.setCar(car);

        when(rentalRepository.findById(rentalId)).thenReturn(Optional.of(rental));
        when(carRepository.save(any())).thenReturn(car);
        when(rentalRepository.save(any())).thenReturn(rental);

        Rental returnedRental = rentalService.returnCar(rentalId);

        assertNotNull(returnedRental);
        assertNotNull(returnedRental.getActualReturnDate());
        assertEquals(5, car.getInventory());

        verify(rentalRepository, times(1)).findById(rentalId);
        verify(carRepository, times(1)).save(any());
        verify(rentalRepository, times(1)).save(any());
    }
}