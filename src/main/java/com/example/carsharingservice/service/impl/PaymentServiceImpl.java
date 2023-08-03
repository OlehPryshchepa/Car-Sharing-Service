package com.example.carsharingservice.service.impl;

import com.example.carsharingservice.model.Payment;
import com.example.carsharingservice.model.Rental;
import com.example.carsharingservice.model.User;
import com.example.carsharingservice.repository.PaymentRepository;
import com.example.carsharingservice.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private PaymentRepository paymentRepository;

    @Override
    public Payment add(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public List<Payment> getByUser(User user) {
        return paymentRepository.findByRentalUser(user);
    }

    @Override
    public Payment.PaymentType findType(Rental rental) {
        if (rental.getActualReturnDate().isAfter(rental.getReturnDate())) {
            return Payment.PaymentType.FINE;
        }
        return Payment.PaymentType.PAYMENT;
    }

    @Override
    @Transactional
    public Payment setPaid(String sessionId) {
        Payment payment = paymentRepository.findBySessionId(sessionId);
        payment.setPaymentStatus(Payment.PaymentStatus.PAID);
        return payment;
    }
}
