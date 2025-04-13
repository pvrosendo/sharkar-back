package com.rosendo.register_car.Controllers;

import com.rosendo.register_car.Domain.models.BrandModels;
import com.rosendo.register_car.Domain.services.CarInfoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/dream-car/brands")
public class CarInfoControllers {

    @Autowired
    private CarInfoServices carInfoServices;

    @GetMapping(value = "/brands", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BrandModels>> getAllBrands(){
        return ResponseEntity.status(HttpStatus.OK).body(carInfoServices.getAllBrands());
    }



}
