package com.developerjorney.application.client.dtos.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

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

        @Schema(
                description = "The initial Date in ISO8601 format",
                example = "2000-12-01"
        )
        LocalDate initDate,

        @Schema(
                description = "The final Date in ISO8601 format",
                example = "2001-12-01"
        )
        LocalDate finalDate
) {}
