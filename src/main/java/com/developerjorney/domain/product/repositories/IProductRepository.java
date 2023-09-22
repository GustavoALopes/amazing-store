package com.developerjorney.domain.product.repositories;

import com.developerjorney.domain.entities.base.interfaces.IAggregateRoot;
import com.developerjorney.domain.entities.base.interfaces.IRepository;

public interface IProductRepository<T extends IAggregateRoot> extends IRepository<T> {
}
