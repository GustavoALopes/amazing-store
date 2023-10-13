package com.developerjorney.infra.repositories;

import com.developerjorney.core.persistence.unitofwork.interfaces.IUnitOfWork;
import com.developerjorney.domain.client.entities.Client;
import com.developerjorney.domain.client.repositories.IClientRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class ClientRepository implements IClientRepository {

    private final IUnitOfWork unitOfWork;

    public ClientRepository(final IUnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public boolean persist(final Client entity) {
        this.unitOfWork.persist(entity);
        return true;
    }

    @Override
    public Client getById(final UUID id) {
        //TODO: Create some query execute
        return this.unitOfWork.getEntityManager().createQuery(
                "SELECT c " +
                "FROM Client c " +
                "WHERE c.id = :id",
                Client.class
        )
                .setParameter("id", id)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }
}
