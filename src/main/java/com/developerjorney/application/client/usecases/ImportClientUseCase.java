package com.developerjorney.application.client.usecases;

import com.developerjorney.application.client.dtos.input.ImportClientInput;
import com.developerjorney.core.persistence.unitofwork.interfaces.IUnitOfWork;
import com.developerjorney.core.usecase.BaseUseCase;
import com.developerjorney.domain.client.entities.inputs.ImportClientDomainInput;
import com.developerjorney.domain.client.service.interfaces.IClientService;
import org.springframework.stereotype.Service;

@Service
public class ImportClientUseCase extends BaseUseCase<ImportClientInput, Boolean> {

    private final IClientService service;
    public ImportClientUseCase(final IUnitOfWork unitOfWork, final IClientService service) {
        super(unitOfWork);
        this.service = service;
    }

    @Override
    protected Boolean executeInternal(final ImportClientInput input) {
        return this.service.importClient(ImportClientDomainInput.create(
                input.name(),
                input.lastname(),
                input.birthdate(),
                "NOT-IMPLEMENT-YET"
        ));
    }
}
