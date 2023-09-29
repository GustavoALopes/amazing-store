package com.developerjorney.infra.repositories;

import com.developerjorney.core.persistence.unitofwork.interfaces.IUnitOfWork;
import com.developerjorney.domain.product.entities.Product;
import com.developerjorney.domain.product.repositories.IProductRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository implements IProductRepository {

    private final IUnitOfWork unitOfWork;

    public ProductRepository(final IUnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public boolean persist(final Product entity) {
        this.unitOfWork.persist(entity);
        return true;
    }
}
