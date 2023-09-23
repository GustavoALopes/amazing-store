package com.developerjorney.configurations;

import com.developerjorney.core.patterns.notification.NotificationSubscriber;
import com.developerjorney.core.patterns.notification.interfaces.INotification;
import com.developerjorney.core.patterns.notification.interfaces.INotificationPublisher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationPublisherConfig implements ApplicationRunner {

    private final INotificationPublisher notificationPublisher;

    public NotificationPublisherConfig(final INotificationPublisher notificationPublisher) {
        this.notificationPublisher = notificationPublisher;
    }

    @Override
    public void run(final ApplicationArguments args) {
        this.notificationPublisher.subscriber(
                NotificationSubscriber.class,
                INotification.class
        );
    }
}
