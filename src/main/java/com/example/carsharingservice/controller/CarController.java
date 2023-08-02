package com.example.carsharingservice.controller;

import com.example.carsharingservice.dto.request.CarRequestDto;
import com.example.carsharingservice.dto.response.CarResponseDto;
import com.example.carsharingservice.mapper.RequestDtoMapper;
import com.example.carsharingservice.mapper.ResponseDtoMapper;
import com.example.carsharingservice.model.Car;
import com.example.carsharingservice.service.CarService;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cars")
@AllArgsConstructor
public class CarController {
    private final CarService carService;
    private final ResponseDtoMapper<CarResponseDto, Car> responseDtoMapper;
    private final RequestDtoMapper<CarRequestDto, Car> requestDtoMapper;

    @PostMapping
    public CarResponseDto add(@RequestBody CarRequestDto requestDto) {
        Car car = carService.add(requestDtoMapper.mapToModel(requestDto));
        return responseDtoMapper.mapToDto(car);
    }

    @GetMapping
    public List<CarResponseDto> getAll() {
        return carService.getAll().stream()
                .map(responseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CarResponseDto get(@PathVariable Long id) {
        return responseDtoMapper.mapToDto(carService.get(id));
    }

    @PutMapping("/{id}")
    public CarResponseDto update(@PathVariable Long id,
                                 @RequestBody @Valid CarRequestDto requestDto) {
        Car car = requestDtoMapper.mapToModel(requestDto);
        return responseDtoMapper.mapToDto(carService.update(id, car));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        carService.delete(id);
    }
}
