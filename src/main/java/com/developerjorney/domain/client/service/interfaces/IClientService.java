package com.developerjorney.domain.client.service.interfaces;

import com.developerjorney.domain.client.entities.inputs.CreateAddressDomainInput;
import com.developerjorney.domain.client.entities.inputs.ImportClientDomainInput;

public interface IClientService {

    boolean importClient(final ImportClientDomainInput input);

    boolean createAddress(final CreateAddressDomainInput input);
}
