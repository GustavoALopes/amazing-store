package com.developerjorney.application.product.queries.repositories;

import com.developerjorney.application.base.dtos.PageableResponse;
import com.developerjorney.application.product.dtos.views.ProductViewModel;
import org.springframework.data.domain.Pageable;

public interface IProductReadOnlyRepository {

    PageableResponse<ProductViewModel> getListProduct(final Pageable page);
}
