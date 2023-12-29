package com.developerjorney;

import com.developerjorney.application.client.dtos.input.ImportClientInput;
import com.developerjorney.domain.client.entities.Client;

import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;

public class BaseTest {

    public Client makeClient() {
        return Client.fromExistClient(
            UUID.randomUUID(),
            "Name XPTO",
            "LastName XPTO",
            "email@xpto.com",
            LocalDate.parse("2000-12-01"),
            Collections.emptySet()
        );
    }

    public Client makeClient(final ImportClientInput input) {
        return Client.fromExistClient(
                UUID.randomUUID(),
                input.name(),
                input.lastName(),
                input.email(),
                input.birthdate(),
                Collections.emptySet()
        );
    }
}
