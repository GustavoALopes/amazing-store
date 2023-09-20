package com.developerjorney.core.patterns.validation.models;

import com.developerjorney.core.patterns.validation.enums.ValidateTypeEnum;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class ValidateMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private final ValidateTypeEnum type;

    private final String code;

    private final String description;

    private ValidateMessage(
        final ValidateTypeEnum type,
        final String code,
        final String description
    ) {
        this.type = type;
        this.code = code;
        this.description = description;
    }


    public static ValidateMessage create(
        final ValidateTypeEnum type,
        final String code,
        final String description
    ) {
        return new ValidateMessage(
                type,
                code,
                description
        );
    }


}
