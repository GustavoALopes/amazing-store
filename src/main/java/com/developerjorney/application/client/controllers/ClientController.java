package com.developerjorney.application.client.controllers;

import com.developerjorney.application.base.controllers.BaseController;
import com.developerjorney.application.base.dtos.DefaultResponse;
import com.developerjorney.application.client.dtos.input.ImportClientInput;
import com.developerjorney.application.client.usecases.ImportClientUseCase;
import com.developerjorney.application.enums.ApiVersions;
import com.developerjorney.core.patterns.notification.interfaces.INotificationSubscriber;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = ApiVersions.V1 + "/clients")
public class ClientController extends BaseController {

    private final ImportClientUseCase importClientUseCase;

    public ClientController(
            final INotificationSubscriber subscriber,
            final ImportClientUseCase importClientUseCase
    ) {
        super(subscriber);
        this.importClientUseCase = importClientUseCase;
    }

    @PostMapping(value = "/import")
    public ResponseEntity<DefaultResponse> importClient(
        final @RequestBody ImportClientInput input
    ) {
        final var result = this.importClientUseCase.execute(input);
        if(!result) {
            return ResponseEntity.badRequest().body(
                    this.getNotificationsResponse()
            );
        }

        return ResponseEntity.noContent().build();
    }
}
