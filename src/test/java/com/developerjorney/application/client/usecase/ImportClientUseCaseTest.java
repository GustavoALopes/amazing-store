package com.developerjorney.application.client.usecase;

import com.developerjorney.application.client.dtos.input.ImportClientInput;
import com.developerjorney.application.client.usecases.ImportClientUseCase;
import com.developerjorney.configurations.MockUnitOfWork;
import com.developerjorney.configurations.NotificationPublisherConfig;
import com.developerjorney.core.patterns.notification.NotificationPublisherInMemory;
import com.developerjorney.core.patterns.notification.NotificationSubscriber;
import com.developerjorney.core.patterns.notification.interfaces.INotificationSubscriber;
import com.developerjorney.core.persistence.unitofwork.UnitOfWork;
import com.developerjorney.domain.client.entities.Client;
import com.developerjorney.domain.client.entities.validations.ImportClientDomainInputValidation;
import com.developerjorney.domain.client.repositories.IClientRepository;
import com.developerjorney.domain.client.service.ClientService;
import org.assertj.core.api.Assertions;
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

import java.time.LocalDate;
import java.util.Map;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        ImportClientUseCase.class,
        ClientService.class,
        UnitOfWork.class,
        MockUnitOfWork.class,
        NotificationPublisherConfig.class,
        NotificationPublisherInMemory.class,
        NotificationSubscriber.class
})
public class ImportClientUseCaseTest {

    @Autowired
    private ImportClientUseCase useCase;

    @MockBean
    private IClientRepository clientRepository;

    @SpyBean
    private INotificationSubscriber notificationSubscriber;

    @Test
    public void shouldImportClient() {
        //Input
        final var input = new ImportClientInput(
                "Cliente",
                "A",
                "email@test.com",
                LocalDate.parse("2000-12-01"),
                null
        );

        //Mock
        BDDMockito.given(this.clientRepository.persist(
                Mockito.any(Client.class)
        )).willReturn(true);

        //Execution
        final var result = this.useCase.execute(input);

        Assertions.assertThat(result.isFail()).isFalse();
        Mockito.verify(this.clientRepository, Mockito.times(1))
                .persist(Mockito.any(Client.class));
    }

    @Test
    public void shouldImportClientAndClientAddress() {
        //Input
        final var input = new ImportClientInput(
                "Cliente",
                "A",
                "email@test.com",
                LocalDate.parse("2000-12-01"),
                new ImportClientInput.Address(
                        "BR",
                        "RJ",
                        "City XPTO",
                        "Neighborhood XPTO",
                        "Street XPTO",
                        "832",
                        "89052-000",
                        "Details XPTO"
                )
        );

        //Mock
        BDDMockito.given(this.clientRepository.persist(
                Mockito.any(Client.class)
        )).willReturn(true);

        //Execution
        final var result = this.useCase.execute(input);

        Assertions.assertThat(result.isFail()).isFalse();
        Mockito.verify(this.clientRepository, Mockito.times(1))
                .persist(Mockito.any(Client.class));
    }

    @Test
    public void shouldNotifyRequiredErrorsWhenTryInvalidInput() {
        //Input
        final var invalidInput = new ImportClientInput(
                null,
                null,
                null,
                null,
                null
        );

        //Execution
        final var result = this.useCase.execute(invalidInput);

        Assertions.assertThat(result.isFail()).isTrue();
        Mockito.verify(
                this.clientRepository,
                Mockito.never()
        ).persist(Mockito.any(Client.class));

        Assertions.assertThat(this.notificationSubscriber.hasNotification()).isTrue();

        final var notifications = this.notificationSubscriber.getNotifications();
        Assertions.assertThat(notifications.size()).isEqualTo(4);

        final var types = Map.of(
                ImportClientDomainInputValidation.NAME_IS_REQUIRED, "",
                ImportClientDomainInputValidation.LAST_NAME_IS_REQUIRED, "",
                ImportClientDomainInputValidation.BIRTHDATE_IS_REQUIRED, "",
                ImportClientDomainInputValidation.EMAIL_IS_INVALID, ""
        );

        notifications.forEach(notification -> {
            Assertions.assertThat(types.containsKey(notification.code())).isTrue();
        });

    }

    @Test
    public void shouldNotifyInvalidsErrorsWhenTryInvalidInput() {
        //Input
        final var invalidInput = new ImportClientInput(
                "nameXPTO".repeat(20),
                "lastNameXPTO".repeat(20),
                "emailXPTO".repeat(20),
                LocalDate.parse("2000-12-30"),
                null
        );

        //Execution
        final var result = this.useCase.execute(invalidInput);

        Assertions.assertThat(result.isFail()).isTrue();
        Mockito.verify(
                this.clientRepository,
                Mockito.never()
        ).persist(Mockito.any(Client.class));

        Assertions.assertThat(this.notificationSubscriber.hasNotification()).isTrue();

        final var notifications = this.notificationSubscriber.getNotifications();
        Assertions.assertThat(notifications.size()).isEqualTo(4);

        final var types = Map.of(
            ImportClientDomainInputValidation.NAME_IS_BIGGEST_THAN_MAX_SIZE, true,
            ImportClientDomainInputValidation.LAST_NAME_IS_BIGGEST_THAN_MAX_SIZE, true,
            ImportClientDomainInputValidation.EMAIL_IS_INVALID, true,
            ImportClientDomainInputValidation.EMAIL_IS_BIGGEST_THAN_MAX_SIZE, true
        );

        notifications.forEach(notification -> {
            Assertions.assertThat(types.containsKey(notification.code())).isTrue();
        });
    }


}
