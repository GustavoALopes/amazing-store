package com.developerjorney.core.patterns.notification;

import com.developerjorney.core.patterns.notification.interfaces.INotification;
import com.developerjorney.core.patterns.notification.interfaces.INotificationSubscriber;
import com.developerjorney.core.persistence.observable.interfaces.ISubscriber;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
@RequestScope
public class NotificationSubscriber implements INotificationSubscriber {

    private final Set<INotification> notifications;

    public NotificationSubscriber() {
        this.notifications = new HashSet<>();
    }

    @Override
    public void update(final INotification subject) {
        this.notifications.add(subject);
    }

    public Set<INotification> getNotifications() {
        return Collections.unmodifiableSet(this.notifications);
    }

    @Override
    public boolean hasNotification() {
        return !this.notifications.isEmpty();
    }
}
