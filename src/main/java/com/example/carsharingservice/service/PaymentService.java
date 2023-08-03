package com.example.carsharingservice.service;

import com.example.carsharingservice.model.Payment;
import com.example.carsharingservice.model.Rental;
import com.example.carsharingservice.model.User;
import java.util.List;

public interface PaymentService {
    Payment add(Payment payment);

    List<Payment> getByUser(User user);

    Payment.PaymentType findType(Rental rental);

    Payment setPaid(String sessionId);

}
