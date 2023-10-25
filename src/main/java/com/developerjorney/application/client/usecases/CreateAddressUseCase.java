package com.developerjorney.application.client.usecases;

import com.developerjorney.application.client.dtos.input.CreateAddressInput;
import com.developerjorney.core.persistence.unitofwork.interfaces.IUnitOfWork;
import com.developerjorney.core.usecase.BaseUseCase;
import com.developerjorney.domain.client.entities.inputs.CreateAddressDomainInput;
import com.developerjorney.domain.client.service.interfaces.IClientService;
import org.springframework.stereotype.Service;

@Service
public class CreateAddressUseCase extends BaseUseCase<CreateAddressInput, Boolean> {

    private final IClientService service;

    public CreateAddressUseCase(
            final IUnitOfWork unitOfWork,
            final IClientService service
    ) {
        super(unitOfWork);
        this.service = service;
    }

    @Override
    protected Boolean executeInternal(final CreateAddressInput input) {
        return this.service.createAddress(CreateAddressDomainInput.create(
                input.country(),
                input.state(),
                input.city(),
                input.neighborhood(),
                input.street(),
                input.number(),
                input.zipCode(),
                input.details()
        ));
    }
}
