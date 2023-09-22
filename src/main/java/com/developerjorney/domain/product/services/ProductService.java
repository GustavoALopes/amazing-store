package com.developerjorney.domain.product.services;

import com.developerjorney.domain.base.services.BaseService;
import com.developerjorney.domain.product.entities.Product;
import com.developerjorney.domain.product.entities.inputs.CreateProductDomainInput;
import com.developerjorney.domain.product.repositories.IProductRepository;
import com.developerjorney.domain.product.services.interfaces.IProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductService extends BaseService<Product> implements IProductService {

    private final IProductRepository repository;

    public ProductService(final IProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean create(final CreateProductDomainInput input) {
        final var product = new Product();
        product.create(input);

        if(!product.isValid()) return false;

        return this.repository.persist(product);
    }
}
