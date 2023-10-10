package com.developerjorney.application.product.dtos.inputs;

import io.swagger.v3.oas.annotations.media.Schema;

public record CreateProductInputModel(
        @Schema(
                description = "The product code like SKU",
                example = "8456789"
        )
        String code,
        @Schema(
                description = "A short description about product",
                example = "Black T-Shirt"
        )
        String description

) {}
