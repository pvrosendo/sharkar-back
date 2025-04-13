package com.rosendo.register_car.Domain.dtos;

import com.rosendo.register_car.Domain.models.CarBrandsEnum;
import com.rosendo.register_car.Domain.models.CarTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CarModelDto(
        @NotNull CarBrandsEnum brand,
        @NotBlank String model,
        @NotNull Integer year,
        @NotNull Double price,
        @NotBlank String displacement,
        @NotNull CarTypeEnum carType
) { }
