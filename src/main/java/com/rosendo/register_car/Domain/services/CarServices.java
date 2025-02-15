package com.rosendo.register_car.Domain.services;

import com.rosendo.register_car.Domain.dtos.CarModelDto;
import com.rosendo.register_car.Domain.models.CarModels;
import com.rosendo.register_car.Domain.repositories.CarRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CarServices {


    @Autowired
    private CarRepository carRepository;

    public List<CarModels> getAllCars() { return carRepository.findAll(); }

    public CarModels registerCar(CarModelDto carModelDto){
        //TODO: verification of exists model/year
        CarModels carModels = new CarModels();
        BeanUtils.copyProperties(carModelDto, carModels);
        carRepository.save(carModels);
        return carModels;
    }

}
