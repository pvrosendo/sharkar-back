package com.rosendo.register_car.Domain.dtos;

import com.rosendo.register_car.Domain.models.CarBrandsEnum;

public record CarModelDto(
        CarBrandsEnum brand,
        String year,
        Double price
) {
}
