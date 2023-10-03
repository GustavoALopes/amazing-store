package com.developerjorney.infra.repositories;

import com.developerjorney.BasePostgreSQLContainer;
import com.developerjorney.configurations.RequestScopeCDI;
import com.developerjorney.core.RequestScopeAttribute;
import com.developerjorney.core.persistence.unitofwork.UnitOfWork;
import com.developerjorney.core.persistence.unitofwork.interfaces.IUnitOfWork;
import com.developerjorney.domain.client.entities.Client;
import com.developerjorney.domain.client.entities.inputs.ImportClientDomainInput;
import com.developerjorney.domain.client.repositories.IClientRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.request.RequestContextHolder;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    ClientRepository.class,
    RequestScopeCDI.class,
    UnitOfWork.class
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClientRepositoryTest extends BasePostgreSQLContainer {

    @Autowired
    private IClientRepository repository;

    @Autowired
    private IUnitOfWork unitOfWork;

    @BeforeEach
    public void setup() {
        RequestContextHolder.setRequestAttributes(new RequestScopeAttribute());
        this.unitOfWork.begin();
    }

    @Test
    public void shouldPersistEntity() {
        //Input
        final var client = new Client();
        client.importClient(ImportClientDomainInput.create(
                "Cliente",
                "A",
                "2000-12-01",
                "NOT-IMPLEMENT-YET"
        ));

        //Execution
        this.repository.persist(client);

        final var result = this.repository.getById(client.getId());

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(client.getId());
        Assertions.assertThat(result.getName()).isEqualTo(client.getName());
        Assertions.assertThat(result.getLastName()).isEqualTo(client.getLastName());
        Assertions.assertThat(result.getBirthdate()).isEqualTo(client.getBirthdate());
    }
}
