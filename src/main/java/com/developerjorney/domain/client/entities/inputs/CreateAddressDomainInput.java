package com.developerjorney.domain.client.entities.inputs;

import lombok.Getter;

import java.util.Optional;
import java.util.UUID;

@Getter
public class CreateAddressDomainInput {

    private final UUID clientId;

    private final String country;

    private final String state;

    private final String city;

    private final String neighborhood;

    private final String street;

    private final String number;

    private final String zipCode;

    private final String details;

    private final String createdBy;

    public CreateAddressDomainInput(
            final UUID clientId,
            final String country,
            final String state,
            final String city,
            final String neighborhood,
            final String street,
            final String number,
            final String zipCode,
            final String details,
            final String createdBy
    ) {
        this.clientId = clientId;
        this.country = country;
        this.state = state;
        this.city = city;
        this.neighborhood = neighborhood;
        this.street = street;
        this.number = number;
        this.zipCode = zipCode;
        this.details = details;
        this.createdBy = createdBy;
    }

    public static CreateAddressDomainInput create(
            final UUID clientId,
            final String country,
            final String state,
            final String city,
            final String neighborhood,
            final String street,
            final String number,
            final String zipCode,
            final String details,
            final String createdBy
    ) {
        final var cleanZipCode = clearNotNumber(zipCode);

        return new CreateAddressDomainInput(
            clientId,
            country,
            state,
            city,
            neighborhood,
            street,
            number,
            cleanZipCode,
            details,
            createdBy
        );
    }

    private static String clearNotNumber(final String number) {
        return Optional.ofNullable(number).map(value -> value.replaceAll("[^\\d+]", "")).orElse("");
    }
}
