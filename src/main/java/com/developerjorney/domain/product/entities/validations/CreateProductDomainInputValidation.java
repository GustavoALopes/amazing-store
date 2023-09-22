package com.developerjorney.domain.product.entities.validations;

import com.developerjorney.core.patterns.validation.BaseValidation;
import com.developerjorney.core.patterns.validation.enums.ValidateTypeEnum;
import com.developerjorney.core.patterns.validation.models.ValidateMessage;
import com.developerjorney.domain.product.entities.inputs.CreateProductDomainInput;

import java.util.Objects;

public class CreateProductDomainInputValidation extends BaseValidation<CreateProductDomainInput> {

    private static final String SUFFIX = "CREATE_PRODUCT_";

    @Override
    public void internalValidate(final CreateProductDomainInput input) {
        if(Objects.isNull(input.getCode()) || input.getCode().isEmpty()) {
            this.addMessage(ValidateMessage.create(
                    ValidateTypeEnum.ERROR,
                    SUFFIX + "CODE_IS_REQUIRED",
                    "The code is required!"
            ));
        }
    }
}
