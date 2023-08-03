package com.example.carsharingservice.service.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import com.example.carsharingservice.model.Rental;
import com.example.carsharingservice.service.RentalService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
@AllArgsConstructor
public class CronJobServiceImpl {
    private RentalService rentalService;
    private TelegramNotificationService notificationService;


    @Scheduled(fixedDelay = 10000)
    public void cronJobOverdueRentals() {
        List<Rental> overdueRentals = rentalService.getOverdueRentals();
        overdueRentals.stream()
                .forEach(rental -> notificationService.sendTelegramMessage(rental.getUser(), getMessage(rental)));
    }

    private static String getMessage(Rental rental) {
        LocalDateTime returnDate = rental.getReturnDate();
        return "Your rental number: " + rental.getId() + "\n"
                + "The model of the car you rented: " + rental.getCar().getModel() + "\n"
                + "The brand of the car you rented: " + rental.getCar().getBrand() + "\n"
                + "Lease start date: " + rental.getRentalDate() + "\n"
                + "Lease end date: " + returnDate + "\n"
                + "Days overdue: " + ChronoUnit.DAYS.between(returnDate, LocalDateTime.now());
    }
}
