package com.developerjorney.application.client.dtos.view;

import com.developerjorney.domain.client.entities.Client;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Getter
public class ClientReportView {

    private final UUID id;

    private final String email;

    private final String name;

    private final String lastName;

    private final String birthdate;

    public ClientReportView(
        final UUID id,
        final String email,
        final String name,
        final String lastName,
        final LocalDate birthdate
    ) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.birthdate = birthdate.toString();
    }

    public static ClientReportView create(
        final UUID id,
        final String name,
        final String lastName,
        final String email,
        final LocalDate birthdate
    ) {
        return new ClientReportView(
                id,
                name,
                lastName,
                email,
                birthdate
        );
    }

    public static ClientReportView create(
            final UUID id,
            final String name,
            final String lastName,
            final String email,
            final String birthdate
    ) {
        return create(
                id,
                name,
                lastName,
                email,
                LocalDate.parse(birthdate)
        );
    }

    public static ClientReportView create(
        final Client client
    ) {
        return create(
                client.getId(),
                client.getName(),
                client.getLastName(),
                client.getEmail(),
                client.getBirthdate()
        );
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ClientReportView that = (ClientReportView) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(lastName, that.lastName) && Objects.equals(birthdate, that.birthdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastName, birthdate);
    }
}
