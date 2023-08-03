package com.example.carsharingservice.controller;

import java.util.List;
import com.example.carsharingservice.dto.request.PaymentRequestDto;
import com.example.carsharingservice.dto.response.PaymentResponseDto;
import com.example.carsharingservice.mapper.DtoMapper;
import com.example.carsharingservice.model.Payment;
import com.example.carsharingservice.service.PaymentService;
import com.example.carsharingservice.service.RentalService;
import com.example.carsharingservice.service.StripeService;
import com.stripe.model.checkout.Session;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/payments")
public class PaymentController {
    private final StripeService stripeService;
    private final RentalService rentalService;
    private final PaymentService paymentService;
    private final DtoMapper<PaymentRequestDto, PaymentResponseDto, Payment> paymentDtoMapper;

    @GetMapping
    public List<String> getAll(@RequestParam Long userId) {
        return List.of("get all payments by userId " + userId);
    }

    @PostMapping
    public Session create(@RequestParam Long rentalId) {
        return new Session();
    }

    @GetMapping("/success")
    public String check() {
        return "successful Stripe payments was checked";
    }

    @GetMapping("/cancel")
    public String cancel() {
        return "payment paused message was canceled";
    }
}
