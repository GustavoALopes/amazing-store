package com.developerjorney.domain.client.service.interfaces;

import com.developerjorney.core.patterns.result.ProcessResult;
import com.developerjorney.domain.client.entities.Client;
import com.developerjorney.domain.client.entities.inputs.CreateAddressDomainInput;
import com.developerjorney.domain.client.entities.inputs.ImportClientDomainInput;

public interface IClientService {

    static final String CREATE_ADDRESS_CLIENT_NOT_FOUND_CODE = "CREATE_ADDRESS_CLIENT_NOT_FOUND";

    ProcessResult<Client> importClient(final ImportClientDomainInput input);

    boolean createAddress(final CreateAddressDomainInput input);
}
