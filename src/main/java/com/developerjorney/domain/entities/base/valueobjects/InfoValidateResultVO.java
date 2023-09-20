package com.developerjorney.domain.entities.base.valueobjects;

import com.developerjorney.core.patterns.validation.enums.ValidateTypeEnum;
import com.developerjorney.core.patterns.validation.models.ValidateMessage;
import com.developerjorney.core.patterns.validation.models.ValidateResult;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Getter
public class InfoValidateResultVO {

    private final Collection<ValidateMessage> message;


    public InfoValidateResultVO() {
        this.message = new ArrayList<>();
    }

    public void addMessage(final ValidateMessage message) {
        this.message.add(message);
    }

    public void addValidateResult(final ValidateResult result) {
        this.message.addAll(result.getMessages());
    }

    public boolean isValid() {
        return this.message.stream()
                .map(message -> {
                    return Objects.equals(message.getType(), ValidateTypeEnum.ERROR) ||
                            Objects.equals(message.getType(), ValidateTypeEnum.WARNING);
                })
                .findFirst()
                .map(value -> false)
                .orElse(true);
    }
}
