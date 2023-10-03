package com.developerjorney.domain.client.entities.validations;

import com.developerjorney.core.patterns.validation.BaseValidation;
import com.developerjorney.core.patterns.validation.enums.ValidateTypeEnum;
import com.developerjorney.core.patterns.validation.models.ValidateMessage;
import com.developerjorney.domain.client.entities.inputs.ImportClientDomainInput;

import java.util.Objects;

public class ImportClientDomainInputValidation extends BaseValidation<ImportClientDomainInput> {

    public static final String SUFFIX = "IMPORT_CLIENT";

    public static final String NAME_IS_REQUIRED = SUFFIX + "_NAME_IS_REQUIRED";

    public static final String LAST_NAME_IS_REQUIRED = SUFFIX + "_LAST_NAME_IS_REQUIRED";

    public static final String BIRTHDATE_IS_REQUIRED = SUFFIX + "_BIRTHDATE_IS_REQUIRED";

    @Override
    public void internalValidate(final ImportClientDomainInput input) {
        if(Objects.isNull(input.getName()) || input.getName().isEmpty()) {
            this.addMessage(ValidateMessage.create(
                    ValidateTypeEnum.ERROR,
                    NAME_IS_REQUIRED,
                    "Nome obrigatório"
            ));
        }

        if(Objects.isNull(input.getLastName()) || input.getLastName().isEmpty()) {
            this.addMessage(ValidateMessage.create(
                    ValidateTypeEnum.ERROR,
                    LAST_NAME_IS_REQUIRED,
                    "Sobrenome obrigatório"
            ));
        }

        if(Objects.isNull(input.getBirthdate())) {
            this.addMessage(ValidateMessage.create(
                    ValidateTypeEnum.ERROR,
                    BIRTHDATE_IS_REQUIRED,
                    "Data de nascimento obrigatória"
            ));
        }
    }
}
