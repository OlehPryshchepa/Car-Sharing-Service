package com.example.carsharingservice.dto.request;

import com.example.carsharingservice.model.Car;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CarRequestDto {
    private String model;
    private String brand;
    private Car.CarType type;
    private int inventory;
    private BigDecimal dailyFree;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Car.CarType getType() {
        return type;
    }

    public void setType(Car.CarType type) {
        this.type = type;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public BigDecimal getDailyFree() {
        return dailyFree;
    }

    public void setDailyFree(BigDecimal dailyFree) {
        this.dailyFree = dailyFree;
    }
}
