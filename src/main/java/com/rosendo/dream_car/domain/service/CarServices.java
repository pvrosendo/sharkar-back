package com.rosendo.dream_car.domain.service;

import com.rosendo.dream_car.domain.dto.CarModelDto;
import com.rosendo.dream_car.domain.model.CarModel;
import com.rosendo.dream_car.domain.repository.CarRepository;
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
    private CarRepository carRepository;

    public List<CarModel> getAllCars() { return carRepository.findAll(); }

    public Optional<CarModel> getCarById(Long carId) {
        return carRepository.findById(carId);
    }

    public CarModel registerCar(CarModelDto carModelDto){
        //TODO: verification of exists model/year

        CarModel carModel = new CarModel();
        BeanUtils.copyProperties(carModelDto, carModel);

        carModel.setRegisterDate(
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        return carRepository.save(carModel);
    }

    public CarModel updateCar(Long id, CarModelDto carModelDto){
        Optional<CarModel> carModel = carRepository.findById(id);

        carModel.orElseThrow().setBrand(carModelDto.brand());
        carModel.orElseThrow().setModel(carModelDto.model());
        carModel.orElseThrow().setYear(carModelDto.year());
        carModel.orElseThrow().setPrice(carModelDto.price());
        carModel.orElseThrow().setDisplacement(carModelDto.displacement());
        carModel.orElseThrow().setCarType(carModelDto.carType());

        carModel.orElseThrow()
                .setRegisterDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        return carRepository.save(carModel.get());
    }

    public void deleteCar(Long id){
        carRepository.deleteById(id);
    }

}
