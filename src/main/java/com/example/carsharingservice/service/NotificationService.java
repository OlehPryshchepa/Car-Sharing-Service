package com.example.carsharingservice.service;

import com.example.carsharingservice.model.Payment;
import com.example.carsharingservice.model.Rental;

public interface NotificationService {
    void sendNewRentalNotification(Rental rental);
    void sendOverdueRentalNotification(Rental rental);
    void sendPaymentSuccessNotification(Payment payment);
}
