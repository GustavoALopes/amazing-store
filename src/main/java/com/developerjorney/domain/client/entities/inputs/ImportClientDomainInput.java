package com.developerjorney.domain.client.entities.inputs;

import com.developerjorney.domain.client.entities.valueobjects.EmailVO;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
public class ImportClientDomainInput {

    private final String name;

    private final String lastName;

    private final LocalDate birthdate;

    private final EmailVO email;

    private final CreateAddressDomainInput address;

    private String createdBy;


    private ImportClientDomainInput(
        final String name,
        final String lastName,
        final LocalDate birthdate,
        final EmailVO email,
        final String createdBy,
        final CreateAddressDomainInput address
    ) {
        this.name = name;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.email = email;
        this.createdBy = createdBy;
        this.address = address;
    }

    public static Builder builder() {
        return new ImportClientDomainInput.Builder();
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
                EmailVO.create(email),
                createdBy,
                null
        );
    }

    public CreateAddressDomainInput getAddress() {
        return this.address;
    }

    public static class Builder {

        private ClientInfoBuilder clientInfo;

        private AddressInfoBuilder addressInfo;

        private String createdBy;

        public Builder addClientInformation(final ClientInfoBuilder builder) {
            this.clientInfo = builder;
            return this;
        }

        public Builder addAddressInformation(final AddressInfoBuilder builder) {
            this.addressInfo = builder;
            return this;
        }

        public Builder addCreateByInformation(final String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public ImportClientDomainInput build() {
            final var addressDomainInput = Objects.nonNull(this.addressInfo) ? new CreateAddressDomainInput(
                    null,
                    this.addressInfo.getCountry(),
                    this.addressInfo.getState(),
                    this.addressInfo.getCity(),
                    this.addressInfo.getNeighborhood(),
                    this.addressInfo.getStreet(),
                    this.addressInfo.getNumber(),
                    this.addressInfo.getZipCode(),
                    this.addressInfo.getDetails(),
                    this.createdBy
            ) : null;

            return new ImportClientDomainInput(
                this.clientInfo.getName(),
                this.clientInfo.getLastName(),
                this.clientInfo.getBirthdate(),
                this.clientInfo.getEmail(),
                this.createdBy,
                addressDomainInput
            );
        }

        @Getter
        public static class ClientInfoBuilder {

            private String name;

            private String lastName;

            private LocalDate birthdate;

            private EmailVO email;

            public static ClientInfoBuilder builder() {
                return new ClientInfoBuilder();
            }

            public ClientInfoBuilder name(final String name) {
                this.name = name;
                return this;
            }
            public ClientInfoBuilder lastName(final String lastName) {
                this.lastName = lastName;
                return this;
            }
            public ClientInfoBuilder birthdate(final LocalDate birthdate) {
                this.birthdate = birthdate;
                return this;
            }
            public ClientInfoBuilder email(final String email) {
                this.email = EmailVO.create(email);
                return this;
            }
        }

        @Getter
        public static class AddressInfoBuilder {

            private String country;

            private String state;

            private String city;

            private String neighborhood;

            private String street;

            private String number;

            private String zipCode;

            private String details;

            public static AddressInfoBuilder builder() {
                return new AddressInfoBuilder();
            }

            public AddressInfoBuilder country(final String country) {
                this.country = country;
                return this;
            }
            public AddressInfoBuilder state(final String state) {
                this.state = state;
                return this;
            }
            public AddressInfoBuilder city(final String city) {
                this.city = city;
                return this;
            }
            public AddressInfoBuilder neighborhood(final String neighborhood) {
                this.neighborhood = neighborhood;
                return this;
            }
            public AddressInfoBuilder street(final String street) {
                this.street = street;
                return this;
            }
            public AddressInfoBuilder number(final String number) {
                this.number = number;
                return this;
            }
            public AddressInfoBuilder zipCode(final String zipCode) {
                this.zipCode = zipCode;
                return this;
            }
            public AddressInfoBuilder details(final String details) {
                this.details = details;
                return this;
            }
        }
    }
}
