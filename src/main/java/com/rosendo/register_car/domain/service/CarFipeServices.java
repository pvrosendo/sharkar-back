package com.rosendo.register_car.domain.service;

import com.rosendo.register_car.application.service.ApiFipeServices;
import com.rosendo.register_car.domain.enums.CarBrandsEnum;
import com.rosendo.register_car.domain.model.FipeBrandModels;
import com.rosendo.register_car.domain.model.FipeInfoModel;
import com.rosendo.register_car.domain.model.FipeCarModels;
import com.rosendo.register_car.domain.model.FipeYearModels;
import com.rosendo.register_car.domain.repository.FipeBrandRepository;
import com.rosendo.register_car.domain.repository.FipeCarRepository;
import com.rosendo.register_car.domain.repository.FipeYearRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.rosendo.register_car.config.WebConfig.BASE_URL_FIPE;

@Service
public class CarFipeServices {

    @Autowired
    private FipeBrandRepository fipeBrandRepository;

    @Autowired
    private FipeCarRepository fipeCarRepository;

    @Autowired
    private FipeYearRepository fipeYearRepository;

    @Autowired
    private ApiFipeServices apiServices;

    private final List<Integer> carsBrandList = List.of(
            6, 7, 13, 17, 20, 21, 23, 25, 26, 28, 29, 31, 33, 34, 39, 41, 43, 44, 47, 48, 54,
            56, 58, 59, 171, 177, 185, 211, 238, 240);


    public List<FipeCarModels> getAllModelsByBrandId(Integer brandId){

        if (carsBrandList.contains(brandId)) {
            return fipeCarRepository.getByBrandId(brandId);
        }
        var response = apiServices.buildGetRequest(BASE_URL_FIPE + brandId + "/models/");

        return apiServices.mapJsonToFipeCarModels(response, brandId);
    }

    public List<FipeYearModels> getCarYearsById(Integer brandId, Integer modelId) {
        if (fipeYearRepository.getByModelId(modelId).isEmpty()) {
            var response = apiServices.buildGetRequest(BASE_URL_FIPE + brandId + "/models/" + modelId + "/years");
            apiServices.mapJsonToFipeYearModels(response);
        }
        return fipeYearRepository.getByModelId(modelId);
    }

    public FipeInfoModel getInfoFipeCar(Integer brandId, Integer modelId, String yearId) {

        var response = apiServices.buildGetRequest(BASE_URL_FIPE + brandId + "/models/" + modelId + "/years/" + yearId);
        return apiServices.mapJsonToFipeInfoModel(response);

    }

    public List<FipeBrandModels> getAllBrands() {
        return fipeBrandRepository.findAll();
    }

    public FipeBrandModels getBrandById(Integer id) {
        return fipeBrandRepository.getByBrandId(id);
    }
}
