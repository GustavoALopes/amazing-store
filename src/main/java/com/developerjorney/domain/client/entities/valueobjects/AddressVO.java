package com.developerjorney.domain.client.entities.valueobjects;

import com.developerjorney.domain.base.entities.valueobjects.InfoAuditVO;
import com.developerjorney.domain.client.entities.Client;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Entity
@Getter
@Table(name = AddressVO.TABLE_NAME)
public class AddressVO {

    public static final String TABLE_NAME = "address";

    @Id
    private final UUID id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private final Client client;

    private final String country;

    private final String state;

    private final String city;

    private final String neighborhood;

    private final String street;

    private final String number;

    @Column(name = "zip_code")
    private final String zipCode;

    private final String details;

    private final boolean isDefault;

    private final InfoAuditVO infoAudit;


    public AddressVO() {
        this.id = null;
        this.client = null;
        this.country = "";
        this.state = "";
        this.city = "";
        this.neighborhood = "";
        this.street = "";
        this.number = "";
        this.zipCode = "";
        this.details = "";
        this.isDefault = false;

        this.infoAudit = new InfoAuditVO();
    }

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
        final String details,
        final boolean isDefault,
        final String createdBy
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
        this.isDefault = isDefault;

        this.infoAudit = InfoAuditVO.createNew(
                createdBy
        );
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
            final String details,
            final boolean isDefault,
            final String createdBy
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
            Optional.ofNullable(details).orElse(""),
            isDefault,
            createdBy
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
