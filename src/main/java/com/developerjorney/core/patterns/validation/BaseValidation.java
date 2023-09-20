package com.developerjorney.core.patterns.validation;

import com.developerjorney.core.patterns.validation.interfaces.IValidation;
import com.developerjorney.core.patterns.validation.models.ValidateMessage;
import com.developerjorney.core.patterns.validation.models.ValidateResult;

import java.util.ArrayList;
import java.util.Collection;

public abstract class BaseValidation<T> implements IValidation<T> {

    private final Collection<ValidateMessage> messages;

    public BaseValidation() {
        this.messages = new ArrayList<>();
    }

    @Override
    public ValidateResult validate(final T input) {
        this.internalValidate(input);
        return new ValidateResult(this.messages);
    }

    public abstract void internalValidate(final T input);

    protected void addMessage(final ValidateMessage message) {
        this.messages.add(message);
    }
}
