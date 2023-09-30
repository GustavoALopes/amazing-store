package com.developerjorney.application.product.queries;

import com.developerjorney.application.base.dtos.PageableResponse;
import com.developerjorney.application.product.dtos.views.ProductViewModel;
import com.developerjorney.application.product.queries.interfaces.IProductQuery;
import com.developerjorney.application.product.queries.interfaces.ProductQuery;
import com.developerjorney.application.product.queries.repositories.IProductReadOnlyRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        ProductQuery.class
})
public class ProductQueryTest {

    @Autowired
    private IProductQuery query;

    @MockBean
    private IProductReadOnlyRepository readOnlyRepository;


    @Test
    public void shouldGetProductList() {
        //Inputs
        final var input = PageRequest.of(0, 20);
        final var productViewModel = ProductViewModel.create("XPTO", "Description XPTO");
        final var viewModel = PageableResponse.create(
                0,
                1,
                1,
                1L,
                Arrays.asList(productViewModel)
        );

        //Mock
        BDDMockito.given(this.readOnlyRepository.getListProduct(input))
                .willReturn(viewModel);

        //Execution
        final var result = this.query.getListProduct(input);

        //Assertions
        Mockito.verify(this.readOnlyRepository, Mockito.times(1)).getListProduct(input);

        Assertions.assertThat(result.getData().getContent()).isNotEmpty();

        final var productResult = result.getData().getContent().stream().findFirst().orElse(null);
        Assertions.assertThat(productResult.getCode()).isEqualTo(productResult.getCode());
        Assertions.assertThat(productResult.getDescription()).isEqualTo(productResult.getDescription());
    }
}
