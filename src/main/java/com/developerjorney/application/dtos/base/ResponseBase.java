package com.developerjorney.application.dtos.base;

import com.developerjorney.core.patterns.notification.interfaces.INotification;
import lombok.Getter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
public abstract class ResponseBase<T> implements Serializable {

    public final long serialVersionUID = 1L;

    protected final T data;

    protected final Set<INotification> messages;

    public ResponseBase() {
        this.data = null;
        this.messages = new HashSet<>();
    }

    protected ResponseBase(
            final T data,
            final Set<INotification> messages
    ) {
        this.data = data;
        this.messages = messages;
    }

    public void addMessage(final INotification message) {
        this.messages.add(message);
    }
}
