package com.developerjorney.domain.base.entities.valueobjects;

import com.developerjorney.core.patterns.validation.enums.ValidateTypeEnum;
import com.developerjorney.core.patterns.validation.models.ValidateMessage;
import com.developerjorney.core.patterns.validation.models.ValidateResult;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Getter
public class InfoValidateResultVO {

    private final Collection<ValidateMessage> messages;


    public InfoValidateResultVO() {
        this.messages = new ArrayList<>();
    }

    public InfoValidateResultVO(final Collection<ValidateMessage> messages) {
        this.messages = messages;
    }

    public void addMessage(final ValidateMessage message) {
        this.messages.add(message);
    }

    public void addValidateResult(final ValidateResult result) {
        this.messages.addAll(result.getMessages());
    }

    public boolean isValid() {
        return this.messages.stream()
                .map(message -> {
                    return Objects.equals(message.getType(), ValidateTypeEnum.ERROR) ||
                            Objects.equals(message.getType(), ValidateTypeEnum.WARNING);
                })
                .findFirst()
                .map(value -> false)
                .orElse(true);
    }

    public InfoValidateResultVO deepClone() {
        return new InfoValidateResultVO(this.messages);
    }
}
