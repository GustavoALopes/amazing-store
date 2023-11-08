package com.developerjorney.application.client.queries;

import com.developerjorney.application.base.dtos.PageableResponse;
import com.developerjorney.application.client.dtos.input.GetAllClientsInput;
import com.developerjorney.application.client.dtos.input.RangeDateInput;
import com.developerjorney.application.client.dtos.view.ClientReportView;
import com.developerjorney.application.client.queries.repositories.IClientReadOnlyRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Set;
import java.util.UUID;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        ClientQuery.class
})
public class ClientQueryTest {

    @Autowired
    private ClientQuery query;

    @MockBean
    private IClientReadOnlyRepository repository;

    @Test
    public void shouldGetClientReport() {
        //Input
        final var input = new GetAllClientsInput(
                "Cliente",
                "A",
                "email@test.com",
                new RangeDateInput(
                        "2000-12-01",
                        "2002-12-01"
                )
        );

        final var viewModel = PageableResponse.create(
                0,
                1,
                20,
                1,
                Set.of(ClientReportView.create(
                        UUID.randomUUID(),
                        "Client",
                        "A",
                        "2000-12-01",
                        "email@test.com"
                ))
        );

        //Mock
        BDDMockito.given(this.repository.list(
                Mockito.any(Specification.class),
                Mockito.any(Pageable.class)
        )).willReturn(viewModel);

        //Execution
        final var response = this.query.report(
                input,
                Pageable.ofSize(20)
        );

        Assertions.assertThat(response).isNotNull();

        final var viewModelClient = viewModel.getContent().stream().findFirst().orElse(null);
        final var responseClient = response.getContent().stream().findFirst().orElse(null);

        Assertions.assertThat(responseClient.getName()).isEqualTo(viewModelClient.getName());
        Assertions.assertThat(responseClient.getLastName()).isEqualTo(viewModelClient.getLastName());
        Assertions.assertThat(responseClient.getBirthdate()).isEqualTo(viewModelClient.getBirthdate());
        Assertions.assertThat(responseClient.getEmail()).isEqualTo(viewModelClient.getEmail());
    }
}
