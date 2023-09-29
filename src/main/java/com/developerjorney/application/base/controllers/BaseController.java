package com.developerjorney.application.base.controllers;

import com.developerjorney.core.patterns.notification.interfaces.INotification;
import com.developerjorney.core.patterns.notification.interfaces.INotificationSubscriber;

import java.util.Collection;
import java.util.Set;

public abstract class BaseController {

    private final INotificationSubscriber subscriber;

    public BaseController(final INotificationSubscriber subscriber) {
        this.subscriber = subscriber;
    }

    public Set<INotification> getNotifications() {
        return this.subscriber.getNotifications();
    }
}
