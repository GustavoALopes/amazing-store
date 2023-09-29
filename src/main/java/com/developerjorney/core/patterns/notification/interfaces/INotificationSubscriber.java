package com.developerjorney.core.patterns.notification.interfaces;

import com.developerjorney.core.persistence.observable.interfaces.ISubscriber;

import java.util.Set;

public interface INotificationSubscriber extends ISubscriber<INotification> {

    Set<INotification> getNotifications();
}
