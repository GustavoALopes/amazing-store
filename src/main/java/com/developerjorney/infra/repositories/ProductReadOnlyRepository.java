package com.developerjorney.infra.repositories;

import com.developerjorney.application.base.dtos.PageableResponse;
import com.developerjorney.application.product.dtos.views.ProductViewModel;
import com.developerjorney.application.product.queries.repositories.IProductReadOnlyRepository;
import com.developerjorney.domain.product.entities.Product;
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
    public PageableResponse<ProductViewModel> getListProduct(final Pageable page) {
        final var result = this.jpaModel.findAll(page);
        return PageableResponse.<ProductViewModel, Product>create(
                result,
                (product) -> ProductViewModel.create(product)
        );
    }
}
