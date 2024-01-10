package com.developerjorney.application.exceptions;

import com.developerjorney.application.base.dtos.DefaultResponse;
import com.developerjorney.core.patterns.notification.enums.NotificationTypeEnum;
import com.developerjorney.core.patterns.notification.interfaces.INotification;
import com.developerjorney.core.patterns.notification.models.Notification;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DefaultResponse<Void>> methodArgumentNotValidHandler(
        final MethodArgumentNotValidException exception
    ) {
        final var messages = new HashSet<INotification>();
        for(final var error : exception.getFieldErrors()) {
            messages.add(new Notification(
                NotificationTypeEnum.ERROR,
                "INVALID_PAYLOAD",
                    "O parêmetro " + error.getField() + " " + error.getDefaultMessage()
            ));
        }

        return ResponseEntity.badRequest().body(DefaultResponse.create(null, messages));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<DefaultResponse<Void>> httpMessageNotReadableExceptionHandler(
            final HttpMessageNotReadableException exception
    ) {
        if(Objects.nonNull(exception.getCause())) {
            if(exception.getCause() instanceof InvalidFormatException cause) {

                return ResponseEntity.badRequest().body(DefaultResponse.create(
                        null,
                        Set.of(new Notification(
                                NotificationTypeEnum.ERROR,
                                "INVALID_PAYLOAD_VALUE",
                                "O parâmetro " + cause.getPath().get(0).getFieldName() + " tem um valor inválido " + cause.getValue()
                        ))
                ));
            }
        }
        return ResponseEntity.badRequest().body(DefaultResponse.create(null, null));
    }
}
