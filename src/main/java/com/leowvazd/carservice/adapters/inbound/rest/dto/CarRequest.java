package com.leowvazd.carservice.adapters.inbound.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CarRequest(
        @Schema(example = "Preto") @NotBlank String color,
        @Schema(example = "Honda") @NotBlank String brand,
        @Schema(example = "Civic") @NotBlank String model,
        @Schema(example = "2024") @NotNull @Min(1886) @Max(2100) Integer year) {
}
