package com.example.carsharingservice.controller;

import com.example.carsharingservice.dto.request.CarRequestDto;
import com.example.carsharingservice.dto.response.CarResponseDto;
import com.example.carsharingservice.mapper.DtoMapper;
import com.example.carsharingservice.model.Car;
import com.example.carsharingservice.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Car", description = "The Car API. "
        + "Describes the complete set of operations that can be performed on car.")
public class CarController {
    private final CarService carService;
    private final DtoMapper<CarRequestDto, CarResponseDto, Car> carMapper;

    @Operation(summary = "Add new car",
            description = "The method handles the addition of a new car. "
                    + "It takes in a request DTO (Data Transfer Object) representing"
                    + " the car details as input and adds it to the system using "
                    + "the carService. The method then converts the resulting Car "
                    + "object into a response DTO (CarResponseDto) using the "
                    + "carMapper and returns it.")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = Car.class),
                            mediaType = "application/json")})
    })
    @PostMapping
    public CarResponseDto add(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Here you need to fill in the fields to create a new car")
                                  @RequestBody CarRequestDto requestDto) {
        Car car = carService.add(carMapper.mapToModel(requestDto));
        return carMapper.mapToDto(car);
    }

    @Operation(summary = "Get all cars",
            description = "The method retrieves all the cars available in "
                    + "the system. It calls the carService to get a list of Car "
                    + "objects and then maps each Car object to its corresponding "
                    + "response DTO (CarResponseDto) using the carMapper. Finally, "
                    + "it collects all the mapped DTOs into a List and returns it.")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = Car.class),
                            mediaType = "application/json")})
    })
    @GetMapping
    public List<CarResponseDto> getAll() {
        return carService.getAll().stream()
                .map(carMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get car by id",
            description = "The method retrieves a specific car by its unique "
                    + "identifier (id). It takes the id as a path variable and "
                    + "calls the carService to fetch the corresponding Car object "
                    + "from the system. The method then maps the Car object to its "
                    + "corresponding response DTO (CarResponseDto) using the "
                    + "carMapper and returns it.")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = Car.class),
                            mediaType = "application/json")})
    })
    @GetMapping("/{id}")
    public CarResponseDto get(@Parameter(
            description = "Press the button \"Try it out\" and put id to get the car")
                                  @PathVariable Long id) {
        return carMapper.mapToDto(carService.get(id));
    }

    @Operation(summary = "Update car by id",
            description = "The method updates of an existing car record. It "
                    + "takes the car's unique identifier (id) as a path variable "
                    + "and the updated car details as a request body in the form "
                    + "of a validated CarRequestDto. The method then maps the "
                    + "requestDto to a Car object using the carMapper and calls "
                    + "the carService to update the corresponding car record in the "
                    + "system. Finally, it maps the updated Car object to its "
                    + "corresponding response DTO (CarResponseDto) using the "
                    + "carMapper and returns it.")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = Car.class),
                            mediaType = "application/json")})
    })
    @PutMapping("/{id}")
    public CarResponseDto update(@Parameter(
            description = "Press the button \"Try it out\" and put id to update the car")
                                     @PathVariable Long id,
                                 @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                         description = "Here you need to specify "
                                                 + "the fields that need to be "
                                                 + "updated in json format")
                                 @RequestBody @Valid CarRequestDto requestDto) {
        Car car = carMapper.mapToModel(requestDto);
        return carMapper.mapToDto(carService.update(id, car));
    }

    @Operation(summary = "Delete car by id",
            description = "The method deletes of a specific car record. It takes the"
                    + " car's unique identifier (id) as a path variable and calls the "
                    + "carService to delete the corresponding car from the system. The "
                    + "method does not return any data, as it is meant to perform the "
                    + "deletion operation without providing a response body.")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = Car.class),
                            mediaType = "application/json")})
    })
    @DeleteMapping("/{id}")
    public void delete(@Parameter(
            description = "Press the button \"Try it out\" and put id to delete the car")
                           @PathVariable Long id) {
        carService.delete(id);
    }
}
