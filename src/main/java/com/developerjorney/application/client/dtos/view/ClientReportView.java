package com.developerjorney.application.client.dtos.view;

import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
public class ClientReportView {

    private final String name;

    private final String lastName;

    private final String birthdate;

    public ClientReportView(
        final String name,
        final String lastName,
        final LocalDate birthdate
    ) {
        this.name = name;
        this.lastName = lastName;
        this.birthdate = birthdate.toString();
    }

    public static ClientReportView create(
        final String name,
        final String lastName,
        final LocalDate birthdate
    ) {
        return new ClientReportView(
                name,
                lastName,
                birthdate
        );
    }

    public static ClientReportView create(
            final String name,
            final String lastName,
            final String birthdate
    ) {
        return create(
                name,
                lastName,
                LocalDate.parse(birthdate)
        );
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ClientReportView that = (ClientReportView) o;
        return Objects.equals(name, that.name) && Objects.equals(lastName, that.lastName) && Objects.equals(birthdate, that.birthdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lastName, birthdate);
    }
}
