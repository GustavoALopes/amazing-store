package com.developerjorney.domain.client.entities.inputs;

import com.developerjorney.domain.client.entities.valueobjects.EmailVO;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Getter
public class ImportClientDomainInput {

    private final String name;

    private final String lastName;

    private final LocalDate birthdate;

    private final EmailVO email;

    private String createdBy;

    private ImportClientDomainInput(
        final String name,
        final String lastName,
        final LocalDate birthdate,
        final String email,
        final String createdBy
    ) {
        this.name = name;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.email = EmailVO.create(email);
        this.createdBy = createdBy;
    }

    public static ImportClientDomainInput create(
            final String name,
            final String lastName,
            final String birthdate,
            final String email,
            final String createdBy
    ) {
        return new ImportClientDomainInput(
                name,
                lastName,
                Objects.isNull(birthdate) ? null : LocalDate.parse(birthdate),
                email,
                createdBy
        );
    }
}
