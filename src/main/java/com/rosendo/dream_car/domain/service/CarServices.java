package com.rosendo.dream_car.domain.service;

import com.rosendo.dream_car.domain.dto.CarModelRequestDto;
import com.rosendo.dream_car.domain.dto.CarModelResponseDto;
import com.rosendo.dream_car.domain.model.CarModel;
import com.rosendo.dream_car.domain.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CarServices {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserService userService;


    public CarModel registerCar(CarModelRequestDto carModelRequestDto, String username){

        var carModel = new CarModel();

        carModel.setUserId(userService.findUserByUsername(username).getId());
        carModel.setModel(carModelRequestDto.model());
        carModel.setBrandName(carModelRequestDto.brand());
        carModel.setModelYear(carModelRequestDto.year());
        carModel.setFuel(carModelRequestDto.fuel());
        carModel.setPrice(carModelRequestDto.price());
        carModel.setReferenceMonth(carModelRequestDto.referenceMonth());

        return carRepository.save(carModel);
    }

    public List<CarModelResponseDto> getAllCars(String username){
        Long userId = userService.findUserByUsername(username).getId();

        var carList = carRepository.findAllByUserId(userId);

        List<CarModelResponseDto> carModelResponseDto = new ArrayList<>();

        carList.forEach(x -> carModelResponseDto.add(new CarModelResponseDto(
                x.getId(),
                x.getModel(),
                x.getBrandName(),
                x.getModelYear(),
                x.getFuel(),
                x.getPrice(),
                x.getReferenceMonth()))
        );
        return carModelResponseDto.isEmpty() ? null : carModelResponseDto;
    }

    public void deleteCar(String id){
        carRepository.deleteById(Long.parseLong(id));
    }


}
