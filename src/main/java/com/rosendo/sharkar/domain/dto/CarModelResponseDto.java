package com.rosendo.sharkar.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CarModelResponseDto(
        @NotNull Long id,
        @NotBlank String model,
        @NotBlank String brand,
        @NotBlank String year,
        @NotBlank String fuel,
        @NotBlank String price,
        @NotBlank String referenceMonth
) { }
