package com.developerjorney.application.client.dtos.input;

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
        String birthdate,

        Address address
) {

        public record Address(

                @NotEmpty
                @Size(min = 2, max = 2)
                @Schema(description = "The country of address", example = "BR")
                String country,

                @NotEmpty
                @Size(max = 3, min = 3)
                @Schema(description = "The state of address", example = "RJ")
                String state,

                @NotEmpty
                @Size(min = 1, max = 100)
                @Schema(description = "The city of address", example = "Duque de Caxias")
                String city,

                @NotEmpty
                @Size(min = 1, max = 100)
                @Schema(description = "The neighborhood of address", example = "Centenário")
                String neighborhood,

                @NotEmpty
                @Size(min = 1, max = 100)
                @Schema(description = "The street of address", example = "Rua Fransicas Thome")
                String street,

                @NotEmpty
                @Size(min= 1, max = 10)
                @Schema(description = "The number of address", example = "686")
                String number,

                @NotEmpty
                @Size(min = 5, max = 11)
                @Schema(description = "The zip code of address", example = "25030210")
                String zipCode,

                @Size(min = 100)
                @Schema(description = "Additional details of address", example = "Apt123A")
                String details
        ) {}
}
