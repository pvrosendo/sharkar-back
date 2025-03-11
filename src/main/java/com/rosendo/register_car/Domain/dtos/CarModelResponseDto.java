package com.rosendo.register_car.Domain.dtos;

import com.rosendo.register_car.Domain.models.CarBrandsEnum;
import com.rosendo.register_car.Domain.models.CarTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record CarModelResponseDto(
        @NotNull CarBrandsEnum brand,
        @NotBlank String model,
        @NotBlank String year,
        @NotNull Double price,
        @NotNull String displacement,
        @NotNull CarTypeEnum carType,
        @NotNull Date registerDate
) { }
