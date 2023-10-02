package com.developerjorney.application.client.controllers;

import com.developerjorney.application.base.dtos.DefaultResponse;
import com.developerjorney.application.client.dtos.input.ImportClientInput;
import com.developerjorney.application.client.usecases.ImportClientUseCase;
import com.developerjorney.application.enums.ApiVersions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = ApiVersions.V1 + "/clients")
public class ClientController {

    private final ImportClientUseCase importClientUseCase;

    public ClientController(final ImportClientUseCase importClientUseCase) {
        this.importClientUseCase = importClientUseCase;
    }

    @PostMapping(value = "/import")
    public ResponseEntity<DefaultResponse> importClient(
            final @RequestBody ImportClientInput input
    ) {
        final var result = this.importClientUseCase.execute(input);
        if(!result) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.noContent().build();
    }
}
