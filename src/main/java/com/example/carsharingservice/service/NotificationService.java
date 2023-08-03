package com.example.carsharingservice.service;

import com.example.carsharingservice.model.Payment;
import com.example.carsharingservice.model.Rental;
import com.example.carsharingservice.model.User;

public interface NotificationService {
    void sendTelegramMessage(User user, String message);
}
