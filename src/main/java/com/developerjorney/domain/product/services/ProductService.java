package com.developerjorney.domain.product.services;

import com.developerjorney.core.patterns.notification.interfaces.INotificationPublisher;
import com.developerjorney.domain.base.services.BaseService;
import com.developerjorney.domain.product.entities.Product;
import com.developerjorney.domain.product.entities.inputs.CreateProductDomainInput;
import com.developerjorney.domain.product.repositories.IProductRepository;
import com.developerjorney.domain.product.services.interfaces.IProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductService extends BaseService<Product> implements IProductService {

    private final IProductRepository repository;

    public ProductService(
            final INotificationPublisher notificationPublisher,
            final IProductRepository repository
    ) {
        super(notificationPublisher);
        this.repository = repository;
    }

    @Override
    public boolean create(final CreateProductDomainInput input) {
        final var product = new Product();
        product.create(input);

        if(!this.isValidOrNotify(product)) return false;

        return this.repository.persist(product);
    }
}
