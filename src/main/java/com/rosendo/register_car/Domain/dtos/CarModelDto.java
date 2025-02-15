package com.rosendo.register_car.Domain.dtos;

import com.rosendo.register_car.Domain.models.CarBrandsEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CarModelDto(
        @NotNull CarBrandsEnum brand,
        @NotBlank String model,
        @NotBlank String year,
        @NotNull Double price,
        @NotBlank String registerDate
) {
}
