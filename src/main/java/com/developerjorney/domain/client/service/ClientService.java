package com.developerjorney.domain.client.service;

import com.developerjorney.core.patterns.notification.enums.NotificationTypeEnum;
import com.developerjorney.core.patterns.notification.interfaces.INotificationPublisher;
import com.developerjorney.core.patterns.notification.models.Notification;
import com.developerjorney.core.patterns.result.ProcessResult;
import com.developerjorney.domain.base.services.BaseService;
import com.developerjorney.domain.client.entities.Client;
import com.developerjorney.domain.client.entities.inputs.CreateAddressDomainInput;
import com.developerjorney.domain.client.entities.inputs.ImportClientDomainInput;
import com.developerjorney.domain.client.entities.valueobjects.EmailVO;
import com.developerjorney.domain.client.repositories.IClientRepository;
import com.developerjorney.domain.client.service.interfaces.IClientService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ClientService extends BaseService<Client> implements IClientService {

    public static final String CLIENT_ALREADY_EXISTS_CODE = "CLIENT_ALREADY_EXISTS";

    public static final String CLIENT_CANNOT_BE_PERSISTED_CODE = "CLIENT_CANNOT_BE_PERSISTED";

    private final IClientRepository repository;

    public ClientService(
            final INotificationPublisher notificationPublisher,
            final IClientRepository repository
    ) {
        super(notificationPublisher);
        this.repository = repository;
    }

    @Override
    public ProcessResult<Client> importClient(final ImportClientDomainInput input) {
        if(this.clientExistsByEmail(input.getEmail())) {
           this.addNotification(new Notification(
                   NotificationTypeEnum.ERROR,
                   CLIENT_ALREADY_EXISTS_CODE,
                   "There is already a client for informed email."
           ));
           return ProcessResult.fail();
        }

        final var client = new Client();
        client.importClient(input);

        if(!this.isValidOrNotify(client)) {
            return ProcessResult.fail();
        }

        final var result = this.repository.persist(client);

        if(!result) {
            this.addNotification(new Notification(
                    NotificationTypeEnum.ERROR,
                    CLIENT_CANNOT_BE_PERSISTED_CODE,
                    "The client cannot be persisted in the storage"
            ));
            return ProcessResult.fail();
        }

        return ProcessResult.success(client);
    }

    private boolean clientExistsByEmail(final EmailVO email) {
        return this.repository.existsByEmail(email);
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
