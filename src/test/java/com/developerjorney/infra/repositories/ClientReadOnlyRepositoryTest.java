package com.developerjorney.infra.repositories;

import com.developerjorney.BasePostgreSQLContainer;
import com.developerjorney.application.client.dtos.input.RangeDateInput;
import com.developerjorney.application.client.queries.repositories.IClientReadOnlyRepository;
import com.developerjorney.application.client.queries.specifications.ClientLastNameSpecification;
import com.developerjorney.application.client.queries.specifications.ClientNameSpecification;
import com.developerjorney.application.client.queries.specifications.ClientRangeBirthdateSpecification;
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
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.request.RequestContextHolder;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Objects;

@DataJpaTest
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        ClientRepository.class,
        ClientReadOnlyRepository.class,
        UnitOfWork.class,
        RequestScopeCDI.class
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClientReadOnlyRepositoryTest extends BasePostgreSQLContainer {

    @Autowired
    private IClientReadOnlyRepository repositoryReadOnly;

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
    public void shouldGetClientReport() {
        //Input
        final var client = new Client();
        client.importClient(ImportClientDomainInput.create(
                "Cliente",
                "A",
                "2000-12-01",
                "NOT-IMPLEMENTED-YET"
        ));

        this.repository.persist(client);
        this.unitOfWork.commit();

        final var specification = new ClientNameSpecification(client.getName())
                .and(new ClientLastNameSpecification(client.getLastName()))
                .and(new ClientRangeBirthdateSpecification(new RangeDateInput(
                        "2000-12-01",
                        "2001-12-01"
                )));

        final var result = this.repositoryReadOnly.report(
                specification,
                Pageable.ofSize(20)
        );

        Assertions.assertThat(result).isNotNull();

        final var resultViewModel = result.getContent().stream()
                .filter(filter -> Objects.equals(filter.getId(), client.getId()))
                .findFirst().orElse(null);
        Assertions.assertThat(resultViewModel.getId()).isEqualTo(client.getId());
        Assertions.assertThat(resultViewModel.getName()).isEqualTo(client.getName());
        Assertions.assertThat(resultViewModel.getLastName()).isEqualTo(client.getLastName());
        Assertions.assertThat(resultViewModel.getBirthdate()).isEqualTo(client.getBirthdate().toString());
    }

    @Test
    public void shouldGetClientReportWhenNotSendFilters() {
        //Input
        final var client = new Client();
        client.importClient(ImportClientDomainInput.create(
                "Cliente",
                "A",
                "2000-12-01",
                "NOT-IMPLEMENTED-YET"
        ));

        this.repository.persist(client);
        this.unitOfWork.commit();

        final var specification = new ClientNameSpecification(null)
                .and(new ClientLastNameSpecification(null))
                .and(new ClientRangeBirthdateSpecification(null));

        final var result = this.repositoryReadOnly.report(
                specification,
                Pageable.ofSize(20)
        );

        Assertions.assertThat(result).isNotNull();

        final var resultViewModel = result.getContent().stream()
                .filter(filter -> Objects.equals(filter.getId(), client.getId()))
                .findFirst().orElse(null);

        Assertions.assertThat(resultViewModel.getId()).isEqualTo(client.getId());
        Assertions.assertThat(resultViewModel.getName()).isEqualTo(client.getName());
        Assertions.assertThat(resultViewModel.getLastName()).isEqualTo(client.getLastName());
        Assertions.assertThat(resultViewModel.getBirthdate()).isEqualTo(client.getBirthdate().toString());
    }

}
