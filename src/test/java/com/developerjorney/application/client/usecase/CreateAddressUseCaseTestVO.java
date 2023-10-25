package com.developerjorney.application.client.usecase;

import com.developerjorney.application.client.dtos.input.CreateAddressInput;
import com.developerjorney.application.client.usecases.CreateAddressUseCase;
import com.developerjorney.configurations.MockUnitOfWork;
import com.developerjorney.core.persistence.unitofwork.UnitOfWork;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        CreateAddressUseCase.class,
        MockUnitOfWork.class,
        UnitOfWork.class
})
public class CreateAddressUseCaseTestVO {

    @Autowired
    private CreateAddressUseCase useCase;

    @Test
    public void shouldCreateAddress() {
        //Inputs
        final var input = new CreateAddressInput(
                "BR",
                "RJ",
                "Blumenau",
                "Itoupava Norte",
                "R. dois de setembro",
                "832",
                "89052-000",
                "Proximo a Chevrolet"
        );

        final var result = this.useCase.execute(input);

        Assertions.assertThat(result).isTrue();
    }



}
