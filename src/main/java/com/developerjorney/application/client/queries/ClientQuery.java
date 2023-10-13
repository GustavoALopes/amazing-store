package com.developerjorney.application.client.queries;

import com.developerjorney.application.base.dtos.PageableResponse;
import com.developerjorney.application.client.dtos.input.GetClientReportInput;
import com.developerjorney.application.client.dtos.view.ClientReportView;
import com.developerjorney.application.client.queries.repositories.IClientReadOnlyRepository;
import com.developerjorney.application.client.queries.specifications.ClientLastNameSpecification;
import com.developerjorney.application.client.queries.specifications.ClientNameSpecification;
import com.developerjorney.application.client.queries.specifications.ClientRangeBirthdateSpecification;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClientQuery {

    private final IClientReadOnlyRepository repository;

    public ClientQuery(final IClientReadOnlyRepository repository) {
        this.repository = repository;
    }

    public PageableResponse<ClientReportView> report(
            final GetClientReportInput input,
            final Pageable page
    ) {
        final var spec = new ClientNameSpecification(input.name())
                .and(new ClientLastNameSpecification(input.lastName()))
                .and(new ClientRangeBirthdateSpecification(input.dates()));

        return this.repository.list(
                spec,
                page
        );
    }
}
