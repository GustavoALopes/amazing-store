package com.developerjorney.application.client.dtos.input;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record ImportClientInput(

        @NotEmpty
        @Size(
            max = 100,
            min = 1
        )
        @Schema(
            description = "The client name",
            example = "Benjamin"
        )
        String name,

        @NotEmpty
        @Size(
            max = 100,
            min = 1
        )
        @Schema(
            description = "The client last name",
            example = "Oliver"
        )
        String lastName,


        @NotEmpty
        @Schema(
                description = "The identifier of client",
                example = "email@teste.com"
        )
        String email,

        @NotEmpty
        @Schema(
            description = "The client birthdate in ISO8601",
            example = "2000-12-01"
        )
        String birthdate
) {}
