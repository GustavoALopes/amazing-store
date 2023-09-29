package com.developerjorney.infra.repositories;

import com.developerjorney.application.product.dtos.views.ProductListViewModel;
import com.developerjorney.application.product.queries.repositories.IProductReadOnlyRepository;
import com.developerjorney.infra.repositories.models.ProductJpaModel;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class ProductReadOnlyRepository implements IProductReadOnlyRepository {

    private final ProductJpaModel jpaModel;

    public ProductReadOnlyRepository(final ProductJpaModel jpaModel) {
        this.jpaModel = jpaModel;
    }

    @Override
    public ProductListViewModel getListProduct(final Pageable page) {
        final var result = this.jpaModel.findAll(page);
        return ProductListViewModel.create(result);
    }
}
