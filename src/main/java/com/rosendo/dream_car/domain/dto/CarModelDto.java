package com.rosendo.dream_car.domain.dto;

import com.rosendo.dream_car.domain.enums.CarBrandsEnum;
import com.rosendo.dream_car.domain.enums.CarTypeEnum;
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
