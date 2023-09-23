package com.developerjorney.core.patterns.notification.interfaces;

import com.developerjorney.core.patterns.notification.enums.NotificationTypeEnum;

public interface INotification {
    NotificationTypeEnum type();

    String code();

    String message();
}
