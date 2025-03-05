package com.example.e_commerce.ecommerce.infra.finan.config.validation.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record CodeDTO(@Valid @NotNull String code) {
}
