package com.developerjorney.core.patterns.validation.interfaces;

import com.developerjorney.core.patterns.validation.models.ValidateResult;

public interface IValidation<T> {
    ValidateResult validate(final T input);
}
