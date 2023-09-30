package com.developerjorney.infra.repositories;

import com.developerjorney.BasePostgreSQLContainer;
import com.developerjorney.application.product.queries.repositories.IProductReadOnlyRepository;
import com.developerjorney.configurations.RequestScopeCDI;
import com.developerjorney.core.RequestScopeAttribute;
import com.developerjorney.core.persistence.unitofwork.UnitOfWork;
import com.developerjorney.core.persistence.unitofwork.interfaces.IUnitOfWork;
import com.developerjorney.domain.product.entities.Product;
import com.developerjorney.domain.product.entities.inputs.CreateProductDomainInput;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.request.RequestContextHolder;

@DataJpaTest
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        ProductReadOnlyRepository.class,
        RequestScopeCDI.class,
        UnitOfWork.class
}, initializers = BasePostgreSQLContainer.Initializer.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductReadOnlyRepositoryTest extends BasePostgreSQLContainer {

    @Autowired
    private IProductReadOnlyRepository readOnlyRepository;

    @Autowired
    private IUnitOfWork unitOfWork;

    @BeforeEach
    public void setup() {
//        //Add Request context cause UnitOfWork has request scoped
        RequestContextHolder.setRequestAttributes(new RequestScopeAttribute());
        this.unitOfWork.begin();
    }

    @Test
    void shouldGetListProducts() {
        //Inputs
        final var input = PageRequest.of(0, 20);
        final var product = new Product();
        product.create(
                CreateProductDomainInput.create(
                        "123456",
                        "Description XPTO",
                        "XPTO"
                )
        );
        this.unitOfWork.persist(product);
        this.unitOfWork.commit();

        final var result = this.readOnlyRepository.getListProduct(input);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getPagination()).isNotNull();
        Assertions.assertThat(result.getContent()).isNotEmpty();

        final var viewModel = result.getContent().stream().findFirst().orElse(null);
        Assertions.assertThat(viewModel.getCode()).isEqualTo(product.getCode());
        Assertions.assertThat(viewModel.getDescription()).isEqualTo(product.getDescription());
    }
}
