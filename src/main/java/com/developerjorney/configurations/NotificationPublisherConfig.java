package com.developerjorney.configurations;

import com.developerjorney.core.patterns.notification.NotificationSubscriber;
import com.developerjorney.core.patterns.notification.interfaces.INotification;
import com.developerjorney.core.patterns.notification.interfaces.INotificationPublisher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationPublisherConfig {

    private final INotificationPublisher notificationPublisher;

    public NotificationPublisherConfig(final INotificationPublisher notificationPublisher) {
        this.notificationPublisher = notificationPublisher;
    }

    @Bean
    public void run() {
        this.notificationPublisher.subscriber(
            NotificationSubscriber.class,
            INotification.class
        );
    }
}
