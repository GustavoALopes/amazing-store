package com.developerjorney.application.product.queries.interfaces;

import com.developerjorney.application.product.dtos.viewmodel.ProductListViewModel;
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
    public ProductListViewModel getListProduct(final Pageable page) {
        return this.readOnlyRepository.getListProduct(page);
    }
}
