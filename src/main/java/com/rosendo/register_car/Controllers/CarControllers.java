package com.rosendo.register_car.Controllers;

import com.rosendo.register_car.Domain.dtos.CarModelDto;
import com.rosendo.register_car.Domain.models.CarModels;
import com.rosendo.register_car.Domain.services.CarServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/register-car")
public class CarControllers {

    @Autowired
    private CarServices carServices;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CarModels>> getAllCars() {
        return ResponseEntity.status(HttpStatus.OK).body(carServices.getAllCars());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody @Valid CarModelDto carModelDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(carServices.registerCar(carModelDto));
    }
}
