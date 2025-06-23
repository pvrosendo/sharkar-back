package com.rosendo.dream_car.domain.service;

import com.rosendo.dream_car.application.service.ApiFipeServices;
import com.rosendo.dream_car.domain.model.FipeBrandModels;
import com.rosendo.dream_car.domain.model.FipeInfoModel;
import com.rosendo.dream_car.domain.model.FipeCarModels;
import com.rosendo.dream_car.domain.model.FipeYearModels;
import com.rosendo.dream_car.domain.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.rosendo.dream_car.config.WebConfig.BASE_URL_FIPE;

@Service
public class CarFipeServices {

    @Autowired
    private FipeBrandRepository fipeBrandRepository;

    @Autowired
    private FipeCarRepository fipeCarRepository;

    @Autowired
    private FipeYearRepository fipeYearRepository;

    @Autowired
    private FipeInfoRepository fipeInfoRepository;

    @Autowired
    private ApiFipeServices apiServices;


    public List<FipeBrandModels> getAllBrands() {
        return fipeBrandRepository.findAll();
    }

    public List<FipeCarModels> getAllModelsByBrandId(Integer brandId){

        var carsList = fipeCarRepository.findAllByBrandId(brandId);

        if (carsList.isEmpty()) {
            var response = apiServices.buildGetRequest(BASE_URL_FIPE + brandId + "/models/");

            var model = apiServices.mapJsonToFipeCarModels(response, brandId);

            model.forEach(
                    x -> fipeCarRepository.save(
                            new FipeCarModels(
                                    brandId,
                                    x.getModelId(),
                                    x.getModelName()
                            )
                    )
            );
            return model;
        }
        return carsList;
    }

    public List<FipeYearModels> getCarYears(Integer brandId, Integer modelId) {
        var infoCar = fipeYearRepository.getByModelId(modelId);

        if (infoCar.isEmpty()) {
            var response = apiServices.buildGetRequest(BASE_URL_FIPE + brandId + "/models/" + modelId + "/years");
            var model = apiServices.mapJsonToFipeYearModels(response);

            model.forEach(
                    x -> fipeYearRepository.save(
                            new FipeYearModels(
                                    modelId,
                                    x.getModelName(),
                                    x.getYearId(),
                                    x.getYearValue()
                            )
                    )
            );
            return model;
        }
        return infoCar;
    }

    public FipeInfoModel getInfoFipeCar(Integer brandId, Integer modelId, String yearId) {

        var infoCar = fipeInfoRepository.findByModelIdAndBrandIdAndModelYear(modelId, brandId, yearId);

        if (infoCar == null){
            var response = apiServices.buildGetRequest(BASE_URL_FIPE + brandId + "/models/" + modelId + "/years/" + yearId);

            var model = apiServices.mapJsonToFipeInfoModel(response);

            var car = new FipeInfoModel(
                    brandId,
                    model.getBrandName(),
                    model.getCodeFipe(),
                    model.getFuel(),
                    model.getFuelAcronym(),
                    model.getModel(),
                    modelId,
                    yearId,
                    model.getPrice(),
                    model.getReferenceMonth(),
                    model.getVehicleType()
            );
            return fipeInfoRepository.save(car);
        }
        return infoCar;
    }

    public FipeInfoModel getUpdateCarInfo(String brand, String model, String year) {
        var brandId = fipeBrandRepository.findBrandIdByBrandName(brand);
        var modelId = fipeCarRepository.findModelIdByModelName(model);
        var yearId = fipeYearRepository.findYearIdByYearValue(year);

        var response = apiServices.buildGetRequest(BASE_URL_FIPE + brandId + "/models/" + modelId + "/years/" + yearId);

        return apiServices.mapJsonToFipeInfoModel(response);

    }
}
