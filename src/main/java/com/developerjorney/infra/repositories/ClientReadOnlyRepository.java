package com.developerjorney.infra.repositories;

import com.developerjorney.application.base.dtos.PageableResponse;
import com.developerjorney.application.client.dtos.view.ClientReportView;
import com.developerjorney.application.client.queries.repositories.IClientReadOnlyRepository;
import com.developerjorney.domain.client.entities.Client;
import com.developerjorney.infra.repositories.models.ClientJpaModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class ClientReadOnlyRepository implements IClientReadOnlyRepository {

    private final ClientJpaModel model;

    public ClientReadOnlyRepository(final ClientJpaModel model) {
        this.model = model;
    }

    @Override
    public PageableResponse<ClientReportView> listAll(
            final Specification<Client> spec,
            final Pageable page
    ) {
        final var result = this.model.findAll(
                spec,
                page
        );

        return PageableResponse.<ClientReportView, Client>create(
                result,
                ClientReportView::create
        );
    }
}
