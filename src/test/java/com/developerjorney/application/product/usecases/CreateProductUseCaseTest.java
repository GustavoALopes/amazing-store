package com.developerjorney.application.product.usecases;


import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        CreateProductUseCase.class
})
public class CreateProductUseCaseTest {

    @Autowired
    private CreateProductUseCase useCase;


}
