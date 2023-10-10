package com.developerjorney.domain.client.entities;

import com.developerjorney.domain.base.entities.BaseEntity;
import com.developerjorney.domain.base.entities.interfaces.IAggregateRoot;
import com.developerjorney.domain.client.entities.inputs.ImportClientDomainInput;
import com.developerjorney.domain.client.entities.validations.ImportClientDomainInputValidation;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Entity
public class Client extends BaseEntity implements IAggregateRoot {

    @Column(name = "name")
    private String name;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "birthdate")
    private LocalDate birthdate;

    @Transient
    private transient ImportClientDomainInputValidation importClientDomainInputValidation;


    public Client() {
        this.name = "";
        this.lastName = "";
        this.importClientDomainInputValidation = new ImportClientDomainInputValidation();
    }

    public boolean importClient(
        final ImportClientDomainInput input
    ) {
        if(!this.valid(() -> this.importClientDomainInputValidation.validate(input))) {
            return false;
        }

        this.<Client>createNew(input.getCreatedBy())
                .setClientName(input.getName(), input.getLastName())
                .setBirthdate(input.getBirthdate());

        return true;
    }

    private Client setBirthdate(final LocalDate birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    private Client setClientName(
        final String name,
        final String lastName
    ) {
        this.name = name;
        this.lastName = lastName;

        return this;
    }
}
