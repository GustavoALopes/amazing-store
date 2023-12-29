package com.developerjorney.application.base.controllers;

import com.developerjorney.application.base.dtos.DefaultResponse;
import com.developerjorney.core.patterns.notification.interfaces.INotification;
import com.developerjorney.core.patterns.notification.interfaces.INotificationSubscriber;

import java.util.Objects;
import java.util.Set;

public abstract class BaseController {

    private final INotificationSubscriber subscriber;

    public BaseController(final INotificationSubscriber subscriber) {
        this.subscriber = subscriber;
    }

    public Set<INotification> getNotifications() {
        return this.subscriber.getNotifications();
    }

    public DefaultResponse<String> getNotificationsResponse() {
        return DefaultResponse.create(
                "Something wrong happen",
                this.subscriber.getNotifications()
        );
    }

    protected boolean checkIfCodeExists(final String code) {
        return this.getNotifications().stream().filter(notification -> {
            return Objects.equals(notification.code(), code);
        })
                .findFirst()
                .map(value -> true)
                .orElse(false);
    }
}
