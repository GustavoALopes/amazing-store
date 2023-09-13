package com.developerjorney.application.product.controllers;

import com.developerjorney.application.product.dtos.viewmodel.ProductListViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        ProductController.class
})
public class ProductControllerTest {

    @Autowired
    private ProductController controller;


    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();
    }

    @Test
    public void shouldGetProductList() {
        //Inputs
        final var viewModel = new ProductListViewModel();
    }
}
