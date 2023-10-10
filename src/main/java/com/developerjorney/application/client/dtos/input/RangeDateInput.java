package com.developerjorney.application.client.dtos.input;

import io.swagger.v3.oas.annotations.media.Schema;

public record RangeDateInput(
        @Schema(
                description = "The initial Date in ISO8601",
                example = "2000-12-01"
        )
        String initDate,

        @Schema(
                description = "The final Date in ISO8601",
                example = "2001-12-01"
        )
        String finalDate
) {}
