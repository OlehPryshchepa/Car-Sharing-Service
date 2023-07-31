package com.example.carsharingservice.сontroller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @GetMapping
    public List<String> getAll(@RequestParam Long userId) {
        return List.of("get all payments by userId " + userId);
    }

    @PostMapping
    public String create() {
        return "payment session was created";
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
