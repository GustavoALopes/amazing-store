package com.developerjorney.domain.entities.product.inputs;

import lombok.Getter;

@Getter
public class CreateProductDomainInput {

    private final String code;

    private final String description;

    private String createdBy;

    private CreateProductDomainInput(
        final String code,
        final String description,
        final String createdBy
    ) {
        this.code = code;
        this.description = description;
        this.createdBy = createdBy;
    }

    public static CreateProductDomainInput create(
            final String code,
            final String description,
            final String createdBy
    ) {
        return new CreateProductDomainInput(
                code,
                description,
                createdBy
        );
    }
}
