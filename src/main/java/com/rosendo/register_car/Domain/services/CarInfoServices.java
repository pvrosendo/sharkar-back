package com.rosendo.register_car.Domain.services;

import com.rosendo.register_car.Domain.models.BrandModels;
import com.rosendo.register_car.Domain.models.CarBrandsEnum;
import com.rosendo.register_car.Domain.repositories.BrandRepository;
import com.rosendo.register_car.Domain.repositories.CarModelsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class CarInfoServices {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CarModelsRepository carModelsRepository;

    private CarBrandsEnum carBrandsEnum;

    private final static String BASE_URL_FIPE = "https://fipe.parallelum.com.br/api/v2/cars/brands/";

    public List<BrandModels> getAllBrands() {
        return brandRepository.findAll();
    }

    public Object getAllModelsByBrandId(Long brandId){

        if (carBrandsEnum.getCarsBrandList().contains(brandId.intValue())) {
            return carModelsRepository.getByBrandId(brandId);
        }

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://fipe.parallelum.com.br/api/v2/cars/brands/" + brandId + "/models"))
                .build();

        HttpResponse<String> response = null;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return response.body();

    }
}
