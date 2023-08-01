package com.example.carsharingservice.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cars")
public class CarController {

    @PostMapping
    public String add() {
        return "car was added";
    }

    @GetMapping
    public List<String> getAll() {
        return List.of("get all cars");
    }

    @GetMapping("/{id}")
    public String get(@PathVariable Long id) {
        return "get car with id " + id;
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id) {
        return "car was updated with id " + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        return "car was deleted with id " + id;
    }
}
