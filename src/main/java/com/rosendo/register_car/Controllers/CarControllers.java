package com.rosendo.register_car.Controllers;

import com.rosendo.register_car.Domain.dtos.CarModelDto;
import com.rosendo.register_car.Domain.models.UserCarModel;
import com.rosendo.register_car.Domain.services.CarServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/dream-car")
public class CarControllers {

    @Autowired
    private CarServices carServices;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserCarModel>> getAllCars() {
        return ResponseEntity.status(HttpStatus.OK).body(carServices.getAllCars());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getCarById(@PathVariable(value = "id") Long carId){
        return ResponseEntity.status(HttpStatus.OK).body(carServices.getCarById(carId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerCar(@RequestBody @Valid CarModelDto carModelDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(carServices.registerCar(carModelDto));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateCar(
            @PathVariable(value = "id") Long carId,
            @RequestBody @Valid CarModelDto carModelDto)
    {
        return ResponseEntity.status(HttpStatus.OK).body(carServices.updateCar(carId, carModelDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCarById(@PathVariable(value = "id") Long userId) {
        carServices.deleteCar(userId);
        return ResponseEntity.noContent().build();
    }

}
