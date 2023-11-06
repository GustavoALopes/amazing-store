package com.developerjorney.domain.client.service;

import com.developerjorney.core.patterns.notification.enums.NotificationTypeEnum;
import com.developerjorney.core.patterns.notification.interfaces.INotificationPublisher;
import com.developerjorney.core.patterns.notification.models.Notification;
import com.developerjorney.domain.base.services.BaseService;
import com.developerjorney.domain.client.entities.Client;
import com.developerjorney.domain.client.entities.inputs.CreateAddressDomainInput;
import com.developerjorney.domain.client.entities.inputs.ImportClientDomainInput;
import com.developerjorney.domain.client.repositories.IClientRepository;
import com.developerjorney.domain.client.service.interfaces.IClientService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ClientService extends BaseService<Client> implements IClientService {

    private final IClientRepository repository;

    public ClientService(
            final INotificationPublisher notificationPublisher,
            final IClientRepository repository
    ) {
        super(notificationPublisher);
        this.repository = repository;
    }

    @Override
    public boolean importClient(final ImportClientDomainInput input) {
        final var client = new Client();
        client.importClient(input);

        if(!this.isValidOrNotify(client)) {
            return false;
        }

        return this.repository.persist(client);
    }

    @Override
    public boolean createAddress(final CreateAddressDomainInput input) {
        final var client = this.repository.getById(input.getClientId());
        if(Objects.isNull(client)) {
            this.addNotification(new Notification(
                    NotificationTypeEnum.ERROR,
                    IClientService.CREATE_ADDRESS_CLIENT_NOT_FOUND_CODE,
                    "The client doesn't exists"
            ));
            return false;
        }

        client.addAddress(input);

        if(!this.isValidOrNotify(client)) {
            return false;
        }

        this.repository.persist(client);

        return true;
    }
}
