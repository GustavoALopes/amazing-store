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

import java.util.Map;
import java.util.Set;

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
    private IClientRepository repository;

    @SpyBean
    private INotificationSubscriber notificationSubscriber;

    @Test
    public void shouldImportClient() {
        //Input
        final var input = new ImportClientInput(
                "Cliente",
                "A",
                "2000-12-01"
        );

        //Mock
        BDDMockito.given(this.repository.persist(
                Mockito.any(Client.class)
        )).willReturn(true);

        //Execution
        final var result = this.useCase.execute(input);

        Assertions.assertThat(result).isTrue();
        Mockito.verify(this.repository, Mockito.times(1))
                .persist(Mockito.any(Client.class));
    }

    @Test
    public void shouldNotifyErrorsWhenTryInvalidInput() {
        //Input
        final var invalidInput = new ImportClientInput(
                null,
                null,
                null
        );

        //Execution
        final var result = this.useCase.execute(invalidInput);

        Assertions.assertThat(result).isFalse();
        Mockito.verify(
                this.repository,
                Mockito.never()
        ).persist(Mockito.any(Client.class));

        Assertions.assertThat(this.notificationSubscriber.hasNotification()).isTrue();

        final var notifications = this.notificationSubscriber.getNotifications();
        Assertions.assertThat(notifications.size()).isEqualTo(3);

        final var types = Map.of(
                ImportClientDomainInputValidation.NAME_IS_REQUIRED, "",
                ImportClientDomainInputValidation.LAST_NAME_IS_REQUIRED, "",
                ImportClientDomainInputValidation.BIRTHDATE_IS_REQUIRED, ""
        );

        notifications.forEach(notification -> {
            Assertions.assertThat(types.containsKey(notification.code())).isTrue();
        });

    }


}
