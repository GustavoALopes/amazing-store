package com.developerjorney.application.product.queries.interfaces;

import com.developerjorney.application.base.dtos.DefaultResponse;
import com.developerjorney.application.base.dtos.PageableResponse;
import com.developerjorney.application.product.dtos.views.ProductViewModel;
import org.springframework.data.domain.Pageable;

public interface IProductQuery {
    DefaultResponse<PageableResponse<ProductViewModel>> getListProduct(final Pageable page);
}
