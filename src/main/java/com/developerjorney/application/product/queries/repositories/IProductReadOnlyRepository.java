package com.developerjorney.application.product.queries.repositories;

import com.developerjorney.application.product.dtos.views.ProductListViewModel;
import org.springframework.data.domain.Pageable;

public interface IProductReadOnlyRepository {

    ProductListViewModel getListProduct(final Pageable page);
}
