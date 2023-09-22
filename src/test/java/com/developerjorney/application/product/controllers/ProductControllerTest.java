package com.developerjorney.application.product.controllers;

import com.developerjorney.application.enums.ApiVersions;
import com.developerjorney.application.product.dtos.viewmodel.ProductListViewModel;
import com.developerjorney.application.product.dtos.viewmodel.ProductViewModel;
import com.developerjorney.application.product.queries.interfaces.IProductQuery;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        ProductController.class
})
public class ProductControllerTest {

    private static final String URI = ApiVersions.V1 + "/products";

    @Autowired
    private ProductController controller;

    @MockBean
    private IProductQuery query;

    private MockMvc mockMvc;

    private final ObjectMapper mapper;

    public ProductControllerTest() {
        this.mapper = new ObjectMapper();
    }

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    public void shouldGetProductList() throws Exception {
        //Inputs
        final var viewModel = ProductListViewModel.builder()
                .addProduct(ProductViewModel.create("XPTO", "description XPTO"))
                .build();

        //Mock
        BDDMockito.given(this.query.getListProduct(PageRequest.of(0, 20)))
                .willReturn(viewModel);

        //Request
        final var payload = this.mapper.writeValueAsString(viewModel);
        final var request = MockMvcRequestBuilders.get(URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload);

        final var productViewModel = viewModel.getData().stream().findFirst().orElse(null);

        this.mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].code").value(productViewModel.getCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].description").value(productViewModel.getDescription()));
    }
}
