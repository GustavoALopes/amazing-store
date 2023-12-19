package com.developerjorney.infra.repositories;

import com.developerjorney.BasePostgreSQLContainer;
import com.developerjorney.configurations.RequestScopeCDI;
import com.developerjorney.core.RequestScopeAttribute;
import com.developerjorney.core.persistence.unitofwork.UnitOfWork;
import com.developerjorney.core.persistence.unitofwork.interfaces.IUnitOfWork;
import com.developerjorney.domain.client.entities.Client;
import com.developerjorney.domain.client.entities.inputs.CreateAddressDomainInput;
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
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.UUID;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    ClientRepository.class,
    RequestScopeCDI.class,
    UnitOfWork.class
})
@EntityScan(value = {
        "com.developerjorney.domain.client.entities",
        "com.developerjorney.domain.product.entities"
})
@EnableJpaRepositories(value = {"com.developerjorney.infra.repositories.*"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClientRepositoryTest {
//        extends BasePostgreSQLContainer {

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
                UUID.randomUUID() + "@test.com",
                "NOT-IMPLEMENT-YET"
        ));

        final var addressInput = CreateAddressDomainInput.create(
                client.getId(),
                "BR",
                "RJ",
                "Blumenau",
                "Itoupava Norte",
                "R. dois de setembro",
                "832",
                "89052-000",
                "Proximo a Chevrolet",
                "NOT-IMPLEMETED"
        );
        client.addAddress(addressInput);

        //Execution
        this.repository.persist(client);

        final var result = this.repository.getById(client.getId());

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(client.getId());
        Assertions.assertThat(result.getName()).isEqualTo(client.getName());
        Assertions.assertThat(result.getLastName()).isEqualTo(client.getLastName());
        Assertions.assertThat(result.getBirthdate()).isEqualTo(client.getBirthdate());
        Assertions.assertThat(result.getEmail()).isEqualTo(client.getEmail());

        //Address
        Assertions.assertThat(result.getAddress()).isNotEmpty();

        final var address = result.getAddress().stream().findFirst().orElse(null);
        Assertions.assertThat(address.getId()).isNotNull();
        Assertions.assertThat(address.getCountry()).isEqualTo(addressInput.getCountry());
        Assertions.assertThat(address.getCity()).isEqualTo(addressInput.getCity());
        Assertions.assertThat(address.getState()).isEqualTo(addressInput.getState());
        Assertions.assertThat(address.getStreet()).isEqualTo(addressInput.getStreet());
        Assertions.assertThat(address.getNeighborhood()).isEqualTo(addressInput.getNeighborhood());
        Assertions.assertThat(address.getNumber()).isEqualTo(addressInput.getNumber());
        Assertions.assertThat(address.getZipCode()).isEqualTo(addressInput.getZipCode());
        Assertions.assertThat(address.getDetails()).isEqualTo(addressInput.getDetails());
        Assertions.assertThat(address.getInfoAudit().getCreatedBy()).isEqualTo(addressInput.getCreatedBy());
    }

}
