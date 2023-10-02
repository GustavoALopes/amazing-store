package com.developerjorney.application.client.usecases;

import com.developerjorney.application.client.dtos.input.ImportClientInput;
import com.developerjorney.core.persistence.unitofwork.interfaces.IUnitOfWork;
import com.developerjorney.core.usecase.BaseUseCase;
import org.springframework.stereotype.Service;

@Service
public class ImportClientUseCase extends BaseUseCase<ImportClientInput, Boolean> {


    public ImportClientUseCase(final IUnitOfWork unitOfWork) {
        super(unitOfWork);
    }

    @Override
    protected Boolean executeInternal(final ImportClientInput importClientInput) {
        return true;
    }
}
