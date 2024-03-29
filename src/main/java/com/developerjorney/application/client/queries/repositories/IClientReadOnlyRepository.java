package com.developerjorney.application.client.queries.repositories;

import com.developerjorney.application.base.dtos.PageableResponse;
import com.developerjorney.application.client.dtos.view.ClientReportView;
import com.developerjorney.domain.client.entities.Client;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface IClientReadOnlyRepository {

    PageableResponse<ClientReportView> listAll(
            final Specification<Client> spec,
            final Pageable page
    );
}
