package com.example.carsharingservice.controller;

import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rentals")
public class RentalController {

    @PostMapping
    public String add() {
        return "rental was added";
    }

    @GetMapping()
    public String get(@RequestParam Long userId,
                      @RequestParam Boolean isActive) {
        return "get rental with userId " + userId
                + " and active status " + isActive;
    }

    @GetMapping("/{id}")
    public String get(@PathVariable Long id) {
        return "get rental with id " + id;
    }

    @PostMapping("/{id}/return")
    public String setReturnDate(@PathVariable Long id,
                                @RequestBody LocalDateTime dateTime) {
        return "set actual return date " + dateTime
                + " with id " + id;
    }
}
