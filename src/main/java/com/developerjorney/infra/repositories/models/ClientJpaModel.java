package com.developerjorney.infra.repositories.models;

import com.developerjorney.domain.client.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClientJpaModel extends JpaRepository<Client, UUID>, JpaSpecificationExecutor<Client> {
}
