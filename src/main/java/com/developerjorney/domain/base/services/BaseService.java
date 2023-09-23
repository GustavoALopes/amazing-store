package com.developerjorney.domain.base.services;

import com.developerjorney.core.persistence.notifications.interfaces.INotificationPublisher;
import com.developerjorney.domain.entities.base.BaseEntity;
import com.developerjorney.domain.entities.base.interfaces.IAggregateRoot;

public abstract class BaseService<T extends IAggregateRoot> {

    private final INotificationPublisher notificationPublisher;

    public BaseService(final INotificationPublisher notificationPublisher) {
        this.notificationPublisher = notificationPublisher;
    }

    protected boolean isValidOrNotify(final BaseEntity entity) {
        if(entity.isValid()) return true;

        entity.get
    }
}
