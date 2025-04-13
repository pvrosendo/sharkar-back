package com.rosendo.register_car.Domain.services;

import com.rosendo.register_car.Domain.dtos.CarModelDto;
import com.rosendo.register_car.Domain.models.UserCarModel;
import com.rosendo.register_car.Domain.repositories.UserCarRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


@Service
public class CarServices {


    @Autowired
    private UserCarRepository userCarRepository;



    public List<UserCarModel> getAllCars() { return userCarRepository.findAll(); }

    public Optional<UserCarModel> getCarById(Long carId) {
        return userCarRepository.findById(carId);
    }

    public UserCarModel registerCar(CarModelDto carModelDto){
        //TODO: verification of exists model/year

        UserCarModel userCarModel = new UserCarModel();
        BeanUtils.copyProperties(carModelDto, userCarModel);

        userCarModel.setRegisterDate(
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        return userCarRepository.save(userCarModel);
    }

    public UserCarModel updateCar(Long id, CarModelDto carModelDto){
        Optional<UserCarModel> carModel = userCarRepository.findById(id);

        carModel.orElseThrow().setBrand(carModelDto.brand());
        carModel.orElseThrow().setModel(carModelDto.model());
        carModel.orElseThrow().setYear(carModelDto.year());
        carModel.orElseThrow().setPrice(carModelDto.price());
        carModel.orElseThrow().setDisplacement(carModelDto.displacement());
        carModel.orElseThrow().setCarType(carModelDto.carType());

        carModel.orElseThrow()
                .setRegisterDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        return userCarRepository.save(carModel.get());
    }

    public void deleteCar(Long id){
        userCarRepository.deleteById(id);
    }

}
