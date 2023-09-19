package com.developerjorney.application.product.queries.interfaces;

import com.developerjorney.application.product.dtos.viewmodel.ProductListViewModel;
import org.springframework.data.domain.Pageable;

public interface IProductQuery {
    ProductListViewModel getListProduct(final Pageable page);
}
