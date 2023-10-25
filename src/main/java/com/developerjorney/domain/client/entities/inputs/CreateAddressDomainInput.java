package com.developerjorney.domain.client.entities.inputs;

import lombok.Getter;

import java.util.Optional;

@Getter
public class CreateAddressDomainInput {

    private final String country;
    private final String state;
    private final String city;
    private final String neighborhood;
    private final String street;
    private final String number;
    private final String zipCode;
    private final String details;

    public CreateAddressDomainInput(
            final String country,
            final String state,
            final String city,
            final String neighborhood,
            final String street,
            final String number,
            final String zipCode,
            final String details
    ) {
        this.country = country;
        this.state = state;
        this.city = city;
        this.neighborhood = neighborhood;
        this.street = street;
        this.number = number;
        this.zipCode = zipCode;
        this.details = details;
    }

    public static Object create(
        final String country,
        final String state,
        final String city,
        final String neighborhood,
        final String street,
        final String number,
        final String zipCode,
        final String details
    ) {
        final var cleanNumber = clearNotNumber(number);
        final var cleanZipCode = clearNotNumber(zipCode);

        return new CreateAddressDomainInput(
            country,
            state,
            city,
            neighborhood,
            street,
            cleanNumber,
            cleanZipCode,
            details
        );
    }

    private static String clearNotNumber(final String number) {
        return Optional.ofNullable(number).map(value -> value.replaceAll("[\\d+]", "")).orElse("");
    }
}
