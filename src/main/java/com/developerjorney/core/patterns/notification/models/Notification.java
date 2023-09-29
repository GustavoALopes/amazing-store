package com.developerjorney.core.patterns.notification.models;

import com.developerjorney.core.patterns.notification.enums.NotificationTypeEnum;
import com.developerjorney.core.patterns.notification.interfaces.INotification;

public record Notification(NotificationTypeEnum type, String code, String message) implements INotification {

}
