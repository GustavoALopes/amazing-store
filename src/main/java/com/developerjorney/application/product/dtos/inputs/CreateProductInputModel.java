package com.developerjorney.application.product.dtos.inputs;

import lombok.Getter;

@Getter
public record CreateProductInputModel(String code, String description) {
}
