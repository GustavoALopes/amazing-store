package com.developerjorney.application.client.usecase;

import com.developerjorney.application.client.dtos.input.CreateAddressInput;
import com.developerjorney.application.client.usecases.CreateAddressUseCase;
import com.developerjorney.configurations.MockUnitOfWork;
import com.developerjorney.configurations.NotificationPublisherConfig;
import com.developerjorney.core.patterns.notification.NotificationPublisherInMemory;
import com.developerjorney.core.patterns.notification.NotificationSubscriber;
import com.developerjorney.core.persistence.unitofwork.UnitOfWork;
import com.developerjorney.domain.client.entities.Client;
import com.developerjorney.domain.client.entities.inputs.CreateAddressDomainInput;
import com.developerjorney.domain.client.entities.inputs.ImportClientDomainInput;
import com.developerjorney.domain.client.entities.validations.CreateAddressDomainInputValidation;
import com.developerjorney.domain.client.repositories.IClientRepository;
import com.developerjorney.domain.client.service.ClientService;
import com.developerjorney.domain.client.service.interfaces.IClientService;
import org.assertj.core.api.Assertions;
import org.javatuples.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.HashMap;
import java.util.UUID;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        CreateAddressUseCase.class,
        ClientService.class,
        NotificationPublisherConfig.class,
        NotificationPublisherInMemory.class,
        NotificationSubscriber.class,
        MockUnitOfWork.class,
        UnitOfWork.class
})
public class CreateAddressUseCaseTest {

    @Autowired
    private CreateAddressUseCase useCase;

    @SpyBean
    private NotificationSubscriber notificationSubscriber;

    @MockBean
    private IClientRepository repository;

    @Test
    public void shouldCreateAddressAsDefault() {
        //Input
        final var client = this.createClient();

        final var input = new CreateAddressInput(
                "BR",
                "RJ",
                "Blumenau",
                "Itoupava Norte",
                "R. dois de setembro",
                "832",
                "89052-000",
                "Proximo a Chevrolet"
        );

        //Mock

        BDDMockito.given(this.repository.getById(client.getId()))
                .willReturn(client);

        //Execution
        final var result = this.useCase.execute(
                Pair.with(
                        client.getId(),
                        input
                )
        );

        //Assertions
        Assertions.assertThat(result).isTrue();

        Mockito.verify(this.repository, Mockito.times(1)).persist(client);

        Assertions.assertThat(client.getAddresses()).isNotEmpty();

        final var addressResult = client.getAddresses().stream().findFirst().orElse(null);
        Assertions.assertThat(addressResult.getId()).isNotNull();
        Assertions.assertThat(addressResult.getCountry()).isEqualTo(input.country());
        Assertions.assertThat(addressResult.getState()).isEqualTo(input.state());
        Assertions.assertThat(addressResult.getCity()).isEqualTo(input.city());
        Assertions.assertThat(addressResult.getNeighborhood()).isEqualTo(input.neighborhood());
        Assertions.assertThat(addressResult.getStreet()).isEqualTo(input.street());
        Assertions.assertThat(addressResult.getZipCode()).isEqualTo(input.zipCode().replaceAll("[^\\d+]", ""));
        Assertions.assertThat(addressResult.getNumber()).isEqualTo(input.number());
        Assertions.assertThat(addressResult.getDetails()).isEqualTo(input.details());
        Assertions.assertThat(addressResult.isDefault()).isTrue();
    }

    @Test
    public void shouldCreateAddressAsNoDefault() {
        //Input
        final var client = this.createClient();
        client.addAddress(CreateAddressDomainInput.create(
                client.getId(),
                "BR",
                "RJ",
                "City XPTO",
                "Neighborhood XPTO",
                "Street XPTO",
                "832",
                "89052-000",
                "Details XPTO",
                "NOT-IMPLEMETED"
        ));

        final var input = new CreateAddressInput(
                "BR",
                "RJ",
                "Blumenau",
                "Itoupava Norte",
                "R. dois de setembro",
                "832",
                "89052-000",
                "Proximo a Chevrolet"
        );

        //Mock

        BDDMockito.given(this.repository.getById(client.getId()))
                .willReturn(client);

        //Execution
        final var result = this.useCase.execute(
                Pair.with(
                        client.getId(),
                        input
                )
        );

        //Assertions
        Assertions.assertThat(result).isTrue();

        Mockito.verify(this.repository, Mockito.times(1)).persist(client);

        Assertions.assertThat(client.getAddresses()).isNotEmpty();

        final var addressResult = client.getAddresses().stream()
                .filter(address -> address.isDefault() == false)
                .findFirst()
                .orElse(null);

        Assertions.assertThat(addressResult.getId()).isNotNull();
        Assertions.assertThat(addressResult.getCountry()).isEqualTo(input.country());
        Assertions.assertThat(addressResult.getState()).isEqualTo(input.state());
        Assertions.assertThat(addressResult.getCity()).isEqualTo(input.city());
        Assertions.assertThat(addressResult.getNeighborhood()).isEqualTo(input.neighborhood());
        Assertions.assertThat(addressResult.getStreet()).isEqualTo(input.street());
        Assertions.assertThat(addressResult.getZipCode()).isEqualTo(input.zipCode().replaceAll("[^\\d+]", ""));
        Assertions.assertThat(addressResult.getNumber()).isEqualTo(input.number());
        Assertions.assertThat(addressResult.getDetails()).isEqualTo(input.details());
        Assertions.assertThat(addressResult.isDefault()).isFalse();
    }

