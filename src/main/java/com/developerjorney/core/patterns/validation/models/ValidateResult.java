package com.developerjorney.core.patterns.validation.models;

import lombok.Getter;

import java.util.Collection;

@Getter
public class ValidateResult {

    private final Collection<ValidateMessage> messages;

    public ValidateResult(final Collection<ValidateMessage> messages) {
        this.messages = messages;
    }
}
