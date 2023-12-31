package com.developerjorney.application.base.dtos.views;

import com.developerjorney.application.base.dtos.DefaultResponse;
import com.developerjorney.core.patterns.notification.interfaces.INotification;
import lombok.Getter;

import java.util.Collections;
import java.util.Set;

@Getter
public class ErrorsNotificationsViewModel extends DefaultResponse<String> {

    public ErrorsNotificationsViewModel() {
        super("", Collections.emptySet());
    }

    public ErrorsNotificationsViewModel(
            final String message,
            final Set<INotification> notifications
    ) {
        super(message, notifications);
    }

    public static ErrorsNotificationsViewModel create(
        final Set<INotification> notifications
    ) {
        return new ErrorsNotificationsViewModel(
                "Some problems has be found when execute the use case!",
                notifications
        );
    }
}