    @Test
    public void shouldNotifyRequiredsDomainValidations() {
        final var client = this.createClient();

        final var input = new CreateAddressInput(
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "Proximo a Chevrolet"
        );

        //Mock
        BDDMockito.given(this.repository.getById(client.getId()))
                .willReturn(client);

        //Execution
        final var result = this.useCase.execute(
                Pair.with(
                        client.getId(),
                        input
                )
        );

        //Assertions
        Assertions.assertThat(result).isFalse();

        Mockito.verify(this.repository, Mockito.never()).persist(Mockito.any(Client.class));

        final var notificationCodes = new HashMap<String, Boolean>();
        notificationCodes.put(CreateAddressDomainInputValidation.COUNTRY_IS_REQUIRED_CODE, false);
        notificationCodes.put(CreateAddressDomainInputValidation.STATE_IS_REQUIRED_CODE, false);
        notificationCodes.put(CreateAddressDomainInputValidation.CITY_IS_REQUIRED_CODE, false);
        notificationCodes.put(CreateAddressDomainInputValidation.NEIGHBORHOOD_IS_REQUIRED_CODE, false);
        notificationCodes.put(CreateAddressDomainInputValidation.ZIP_CODE_IS_REQUIRED_CODE, false);
        notificationCodes.put(CreateAddressDomainInputValidation.STREET_IS_REQUIRED_CODE, false);
        notificationCodes.put(CreateAddressDomainInputValidation.NUMBER_IS_REQUIRED_CODE, false);

        final var notifications = this.notificationSubscriber.getNotifications();

        notifications.forEach(notification -> {
            Assertions.assertThat(notificationCodes.containsKey(notification.code())).isTrue();
            notificationCodes.put(notification.code(), true);
        });

        notificationCodes.values().forEach(value -> {
            Assertions.assertThat(value).isTrue();
        });
    }

    @Test
    public void shouldNotifyInvalidsDomainValidations() {
        final var client = this.createClient();

        final var input = new CreateAddressInput(
                "B",
                "R",
                "Blumenau",
                "a".repeat(101),
                "a".repeat(101),
                "532",
                "123",
                "Proximo a Chevrolet"
        );

        //Mock
        BDDMockito.given(this.repository.getById(client.getId()))
                .willReturn(client);

        //Execution
        final var result = this.useCase.execute(
                Pair.with(
                        client.getId(),
                        input
                )
        );

        //Assertions
        Assertions.assertThat(result).isFalse();

        Mockito.verify(this.repository, Mockito.never()).persist(Mockito.any(Client.class));

        final var notificationCodes = new HashMap<String, Boolean>();
        notificationCodes.put(CreateAddressDomainInputValidation.COUNTRY_IS_INVALID_CODE, false);
        notificationCodes.put(CreateAddressDomainInputValidation.STATE_IS_INVALID_CODE, false);
        notificationCodes.put(CreateAddressDomainInputValidation.NEIGHBORHOOD_IS_BIGGEST_THAN_MAX_SIZE_CODE, false);
        notificationCodes.put(CreateAddressDomainInputValidation.ZIP_CODE_IS_INVALID_CODE, false);
        notificationCodes.put(CreateAddressDomainInputValidation.STREET_IS_BIGGEST_THAN_MAX_SIZE_CODE, false);

        final var notifications = this.notificationSubscriber.getNotifications();

        notifications.forEach(notification -> {
            Assertions.assertThat(notificationCodes.containsKey(notification.code())).isTrue();
            notificationCodes.put(notification.code(), true);
        });

        notificationCodes.values().forEach(value -> {
            Assertions.assertThat(value).isTrue();
        });
    }

    @Test
    public void shouldNotifyWhenClientDoesntExists() {
        //Inputs
        final var input = new CreateAddressInput(
                "BR",
                "RJ",
                "Blumenau",
                "Itoupava Norte",
                "R. dois de setembro",
                "832",
                "89052-000",
                "Proximo a Chevrolet"
        );

        final var result = this.useCase.execute(
                Pair.with(
                    UUID.randomUUID(),
                    input
                )
        );

        Assertions.assertThat(result).isFalse();

        Mockito.verify(this.repository, Mockito.never()).persist(Mockito.any(Client.class));

        final var notification = this.notificationSubscriber.getNotifications().stream().findFirst().orElse(null);
        Assertions.assertThat(notification.code()).isEqualTo(IClientService.CREATE_ADDRESS_CLIENT_NOT_FOUND_CODE);
    }


    private Client createClient() {
        final var client = new Client();
        client.importClient(ImportClientDomainInput.create(
                "Name XPTO",
                "LastName XPTO",
                "2000-12-31",
                "email@test.com",
                "NOT-IMPLEMENTED"
        ));
        return client;
    }
}
