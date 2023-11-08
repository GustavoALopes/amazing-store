package com.developerjorney.application.client.controllers;

import com.developerjorney.application.base.controllers.BaseController;
import com.developerjorney.application.base.dtos.DefaultResponse;
import com.developerjorney.application.client.dtos.input.CreateAddressInput;
import com.developerjorney.application.client.dtos.input.GetAllClientsInput;
import com.developerjorney.application.client.dtos.input.ImportClientInput;
import com.developerjorney.application.client.queries.ClientQuery;
import com.developerjorney.application.client.usecases.CreateAddressUseCase;
import com.developerjorney.application.client.usecases.ImportClientUseCase;
import com.developerjorney.application.enums.ApiVersions;
import com.developerjorney.core.patterns.notification.interfaces.INotificationSubscriber;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.javatuples.Pair;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Tag(name = "Client")
@RequestMapping(value = ApiVersions.V1 + "/clients")
public class ClientController extends BaseController {

    private final ImportClientUseCase importClientUseCase;

    private final CreateAddressUseCase createAddressUseCase;

    private final ClientQuery query;

    public ClientController(
            final INotificationSubscriber subscriber,
            final ImportClientUseCase importClientUseCase,
            final CreateAddressUseCase createAddressUseCase,
            final ClientQuery query
    ) {
        super(subscriber);
        this.query = query;
        this.importClientUseCase = importClientUseCase;
        this.createAddressUseCase = createAddressUseCase;
    }

    @PostMapping(value = "/import")
    @Operation(
            description = "Using this resource to import some Client.",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            content = @Content,
                            description = "Client has been registered"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Something need to be validate."
                    )
            }
    )
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

    @GetMapping
    public ResponseEntity<DefaultResponse> list(
            final @ParameterObject @ModelAttribute GetAllClientsInput input,
            final @ParameterObject @PageableDefault(size = 20) Pageable page
    ) {
        final var result = this.query.report(
                input,
                page
        );
        return ResponseEntity.ok(DefaultResponse.create(result));
    }

    @PostMapping(value = "/{clientId}/address")
    public ResponseEntity<DefaultResponse> createAddress(
        final @PathVariable(name = "clientId") UUID clientId,
        final @RequestBody CreateAddressInput input
    ) {
        final var result = this.createAddressUseCase.execute(
                Pair.with(
                        clientId,
                        input
                )
        );

        if(!result) {
            return ResponseEntity.badRequest()
                    .body(this.getNotificationsResponse());
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
