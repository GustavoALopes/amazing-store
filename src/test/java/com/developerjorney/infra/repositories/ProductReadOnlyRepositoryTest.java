package com.developerjorney.infra.repositories;

import com.developerjorney.application.product.queries.repositories.IProductReadOnlyRepository;
import com.developerjorney.infra.repositories.models.ProductJpaModel;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        ProductReadOnlyRepository.class,
        ProductJpaModel.class
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EntityScan(value = {"domain.entities"})
@EnableJpaRepositories(value = {"infra.repositories.*"})
public class ProductReadOnlyRepositoryTest {

    @Autowired
    private IProductReadOnlyRepository readOnlyRepository;

    @Test
    void shouldGetListProducts() {
        //Inputs
        final var input = PageRequest.of(0, 20);

        final var result = this.readOnlyRepository.getListProduct(input);

        Assertions.assertThat(result).isNotNull();
    }
}
