package com.developerjorney.domain.product.services.interfaces;

import com.developerjorney.domain.product.entities.inputs.CreateProductDomainInput;

public interface IProductService {

    boolean create(final CreateProductDomainInput input);
}
