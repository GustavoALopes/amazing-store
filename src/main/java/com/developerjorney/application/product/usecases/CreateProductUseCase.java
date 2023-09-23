package com.developerjorney.application.product.usecases;

import com.developerjorney.application.product.dtos.inputs.CreateProductInputModel;
import com.developerjorney.application.product.dtos.viewmodel.CreatedProductViewModel;
import com.developerjorney.core.persistence.unitofwork.interfaces.IUnitOfWork;
import com.developerjorney.core.usecase.BaseUseCase;
import com.developerjorney.domain.product.entities.inputs.CreateProductDomainInput;
import com.developerjorney.domain.product.services.interfaces.IProductService;
import org.springframework.stereotype.Service;

@Service
public class CreateProductUseCase extends BaseUseCase<CreateProductInputModel, Boolean> {

    private final IProductService service;

    public CreateProductUseCase(
            final IUnitOfWork unitOfWork,
            final IProductService service
    ) {
        super(unitOfWork);
        this.service = service;
    }

    @Override
    protected Boolean executeInternal(final CreateProductInputModel input) {
        return this.service.create(CreateProductDomainInput.create(
                input.code(),
                input.description(),
                "not_implemeted"
        ));
    }
}
