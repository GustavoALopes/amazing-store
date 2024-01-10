package com.developerjorney.domain.client.entities.validations;

import com.developerjorney.core.patterns.validation.BaseValidation;
import com.developerjorney.core.patterns.validation.enums.ValidateTypeEnum;
import com.developerjorney.core.patterns.validation.models.ValidateMessage;
import com.developerjorney.domain.client.entities.inputs.ImportClientDomainInput;

import java.util.Objects;

public class ImportClientDomainInputValidation extends BaseValidation<ImportClientDomainInput> {

    public static final String SUFFIX = "IMPORT_CLIENT";

    public static final String NAME_IS_REQUIRED = SUFFIX + "_NAME_IS_REQUIRED";

    public static final String NAME_IS_BIGGEST_THAN_MAX_SIZE = SUFFIX + "_NAME_IS_BIGGEST_THAN_MAX_SIZE";

    public static final String LAST_NAME_IS_REQUIRED = SUFFIX + "_LAST_NAME_IS_REQUIRED";

    public static final String LAST_NAME_IS_BIGGEST_THAN_MAX_SIZE = SUFFIX + "_LAST_NAME_IS_BIGGEST_THAN_MAX_SIZE";

    public static final String BIRTHDATE_IS_REQUIRED = SUFFIX + "_BIRTHDATE_IS_REQUIRED";

    public static final String EMAIL_IS_INVALID = SUFFIX + "_EMAIL_IS_INVALID";

    public static final String EMAIL_IS_BIGGEST_THAN_MAX_SIZE = SUFFIX + "_EMAIL_IS_BIGGEST_THAN_MAX_SIZE";

    @Override
    public void internalValidate(final ImportClientDomainInput input) {
        if(Objects.isNull(input.getName()) || input.getName().isEmpty()) {
            this.addMessage(ValidateMessage.create(
                    ValidateTypeEnum.ERROR,
                    NAME_IS_REQUIRED,
                    "Nome obrigatório"
            ));
        }

        if (Objects.nonNull(input.getName()) && input.getName().length() > 100) {
            this.addMessage(ValidateMessage.create(
                    ValidateTypeEnum.ERROR,
                    NAME_IS_BIGGEST_THAN_MAX_SIZE,
                    "O tamanho máximo para nome é 100 caracteres"
            ));
        }

        if(Objects.isNull(input.getLastName()) || input.getLastName().isEmpty()) {
            this.addMessage(ValidateMessage.create(
                    ValidateTypeEnum.ERROR,
                    LAST_NAME_IS_REQUIRED,
                    "Sobrenome obrigatório"
            ));
        }

        if (Objects.nonNull(input.getLastName()) && input.getLastName().length() > 100) {
            this.addMessage(ValidateMessage.create(
                    ValidateTypeEnum.ERROR,
                    LAST_NAME_IS_BIGGEST_THAN_MAX_SIZE,
                    "O tamanho máximo de sobrenome é de 100 caracteres"
            ));
        }

        if(Objects.isNull(input.getBirthdate())) {
            this.addMessage(ValidateMessage.create(
                    ValidateTypeEnum.ERROR,
                    BIRTHDATE_IS_REQUIRED,
                    "Data de nascimento obrigatória"
            ));
        }

        if(!input.getEmail().isValid()) {
            this.addMessage(ValidateMessage.create(
                    ValidateTypeEnum.ERROR,
                    EMAIL_IS_INVALID,
                    "Email esta invalido"
            ));
        }

        if (input.getEmail().getValue().length() > 100) {
            this.addMessage(ValidateMessage.create(
                    ValidateTypeEnum.ERROR,
                    EMAIL_IS_BIGGEST_THAN_MAX_SIZE,
                    "O tamanho máximo do email é de 100 caracteres"
            ));
        }
    }
}
