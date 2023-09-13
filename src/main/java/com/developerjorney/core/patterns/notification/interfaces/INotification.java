package com.developerjorney.core.patterns.notification.interfaces;

import com.developerjorney.core.patterns.notification.enums.ValidationTypeEnum;

public interface INotification {
    ValidationTypeEnum getType();

    String getCode();

    String getMessage();
}
