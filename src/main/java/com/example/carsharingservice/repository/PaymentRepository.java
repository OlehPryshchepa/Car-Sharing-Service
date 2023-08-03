package com.example.carsharingservice.repository;

import com.example.carsharingservice.model.Payment;
import com.example.carsharingservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByRentalUser(User user);

    Payment findBySessionId(String sessionId);
}
