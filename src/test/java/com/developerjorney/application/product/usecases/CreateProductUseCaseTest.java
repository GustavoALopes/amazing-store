package com.developerjorney.application.product.usecases;


import com.developerjorney.application.product.dtos.inputs.CreateProductInputModel;
import com.developerjorney.configurations.MockUnitOfWork;
import com.developerjorney.core.RequestScopeAttribute;
import com.developerjorney.core.persistence.unitofwork.UnitOfWork;
import com.developerjorney.domain.product.entities.Product;
import com.developerjorney.domain.product.repositories.IProductRepository;
import com.developerjorney.domain.product.services.ProductService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.request.RequestContextHolder;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        CreateProductUseCase.class,
        ProductService.class,
        UnitOfWork.class,
        MockUnitOfWork.class
})
public class CreateProductUseCaseTest {

    @Autowired
    private CreateProductUseCase useCase;


    @MockBean
    private IProductRepository repository;


    @Test
    public void shouldCreateProduct() {
        //Inputs
        final var input = new CreateProductInputModel(
            "XPTO",
                "Description XPTO"
        );

        //Mock
        Mockito.when(this.repository.persist(Mockito.any(Product.class))).thenReturn(true);

        //Execute
        final var response = this.useCase.execute(input);

        Assertions.assertThat(response).isTrue();

        Mockito.verify(this.repository, Mockito.times(1)).persist(Mockito.any(Product.class));
    }

    @Test
    public void shouldFailAndNotifyReasons() {
        //Inputs
        final var input = new CreateProductInputModel(
                "",
                ""
        );

        //Execute
        final var response = this.useCase.execute(input);

        Assertions.assertThat(response).isFalse();

        Mockito.verify(this.repository, Mockito.never()).persist(Mockito.any(Product.class));
    }
}
