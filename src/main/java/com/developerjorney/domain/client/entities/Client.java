package com.developerjorney.domain.client.entities;

import ch.qos.logback.core.BasicStatusManager;
import com.developerjorney.core.patterns.validation.BaseValidation;
import com.developerjorney.domain.base.entities.BaseEntity;
import com.developerjorney.domain.base.entities.interfaces.IAggregateRoot;
import com.developerjorney.domain.client.entities.inputs.CreateAddressDomainInput;
import com.developerjorney.domain.client.entities.inputs.ImportClientDomainInput;
import com.developerjorney.domain.client.entities.validations.CreateAddressDomainInputValidation;
import com.developerjorney.domain.client.entities.validations.ImportClientDomainInputValidation;
import com.developerjorney.domain.client.entities.valueobjects.AddressVO;
import com.developerjorney.domain.client.entities.valueobjects.EmailVO;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(mappedBy = "client", cascade = {CascadeType.ALL})
    private Set<AddressVO> address;

    @Transient
    private final transient ImportClientDomainInputValidation importClientDomainInputValidation;

    @Transient
    private final transient CreateAddressDomainInputValidation createAddressDomainInputValidation;


    public Client() {
        this.name = "";
        this.lastName = "";
        this.address = new HashSet<>();
        this.importClientDomainInputValidation = new ImportClientDomainInputValidation();
        this.createAddressDomainInputValidation = new CreateAddressDomainInputValidation();
    }

    public boolean importClient(
        final ImportClientDomainInput input
    ) {
        if(!this.valid(() -> this.importClientDomainInputValidation.validate(input))) {
            return false;
        }

        this.<Client>createNew(input.getCreatedBy())
                .setIdentifier(input.getEmail())
                .setClientName(input.getName(), input.getLastName())
                .setBirthdate(input.getBirthdate());

        return true;
    }

    private Client setIdentifier(final EmailVO email) {
        this.email = email.getValue();
        return this;
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

    public boolean addAddress(final CreateAddressDomainInput input) {
        if(!this.valid(() -> this.createAddressDomainInputValidation.validate(input))) {
            return false;
        }

        final var shouldBeDefault = this.address.isEmpty();
        final var address = AddressVO.create(
                this,
                input.getCountry(),
                input.getState(),
                input.getCity(),
                input.getNeighborhood(),
                input.getStreet(),
                input.getNumber(),
                input.getZipCode(),
                input.getDetails(),
                shouldBeDefault,
                input.getCreatedBy()
        );

        this.address.add(address);

        return true;
    }
}
