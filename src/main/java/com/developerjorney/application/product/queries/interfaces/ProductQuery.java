package com.developerjorney.application.product.queries.interfaces;

import com.developerjorney.application.base.dtos.DefaultResponse;
import com.developerjorney.application.base.dtos.PageableResponse;
import com.developerjorney.application.product.dtos.views.ProductViewModel;
import com.developerjorney.application.product.queries.repositories.IProductReadOnlyRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductQuery implements IProductQuery {

    private final IProductReadOnlyRepository readOnlyRepository;

    public ProductQuery(final IProductReadOnlyRepository readOnlyRepository) {
        this.readOnlyRepository = readOnlyRepository;
    }

    @Override
    public DefaultResponse<PageableResponse<ProductViewModel>> getListProduct(final Pageable page) {
        return DefaultResponse.create(this.readOnlyRepository.getListProduct(page));
    }
}
