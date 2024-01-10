package com.developerjorney.domain.client.repositories;

import com.developerjorney.domain.base.entities.interfaces.IRepository;
import com.developerjorney.domain.client.entities.Client;
import com.developerjorney.domain.client.entities.valueobjects.EmailVO;

import java.util.UUID;

public interface IClientRepository extends IRepository<Client> {
    Client getById(final UUID id);

    boolean existsByEmail(final EmailVO email);
}
