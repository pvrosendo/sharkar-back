package com.rosendo.dream_car.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record CarModelRequestDto(
        @NotBlank String username,
        @NotBlank String model,
        @NotBlank String brand,
        @NotBlank String year,
        @NotBlank String fuel,
        @NotBlank String price,
        @NotBlank String referenceMonth
) { }
