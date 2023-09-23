package com.developerjorney.domain.base.services;

import com.developerjorney.core.patterns.notification.enums.NotificationTypeEnum;
import com.developerjorney.core.patterns.notification.models.Notification;
import com.developerjorney.core.patterns.notification.interfaces.INotificationPublisher;
import com.developerjorney.domain.base.entities.BaseEntity;
import com.developerjorney.domain.base.entities.interfaces.IAggregateRoot;

public abstract class BaseService<T extends IAggregateRoot> {

    private final INotificationPublisher notificationPublisher;

    public BaseService(final INotificationPublisher notificationPublisher) {
        this.notificationPublisher = notificationPublisher;
    }

    protected boolean isValidOrNotify(final BaseEntity entity) {
        if(entity.isValid()) return true;
        
        entity.getInfoValidateResultVO().getMessages()
                .forEach(message -> {
                    this.notificationPublisher.publisher(new Notification(
                            NotificationTypeEnum.valueOf(message.getType().name()),
                            message.getCode(),
                            message.getDescription()
                    ));
                });

        return false;
    }
}
