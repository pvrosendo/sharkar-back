package com.rosendo.register_car.application;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rosendo.register_car.domain.model.FipeCarModels;
import com.rosendo.register_car.domain.model.FipeInfoModel;
import com.rosendo.register_car.domain.model.FipeYearModels;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class ApiFipeServices {

    public HttpResponse<String> buildGetRequest(String url) {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = null;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response;

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public FipeInfoModel mapJsonToFipeInfoModel(HttpResponse<String> response) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(response.body(), FipeInfoModel.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<FipeYearModels> mapJsonToFipeYearModels(HttpResponse<String> response) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(response.body(), new TypeReference<List<FipeYearModels>>(){});
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<FipeCarModels> mapJsonToFipeCarModels(HttpResponse<String> response, Integer brandId) {
        ObjectMapper mapper = new ObjectMapper();
        try {

            List<FipeCarModels> modelsList = mapper.readValue(response.body(), new TypeReference<List<FipeCarModels>>(){});

            for (FipeCarModels fipeCarModels : modelsList) {
                fipeCarModels.setBrandId(brandId);
            }
            return modelsList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
