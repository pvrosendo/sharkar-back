package com.rosendo.dream_car.presentation.controller;

import com.rosendo.dream_car.domain.dto.CarModelRequestDto;
import com.rosendo.dream_car.domain.dto.CarModelResponseDto;
import com.rosendo.dream_car.domain.service.CarServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/dream-car")
public class CarControllers {

    @Autowired
    private CarServices carServices;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerCar(@RequestBody @Valid CarModelRequestDto carModelRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(carServices.registerCar(carModelRequestDto, carModelRequestDto.username()));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CarModelResponseDto>> getAllCars(@RequestParam String username){
        return ResponseEntity.status(HttpStatus.OK).body(carServices.getAllCars(username));
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteCar(@RequestParam String id){
        carServices.deleteCar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
