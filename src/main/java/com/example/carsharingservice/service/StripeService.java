package com.example.carsharingservice.service;

import com.example.carsharingservice.model.Car;
import com.example.carsharingservice.model.Payment;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;

public interface StripeService {
    Session createSession(Payment payment, Car car) throws StripeException;

    String checkStatus(String sessionId);
}
