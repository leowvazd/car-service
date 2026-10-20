package com.leowvazd.carservice.adapters.inbound.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record CarResponse(
        @Schema(example = "1") Long id,
        @Schema(example = "Prata") String color,
        @Schema(example = "Toyota") String brand,
        @Schema(example = "Corolla") String model,
        @Schema(example = "2022") Integer year) {
}
