package com.developerjorney.core.persistence.notifications;

import com.developerjorney.core.persistence.notifications.interfaces.INotificationPublisher;
import com.developerjorney.core.persistence.observable.interfaces.BasePublisher;
import com.developerjorney.core.persistence.observable.interfaces.ISubscriber;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class NotificationPublisherInMemory extends BasePublisher implements INotificationPublisher {

    public final ApplicationContext cdi;

    public NotificationPublisherInMemory(final ApplicationContext cdi) {
        this.cdi = cdi;
    }

    @Override
    protected <TSubject> ISubscriber<TSubject> instanceSubscriber(final Class<TSubject> subscriber) {
        final var instance = this.cdi.getBean(subscriber);
        if(Objects.isNull(instance)) {
            throw new RuntimeException(String.format("Cannot instance %s subscriber", subscriber.getName()));
        }

        return (ISubscriber<TSubject>) instance;
    }
}
