package com.developerjorney.application.base.dtos;

import com.developerjorney.core.patterns.notification.interfaces.INotification;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
public class DefaultResponse<T> implements Serializable {

    public static final long serialVersionUID = 1L;

    protected final T data;

    protected final Set<INotification> messages;

    public DefaultResponse() {
        this.data = null;
        this.messages = new HashSet<>();
    }

    protected DefaultResponse(
            final T data,
            final Set<INotification> messages
    ) {
        this.data = data;
        this.messages = messages;
    }

    public void addMessage(final INotification message) {
        this.messages.add(message);
    }

    public static <T> DefaultResponse<T> create(
        final T data
    ) {
        return new DefaultResponse<>(
                data,
                new HashSet<>()
        );
    }

    public static <T> DefaultResponse<T> create(
            final T data,
            final Set<INotification> notifications
    ) {
        return new DefaultResponse<>(
                data,
                notifications
        );
    }
}
