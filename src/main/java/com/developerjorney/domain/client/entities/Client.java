package com.developerjorney.domain.client.entities;

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
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

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
    private Set<AddressVO> addresses;

    @Transient
    private final transient ImportClientDomainInputValidation importClientDomainInputValidation;

    @Transient
    private final transient CreateAddressDomainInputValidation createAddressDomainInputValidation;


    public Client() {
        this.name = "";
        this.lastName = "";
        this.addresses = new HashSet<>();
        this.importClientDomainInputValidation = new ImportClientDomainInputValidation();
        this.createAddressDomainInputValidation = new CreateAddressDomainInputValidation();
    }

    private Client(
        final UUID id,
        final String name,
        final String lastName,
        final String email,
        final LocalDate birthdate,
        final Set<AddressVO> addresses
    ) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.birthdate = birthdate;
        this.addresses = addresses;

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

        if(Objects.nonNull(input.getAddress())) {
            this.addAddress(input.getAddress());
        }

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

        final var shouldBeDefault = this.addresses.isEmpty();
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

        this.addresses.add(address);

        return true;
    }

    public static Client fromExistClient(
        final UUID id,
        final String name,
        final String lastName,
        final String email,
        final LocalDate birthdate,
        final Set<AddressVO> addresses
    ) {
        return new Client(
            id,
            name,
            lastName,
            email,
            birthdate,
            addresses
        );
    }
}
