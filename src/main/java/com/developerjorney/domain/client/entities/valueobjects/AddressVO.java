package com.developerjorney.domain.client.entities.valueobjects;

import com.developerjorney.domain.client.entities.Client;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Entity
@Getter
public class AddressVO {

    @Id
    private final UUID id;

    @ManyToOne
    private final Client client;

    private final String country;
    private final String state;
    private final String city;
    private final String neighborhood;
    private final String street;
    private final String number;
    private final String zipCode;
    private final String details;

    public AddressVO(
        final UUID id,
        final Client client,
        final String country,
        final String state,
        final String city,
        final String neighborhood,
        final String street,
        final String number,
        final String zipCode,
        final String details
    ) {
        this.id = id;
        this.client = client;
        this.country = country;
        this.state = state;
        this.city = city;
        this.neighborhood = neighborhood;
        this.street = street;
        this.number = number;
        this.zipCode = zipCode;
        this.details = details;
    }

    public static AddressVO create(
            final Client client,
            final String country,
            final String state,
            final String city,
            final String neighborhood,
            final String street,
            final String number,
            final String zipCode,
            final String details
    ) {
        return new AddressVO(
            UUID.randomUUID(),
            client,
            country,
            state,
            city,
            neighborhood,
            street,
            number,
            zipCode,
            Optional.ofNullable(details).orElse("")
        );
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final AddressVO addressVO = (AddressVO) o;
        return Objects.equals(client, addressVO.client) && Objects.equals(country, addressVO.country) && Objects.equals(state, addressVO.state) && Objects.equals(city, addressVO.city) && Objects.equals(neighborhood, addressVO.neighborhood) && Objects.equals(street, addressVO.street) && Objects.equals(number, addressVO.number) && Objects.equals(zipCode, addressVO.zipCode) && Objects.equals(details, addressVO.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(client, country, state, city, neighborhood, street, number, zipCode, details);
    }
}
