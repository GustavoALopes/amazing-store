package com.developerjorney.domain.entities.product;

import com.developerjorney.domain.entities.base.BaseEntity;
import com.developerjorney.domain.entities.product.inputs.CreateProductDomainInput;
import com.developerjorney.domain.entities.product.validations.CreateProductDomainInputValidation;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;
import java.util.concurrent.locks.StampedLock;

@Entity
@Getter
public class Product extends BaseEntity {

    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;

    private final CreateProductDomainInputValidation createProductDomainInputValidation;

    public Product() {
        this.code = "";
        this.description = "";

        this.createProductDomainInputValidation = new CreateProductDomainInputValidation();
    }

    public boolean create(final CreateProductDomainInput input) {
        if(!this.valid(() -> this.createProductDomainInputValidation.validate(input))) {
            return false;
        }

        this.<Product>createNew(input.getCreatedBy())
        .create(input.getCode())
        .addProductExtraInfo(input.getDescription());

        return true;
    }

    private Product create(final String code) {
        this.code = code;
        return this;
    }

    private Product addProductExtraInfo(final String description) {
        this.description = description;
        return this;
    }
}
