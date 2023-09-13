package com.developerjorney.application.dtos.base;

import com.developerjorney.core.patterns.notification.interfaces.INotification;

import java.util.HashSet;
import java.util.Set;

public class ResponseViewModel<T> {

    private T data;

    private Set<INotification> messages;

    private ResponseViewModel(
            final T data,
            final Set<INotification> messages
    ) {
        this.data = data;
        this.messages = messages;
    }

    public void addMessage(final INotification message) {
        this.messages.add(message);
    }

    public static <T> ResponseViewModel<T> create(final T data) {
        return new ResponseViewModel(
                data,
                new HashSet<>()
        );
    }

    public static <T> ResponseViewModel<T> create(
            final T data,
            final Set<INotification> messages
    ) {
        return new ResponseViewModel<>(
                data,
                messages
        );
    }
}
