package com.rosendo.sharkar.presentation.controller;

import com.rosendo.sharkar.domain.model.FipeBrandModels;
import com.rosendo.sharkar.domain.model.FipeInfoModel;
import com.rosendo.sharkar.domain.model.FipeCarModels;
import com.rosendo.sharkar.domain.model.FipeYearModels;
import com.rosendo.sharkar.domain.service.CarFipeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/sharkar/fipe")
public class CarFipeControllers {

    @Autowired
    private CarFipeServices carFipeServices;

    @GetMapping(value = "/brands", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FipeBrandModels>> getAllBrands(){
        return ResponseEntity.status(HttpStatus.OK).body(carFipeServices.getAllBrands());
    }

    @GetMapping(value = "/models", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FipeCarModels>> getAllModelsByBrand(@RequestParam Integer brandId){
        return ResponseEntity.status(HttpStatus.OK).body(carFipeServices.getAllModelsByBrandId(brandId));
    }

    @GetMapping(value = "/years", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FipeYearModels>> getAllYearsByBrandAndModel(@RequestParam Integer brandId,
                                                                    @RequestParam Integer modelId){
        return ResponseEntity.status(HttpStatus.OK).body(carFipeServices.getCarYears(brandId, modelId));
    }

    @GetMapping(value = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FipeInfoModel> getInfoFipe(@RequestParam Integer brandId,
                                                             @RequestParam Integer modelId,
                                                             @RequestParam String yearId){
        return ResponseEntity.status(HttpStatus.OK).body(carFipeServices.getInfoFipeCar(brandId, modelId, yearId));
    }

}
