package com.example.carsharingservice.service.impl;

import com.example.carsharingservice.model.Car;
import com.example.carsharingservice.repository.CarRepository;
import com.example.carsharingservice.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CarServiceImplTest {
    private CarRepository carRepository;
    private CarService carService;

    @BeforeEach
    public void setUp() {
        carRepository = Mockito.mock(CarRepository.class);
        carService = new CarServiceImpl(carRepository);
    }

    @Test
    public void testAddCar() {
        Car car = new Car();
        car.setId(1L);
        car.setModel("A4");
        car.setBrand("Audi");
        car.setType(Car.CarType.SEDAN);
        car.setInventory(3);
        car.setDailyFee(BigDecimal.valueOf(100));
        when(carRepository.save(car)).thenReturn(car);

        Car savedCar = carService.add(car);
        assertNotNull(savedCar);
        assertEquals(car.getId(), savedCar.getId());
        assertEquals(car.getModel(), savedCar.getModel());
        assertEquals(car.getBrand(), savedCar.getBrand());
        assertEquals(car.getInventory(), savedCar.getInventory());
        assertEquals(car.getDailyFee(), savedCar.getDailyFee());
        assertEquals(car.getType(), savedCar.getType());

        verify(carRepository, times(1)).save(car);
    }


    @Test
    public void getAllCars_ok() {
        List<Car> cars = new ArrayList<>();
        Car car = new Car();
        car.setId(1L);
        car.setModel("A4");
        car.setBrand("Audi");
        car.setType(Car.CarType.SEDAN);
        car.setInventory(3);
        car.setDailyFee(BigDecimal.valueOf(100));
        cars.add(car);

        when(carRepository.findAll()).thenReturn(cars);
        List<Car> actual = carService.getAll();
        assertNotNull(actual);
        assertEquals(cars, actual);
        verify(carRepository, times(1)).findAll();
    }

    @Test
    public void deleteCar_ok() {
        Long carId = 1L;
        carService.delete(carId);
        verify(carRepository, times(1)).deleteById(carId);
    }

    @Test
    public void getCarByIdNotFound_notOk() {
        Long carId = 1L;
        when(carRepository.findById(carId)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> carService.get(carId));
        verify(carRepository, times(1)).findById(carId);
    }

    @Test
    public void testGetCarById() {
        Long carId = 1L;
        Car car = new Car();
        car.setId(1L);
        car.setModel("A4");
        car.setBrand("Audi");
        car.setType(Car.CarType.SEDAN);
        car.setInventory(3);
        car.setDailyFee(BigDecimal.valueOf(100));
        when(carRepository.findById(carId)).thenReturn(Optional.of(car));

        Car actual = carService.get(carId);
        assertNotNull(actual);
        assertEquals(car.getId(), actual.getId());
        assertEquals(car.getModel(), actual.getModel());
        assertEquals(car.getBrand(), actual.getBrand());
        assertEquals(car.getType(), actual.getType());
        assertEquals(car.getInventory(), actual.getInventory());
        assertEquals(car.getDailyFee(), actual.getDailyFee());
        verify(carRepository, times(1)).findById(carId);
    }

    @Test
    public void updateCar_ok() {
        Long carId = 1L;
        Car existingCar = new Car();
        existingCar.setId(1L);
        existingCar.setModel("A4");
        existingCar.setBrand("Audi");
        existingCar.setType(Car.CarType.SEDAN);
        existingCar.setInventory(3);
        existingCar.setDailyFee(BigDecimal.valueOf(100));
        when(carRepository.findById(carId)).thenReturn(Optional.of(existingCar));

        Car updatedCar = new Car();
        updatedCar.setId(1L);
        updatedCar.setModel("Corolla");
        updatedCar.setBrand("Toyota");
        updatedCar.setType(Car.CarType.SEDAN);
        updatedCar.setInventory(5);
        updatedCar.setDailyFee(BigDecimal.valueOf(50));
        when(carRepository.save(any(Car.class))).thenReturn(updatedCar);

        Car result = carService.update(carId, updatedCar);
        assertNotNull(result);
        assertEquals(updatedCar.getId(), result.getId());
        assertEquals(updatedCar.getModel(), result.getModel());
        assertEquals(updatedCar.getBrand(), result.getBrand());
        assertEquals(updatedCar.getType(), result.getType());
        assertEquals(updatedCar.getInventory(), result.getInventory());
        assertEquals(updatedCar.getDailyFee(), result.getDailyFee());

        verify(carRepository, times(1)).findById(carId);
        verify(carRepository, times(1)).save(any(Car.class));
    }
}