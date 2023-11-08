package com.developerjorney.application.client.dtos.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

public record GetAllClientsInput(

        @Size(
            max = 100,
            min = 1
        )
        @Schema(
            description = "The client name",
            example = "Benjamin"
        )
        String name,

        @Schema(
                description = "The client last name",
                example = "Oliver"
        )
        String lastName,

        @Schema(
                description = "The client identifier",
                example = "email@test.com"
        )
        String email,

        RangeDateInput dates
) {}
