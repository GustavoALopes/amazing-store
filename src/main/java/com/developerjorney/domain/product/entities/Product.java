package com.developerjorney.domain.product.entities;

import com.developerjorney.domain.entities.base.BaseEntity;
import com.developerjorney.domain.entities.base.interfaces.IAggregateRoot;
import com.developerjorney.domain.product.entities.inputs.CreateProductDomainInput;
import com.developerjorney.domain.product.entities.validations.CreateProductDomainInputValidation;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import lombok.Getter;

@Entity
@Getter
public class Product extends BaseEntity implements IAggregateRoot {

    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;

    @Transient
    private final transient CreateProductDomainInputValidation createProductDomainInputValidation;

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
