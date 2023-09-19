package com.developerjorney.application.product.dtos.viewmodel;

import lombok.Getter;

@Getter
public class ProductViewModel {

    private final String code;

    private final String description;

    public ProductViewModel() {
        this.code = "";
        this.description = "";
    }

    public ProductViewModel(
        final String code,
        final String description
    ) {
        this.code = code;
        this.description = description;
    }

    public static ProductViewModel create(
            final String code,
            final String description
    ) {
        return new ProductViewModel(
                code,
                description
        );
    }
}
