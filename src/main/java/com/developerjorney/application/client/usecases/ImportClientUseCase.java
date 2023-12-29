package com.developerjorney.application.client.usecases;

import com.developerjorney.application.client.dtos.input.ImportClientInput;
import com.developerjorney.core.patterns.result.ProcessResult;
import com.developerjorney.core.persistence.unitofwork.interfaces.IUnitOfWork;
import com.developerjorney.core.usecase.BaseUseCase;
import com.developerjorney.domain.client.entities.Client;
import com.developerjorney.domain.client.entities.inputs.ImportClientDomainInput;
import com.developerjorney.domain.client.service.interfaces.IClientService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ImportClientUseCase extends BaseUseCase<ImportClientInput, ProcessResult<Client>> {

    private final IClientService service;
    public ImportClientUseCase(final IUnitOfWork unitOfWork, final IClientService service) {
        super(unitOfWork);
        this.service = service;
    }

    @Override
    protected ProcessResult<Client> executeInternal(final ImportClientInput input) {
        final var builder = ImportClientDomainInput.builder()
                .addClientInformation(
                        ImportClientDomainInput.Builder.ClientInfoBuilder.builder()
                                .name(input.name())
                                .lastName(input.lastName())
                                .birthdate(input.birthdate())
                                .email(input.email())
                )
                .addCreateByInformation("NOT-IMPLEMENTED");

        if(Objects.nonNull(input.address())) {
            builder.addAddressInformation(
                    ImportClientDomainInput.Builder.AddressInfoBuilder.builder()
                            .country(input.address().country())
                            .state(input.address().state())
                            .city(input.address().city())
                            .neighborhood(input.address().neighborhood())
                            .street(input.address().street())
                            .number(input.address().number())
                            .zipCode(input.address().zipCode())
                            .details(input.address().details())
            );
        }

        return this.service.importClient(builder.build());
    }
}
