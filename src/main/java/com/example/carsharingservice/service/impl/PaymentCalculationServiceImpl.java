package com.example.carsharingservice.service.impl;

import com.example.carsharingservice.model.Car;
import com.example.carsharingservice.model.Payment;
import com.example.carsharingservice.model.Rental;
import com.example.carsharingservice.service.CarService;
import com.example.carsharingservice.service.PaymentCalculationService;
import com.example.carsharingservice.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class PaymentCalculationServiceImpl implements PaymentCalculationService {
    private static final double FINE_MULTIPLIER = 1.2;
    private final RentalService rentalService;
    private final CarService carService;

    @Override
    public BigDecimal calculatePaymentAmount(Payment payment) {
        Rental rental = rentalService.find(payment.getRental().getId());
        Car car = carService.get(rental.getCar().getId());
        long rentalDuration = ChronoUnit.DAYS.between(rental.getRentalDate(), rental.getReturnDate());
        return car.getDailyFee().multiply(BigDecimal.valueOf(rentalDuration));
    }

    @Override
    public BigDecimal calculateFineAmount(Payment payment) {
        Rental rental = rentalService.find(payment.getRental().getId());
        long overdueDays = rental.getActualReturnDate() != null
                ? ChronoUnit.DAYS.between(rental.getReturnDate(), rental.getActualReturnDate()) : 0;
        BigDecimal dailyFee = rental.getCar().getDailyFee();
        return dailyFee.multiply(BigDecimal.valueOf((overdueDays)
                * FINE_MULTIPLIER)).divide(BigDecimal.valueOf(100), RoundingMode.UP);
    }
}
