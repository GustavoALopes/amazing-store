package com.developerjorney.core.patterns.notification.models;

import com.developerjorney.core.patterns.notification.enums.NotificationTypeEnum;
import com.developerjorney.core.patterns.notification.interfaces.INotification;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record Notification(

        @NotNull
        @Schema(name = "type", description = "The type of error")
        NotificationTypeEnum type,

        @NotBlank
        @Schema(name = "code", description = "The error identifier")
        String code,

        @NotBlank
        @Schema(name = "message", description = "A description of error")
        String message

) implements INotification {}
