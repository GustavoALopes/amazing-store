package com.developerjorney.domain.client.service;

import com.developerjorney.core.patterns.notification.interfaces.INotificationPublisher;
import com.developerjorney.domain.base.services.BaseService;
import com.developerjorney.domain.client.entities.Address;
import com.developerjorney.domain.client.entities.Client;
import com.developerjorney.domain.client.entities.inputs.CreateAddressDomainInput;
import com.developerjorney.domain.client.entities.inputs.ImportClientDomainInput;
import com.developerjorney.domain.client.entities.valueobjects.AddressVO;
import com.developerjorney.domain.client.repositories.IClientRepository;
import com.developerjorney.domain.client.service.interfaces.IClientService;
import org.springframework.stereotype.Service;

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
        final var address = AddressVO();

        return false;
    }
}
