package com.rosendo.sharkar.domain.service;

import com.rosendo.sharkar.domain.model.CarModel;
import com.rosendo.sharkar.domain.repository.CarRepository;
import com.rosendo.sharkar.domain.repository.FipeInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Component
@EnableScheduling
@Service
public class ScheduleService {

    @Autowired
    private CarServices carServices;

    @Autowired
    private CarFipeServices carFipeServices;

    @Autowired
    private FipeInfoRepository fipeInfoRepository;

    @Autowired
    private CarRepository carRepository;

    @Scheduled(cron = "0  0  0  1  *  ?", zone="\"America/Sao_Paulo\"")
    public void getUpdatePriceCar(){

        var listCars = carServices.findAllCars();

        for (CarModel carToUpdate : listCars){
            var carUpdated = carFipeServices.getUpdateCarInfo(carToUpdate.getBrandName(), carToUpdate.getModel(),
                    carToUpdate.getModelYear());

            if (Double.parseDouble(carUpdated.getPrice()) < Double.parseDouble(carToUpdate.getPrice())){
                // NOTIFY THE USER THAT PRICE IS OVER and UPDATE THE DATABASE
                
                carRepository.save(new CarModel(
                        carToUpdate.getUserId(),
                        carToUpdate.getModel(),
                        carToUpdate.getBrandName(),
                        carToUpdate.getModelYear(),
                        carToUpdate.getFuel(),
                        carUpdated.getPrice(),
                        carUpdated.getReferenceMonth()
                ));
            }
        }



    }



}
